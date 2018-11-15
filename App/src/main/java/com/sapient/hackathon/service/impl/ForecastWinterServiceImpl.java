package com.sapient.hackathon.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapient.hackathon.dao.ForecastWinterDao;
import com.sapient.hackathon.domain.ForecastWinter;
import com.sapient.hackathon.model.ChartsResponse;
import com.sapient.hackathon.model.Dataset;
import com.sapient.hackathon.model.JsonDataset;
import com.sapient.hackathon.service.ForecastWinterService;
import com.sapient.hackathon.service.SettlementIntervalService;

@Service
public class ForecastWinterServiceImpl implements ForecastWinterService{

	@Autowired
	ForecastWinterDao forecastWinterDao;
	
	@Autowired
	SettlementIntervalService settlementIntervalService;
	
	
	@Override
	public List<ForecastWinter> getForecastForWinter() {
		return forecastWinterDao.getForecastForWinter();
	}

	@Override
	public ChartsResponse getForecastForAwinterDay(int evPercentage, String requestedHour) {
		
//		int si = settlementIntervalService.getSettelementInterval(Instant.MIN, endDateTime);

		// TODO Auto-generated method stub
		List<ForecastWinter> forecasts = forecastWinterDao.getForecastForAwinterDay();
		ChartsResponse chartResponse = new ChartsResponse();
		chartResponse.setAppName("Winter Forecast");
		
		List<String> lables = new ArrayList<String>();
		IntStream.range(1,24).forEach(i -> lables.add(Integer.toString(i)));
		chartResponse.setLables(lables);
		
		
		List<Dataset> datasets = new ArrayList<Dataset>();
		Dataset dataset = new Dataset();
		
		dataset.setName("My First dataset");				
		dataset.setValue(forecasts.stream().map(ForecastWinter::getyHat).collect(Collectors.toList()));
		datasets.add(dataset);
		
		
		Dataset datasetNormalDay = new Dataset();
		
		datasetNormalDay.setName("My Second dataset");				
		datasetNormalDay.setValue(forecasts.stream().map(ForecastWinter::getyHat).map(i->i-500).collect(Collectors.toList()));
		datasets.add(datasetNormalDay);
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

}
