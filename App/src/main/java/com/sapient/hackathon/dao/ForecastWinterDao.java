package com.sapient.hackathon.dao;

import java.util.List;

import com.sapient.hackathon.domain.ForecastWinter;

public interface ForecastWinterDao {

	public List<ForecastWinter> getForecastForWinter();
	
	public List<ForecastWinter> getForecastForAwinterDay();
	
	public List<ForecastWinter> getForecastForANormalDay();
	
	public Double getMaxYhat(int permuation, int startSettlementIntervalId, int endSettlementIntervalId);

	public List<Integer> getSettlementInterval(Double integer);

	public List<ForecastWinter> getForecastForAwinterDay(int permuation,int startId, int endId);
}
