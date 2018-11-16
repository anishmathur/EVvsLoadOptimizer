package com.sapient.hackathon.service.impl;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapient.hackathon.dao.ForecastSummerDao;
import com.sapient.hackathon.domain.ForecastSummer;
import com.sapient.hackathon.domain.ForecastWinter;
import com.sapient.hackathon.domain.Permutation;
import com.sapient.hackathon.domain.SettlementInterval;
import com.sapient.hackathon.model.ChartsResponse;
import com.sapient.hackathon.model.Dataset;
import com.sapient.hackathon.model.JsonDataset;
import com.sapient.hackathon.service.ForecastSummerService;
import com.sapient.hackathon.service.PermutationService;
import com.sapient.hackathon.service.SettlementIntervalService;

@Service
public class ForecastSummerServiceImpl implements ForecastSummerService{

	@Autowired
	ForecastSummerDao forecastSummerDao;
	
	@Autowired
	SettlementIntervalService settlementIntervalService;
	
	@Autowired
	PermutationService permutationService;
	
	
	@Override
	public List<ForecastSummer> getForecastForSummer() {
		return forecastSummerDao.getForecastForSummer();
	}

	@Override
	public ChartsResponse getForecastForASummerDay(int evpercentage, String requestedHour) {
	
		System.out.println("SUMMER-AAAA :"+evpercentage);
		System.out.println("SUMMER-BBBB :"+requestedHour);
		
		Map<String, Integer> hourMap = new HashMap<String, Integer>();
		hourMap.put("0,3",1);
		hourMap.put("3,6",2);
		hourMap.put("6,9",3);
		hourMap.put("9,12",4);
		hourMap.put("12,15",5);
		hourMap.put("15,18",6);
		hourMap.put("18,21",7);
		hourMap.put("21,24",8);
		
		//TODO: remove hardcoded value
		Permutation permutation = permutationService.getPermutationForEVPercentandWindow(evpercentage, hourMap.get(requestedHour));
		System.out.println("SUMMER-Permutation :"+permutation.getId());
		//Get Max yHat between settlement interval 105193,113952
		Double yhat = forecastSummerDao.getMaxYhat(permutation.getId(), 107353,111744);
		System.out.println("SUMMER-Max yHat between settlement interval 105193,113952:"+yhat);
		
		
		//Get Settlement Intervals for Max(yHat)
		List<Integer> sids = forecastSummerDao.getSettlementInterval(yhat);
		System.out.println("SUMMER-Settlement Intervals for max yHat:"+sids);
		
		int setId=0;
		for (Integer temp : sids) {
			if(temp>=107353 && temp<=111744){
				setId = temp;
			}
			
		}
		
		//Get Settlement Interval for Max Yhat-sid
		SettlementInterval siMaxYhat = settlementIntervalService.getSettelementInterval(setId);
		System.out.println("SUMMER-Settlement Interval for Max Yhat-settlementInterval:"+siMaxYhat.getEffectiveEndTime());
		
		//Let all settlement interval for that day
		Date topOfDay = siMaxYhat.getEffectiveEndTime();
		Date endOfDay = siMaxYhat.getEffectiveEndTime();
		topOfDay.setHours(0);
		
		
		
		//Date endOfDay = siMaxYhat.getEffectiveEndTime();
		//endOfDay.setHours(23);
		
		System.out.println("SUMMER-Start Date:"+topOfDay);
		System.out.println("SUMMER-End Date:"+endOfDay);
		List<SettlementInterval> sis = settlementIntervalService.getSettelementInterval(topOfDay, endOfDay);
		
		System.out.println("SUMMER-Sids"+sis.get(0).getId());
		
		List<ForecastSummer> forecastsWithoutEV = forecastSummerDao.getForecastForASummerDay(81,sis.get(0).getId(), sis.get(0).getId()+23);
		List<ForecastSummer> forecastsWithEV = forecastSummerDao.getForecastForASummerDay(permutation.getId(),sis.get(0).getId(), sis.get(0).getId()+23);
		
		
		ChartsResponse chartResponse = new ChartsResponse();
		chartResponse.setAppName("Summer Forecast for 2022");
		
		List<String> lables = new ArrayList<String>();
		IntStream.range(1,24).forEach(i -> lables.add(Integer.toString(i)));
		chartResponse.setLables(lables);
		
		
		List<Dataset> datasets = new ArrayList<Dataset>();
		Dataset dataset = new Dataset();
		
		dataset.setName(" Load with EVs ");				
		dataset.setValue(forecastsWithEV.stream().map(ForecastSummer::getyHat).collect(Collectors.toList()));
		datasets.add(dataset);
		
		
		Dataset datasetNormalDay = new Dataset();
		
		datasetNormalDay.setName(" Load without EVs ");				
		datasetNormalDay.setValue(forecastsWithoutEV.stream().map(ForecastSummer::getyHat).collect(Collectors.toList()));
		datasets.add(datasetNormalDay);
		Dataset forecastedGeneration = new Dataset();
		List<Double> generation = new ArrayList<Double>();
		IntStream.range(1,24).forEach(i -> generation.add(12880.0));
		generation.stream().forEach(System.out::println);
		forecastedGeneration.setName("Generation");;
		forecastedGeneration.setValue(generation);
		datasets.add(forecastedGeneration);
		chartResponse.setDatasets(datasets);
		
		return chartResponse;
		
	}
	
	@Override
	public Map<String, Double>  getForecastForANormalDay() {
		// TODO Auto-generated method stub
		
		List<ForecastSummer> forecasts = forecastSummerDao.getForecastForASummerDay();
		
		//JsonDataset dataset = new JsonDataset();
		List<JsonDataset> datasets = new ArrayList<JsonDataset>();
		datasets.add(new JsonDataset("0",forecasts.get(0).getyHat()));
		datasets.add(new JsonDataset("1",forecasts.get(1).getyHat()));
		datasets.add(new JsonDataset("2",forecasts.get(2).getyHat()));
		datasets.add(new JsonDataset("3",forecasts.get(3).getyHat()));
		
		Map<String, Double> map = new HashMap<String, Double>();
		map.put("0",forecasts.get(0).getyHat());
		map.put("1",forecasts.get(1).getyHat());
		map.put("2",forecasts.get(2).getyHat());
		map.put("3",forecasts.get(3).getyHat());
		map.put("4",forecasts.get(4).getyHat());
		
		
		//IntStream.range(1,24).forEach(i -> lables.add(Integer.toString(i)));
		
		//forecasts.stream().map(ForecastSummer::getyHat).collect(Collectors.toList());
		return map;
		
	}
	
	public static Instant getDateTimeInstant(int year, int month, int dayOfMonth, int hour, int minute,
		      int second, int milliSecond) {
		System.out.println("Instant:"+ZonedDateTime.of(year, month, dayOfMonth, hour, minute, second, milliSecond,ZoneId.systemDefault()).toInstant());
		    return ZonedDateTime.of(year, month, dayOfMonth, hour, minute, second, milliSecond,ZoneId.systemDefault()).toInstant();
		  }

}
