package com.sapient.hackathon.dao;

import java.time.Instant;
import java.util.List;

import com.sapient.hackathon.domain.ForecastWinter;

public interface SettlementIntervalDao {

	public int getSettelementInterval(Instant startDateTime, Instant endDateTime);
}
