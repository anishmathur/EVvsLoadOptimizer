package com.sapient.hackathon.dao;

import java.util.List;

import com.sapient.hackathon.domain.ForecastWinter;

public interface ForecastWinterDao {

	public List<ForecastWinter> getForecastForWinter();
	
	public List<ForecastWinter> getForecastForAwinterDay();
	
	public List<ForecastWinter> getForecastForANormalDay();
}
