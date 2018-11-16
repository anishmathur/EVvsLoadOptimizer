package com.sapient.hackathon.dao;

import java.util.List;

import com.sapient.hackathon.domain.ForecastSummer;
import com.sapient.hackathon.domain.ForecastWinter;

public interface ForecastSummerDao {

	public List<ForecastSummer> getForecastForSummer();
	
	public List<ForecastSummer> getForecastForASummerDay();
	
	public List<ForecastSummer> getForecastForANormalDay();
	
	public Double getMaxYhat(int permuation, int startSettlementIntervalId, int endSettlementIntervalId);

	public List<Integer> getSettlementInterval(Double integer);

	public List<ForecastSummer> getForecastForASummerDay(int permuation,int startId, int endId);
}
