package com.sapient.hackathon.service;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import com.sapient.hackathon.domain.ForecastWinter;
import com.sapient.hackathon.model.ChartsResponse;
import com.sapient.hackathon.model.JsonDataset;

public interface SettlementIntervalService {
	public int getSettelementInterval(Instant startDateTime, Instant endDateTime);
}
