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

import com.sapient.hackathon.dao.ForecastWinterDao;
import com.sapient.hackathon.domain.ForecastWinter;
import com.sapient.hackathon.domain.Permutation;
import com.sapient.hackathon.domain.SettlementInterval;
import com.sapient.hackathon.model.ChartsResponse;
import com.sapient.hackathon.model.Dataset;
import com.sapient.hackathon.model.JsonDataset;
import com.sapient.hackathon.service.ForecastWinterService;
import com.sapient.hackathon.service.PermutationService;
import com.sapient.hackathon.service.SettlementIntervalService;

import ch.qos.logback.core.net.SyslogOutputStream;

@Service
public class ForecastWinterServiceImpl implements ForecastWinterService{

	@Autowired
	ForecastWinterDao forecastWinterDao;
	
	@Autowired
	SettlementIntervalService settlementIntervalService;
	
	@Autowired
	PermutationService permutationService;
	
	
	@Override
	public List<ForecastWinter> getForecastForWinter() {
		return forecastWinterDao.getForecastForWinter();
	}

	@Override
	public ChartsResponse getForecastForAwinterDay(int evpercentage, String requestedHour) {
	
		System.out.println("AAAA :"+evpercentage);
		System.out.println("BBBB :"+requestedHour);
		
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
		System.out.println("Permutation :"+permutation.getId());
		//Get Max yHat between settlement interval 105193,113952
		Double yhat = forecastWinterDao.getMaxYhat(permutation.getId(), 105193,107352);
		System.out.println("Max yHat between settlement interval 105193,113952:"+yhat);
		
		
		//Get Settlement Intervals for Max(yHat)
		List<Integer> sids = forecastWinterDao.getSettlementInterval(yhat);
		System.out.println("Settlement Intervals for max yHat:"+sids);
		
		int setId=0;
		for (Integer temp : sids) {
			if(temp>=105193 && temp<=107352){
				setId = temp;
			}
			
		}
		System.out.println("Settlement Intervals for max yHat:"+setId);
		
		//Get Settlement Interval for Max Yhat-sid
		SettlementInterval siMaxYhat = settlementIntervalService.getSettelementInterval(setId);
		System.out.println("Settlement Interval for Max Yhat-settlementInterval:"+siMaxYhat.getEffectiveEndTime());
		
		//Let all settlement interval for that day
		Date topOfDay = siMaxYhat.getEffectiveEndTime();
		Date endOfDay = siMaxYhat.getEffectiveEndTime();
		topOfDay.setHours(0);
		
		
		
		//Date endOfDay = siMaxYhat.getEffectiveEndTime();
		//endOfDay.setHours(23);
		
		System.out.println("Start Date:"+topOfDay);
		System.out.println("End Date:"+endOfDay);
		List<SettlementInterval> sis = settlementIntervalService.getSettelementInterval(topOfDay, endOfDay);
		
		System.out.println("Sids"+sis.get(0).getId());
		
		List<ForecastWinter> forecastsWithEV = forecastWinterDao.getForecastForAwinterDay(permutation.getId(),sis.get(0).getId(), sis.get(0).getId()+23);
		List<ForecastWinter> forecastsWithoutEV = forecastWinterDao.getForecastForAwinterDay(81,sis.get(0).getId(), sis.get(0).getId()+23);
		
		ChartsResponse chartResponse = new ChartsResponse();
		chartResponse.setAppName("Winter Forecast");
		
		List<String> lables = new ArrayList<String>();
		IntStream.range(1,24).forEach(i -> lables.add(Integer.toString(i)));
		chartResponse.setLables(lables);
		
		
		List<Dataset> datasets = new ArrayList<Dataset>();
		Dataset dataset = new Dataset();
		
		dataset.setName(" Load with EVs ");				
		dataset.setValue(forecastsWithEV.stream().map(ForecastWinter::getyHat).collect(Collectors.toList()));
		datasets.add(dataset);
		
		
		Dataset datasetNormalDay = new Dataset();
		
		datasetNormalDay.setName(" Load without EVs ");				
		datasetNormalDay.setValue(forecastsWithoutEV.stream().map(ForecastWinter::getyHat).collect(Collectors.toList()));
		datasets.add(datasetNormalDay);
		Dataset forecastedGeneration = new Dataset();
		List<Double> generation = new ArrayList<Double>();
		IntStream.range(1,24).forEach(i -> generation.add(12880.0));
		generation.stream().forEach(System.out::println);
		forecastedGeneration.setName(" Generation ");;
		forecastedGeneration.setValue(generation);
		datasets.add(forecastedGeneration);
		chartResponse.setDatasets(datasets);
		
		return chartResponse;
		
	}
	
	@Override
	public Map<String, Double>  getForecastForANormalDay() {
		// TODO Auto-generated method stub
		
		List<ForecastWinter> forecasts = forecastWinterDao.getForecastForAwinterDay();
		
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
		
		//forecasts.stream().map(ForecastWinter::getyHat).collect(Collectors.toList());
		return map;
		
	}
	
	public static Instant getDateTimeInstant(int year, int month, int dayOfMonth, int hour, int minute,
		      int second, int milliSecond) {
		System.out.println("Instant:"+ZonedDateTime.of(year, month, dayOfMonth, hour, minute, second, milliSecond,ZoneId.systemDefault()).toInstant());
		    return ZonedDateTime.of(year, month, dayOfMonth, hour, minute, second, milliSecond,ZoneId.systemDefault()).toInstant();
		  }

}
