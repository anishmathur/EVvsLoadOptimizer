package com.sapient.hackathon.service;

import java.util.List;
import java.util.Map;

import com.sapient.hackathon.domain.ForecastWinter;
import com.sapient.hackathon.model.ChartsResponse;
import com.sapient.hackathon.model.JsonDataset;

public interface ForecastWinterService {
	public List<ForecastWinter> getForecastForWinter();
	public ChartsResponse getForecastForAwinterDay(int evPercentage, String requestedHour);
	public Map<String, Double> getForecastForANormalDay();
}
