package com.sapient.hackathon.service;

import java.util.List;
import java.util.Map;

import com.sapient.hackathon.domain.ForecastSummer;
import com.sapient.hackathon.model.ChartsResponse;

public interface ForecastSummerService {
	public List<ForecastSummer> getForecastForSummer();
	public ChartsResponse getForecastForASummerDay(int evPercentage, String requestedHour);
	public Map<String, Double> getForecastForANormalDay();
}
