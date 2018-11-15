package com.sapient.hackathon.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapient.hackathon.dao.ForecastWinterDao;
import com.sapient.hackathon.dao.SettlementIntervalDao;
import com.sapient.hackathon.domain.ForecastWinter;
import com.sapient.hackathon.model.ChartsResponse;
import com.sapient.hackathon.model.Dataset;
import com.sapient.hackathon.model.JsonDataset;
import com.sapient.hackathon.service.ForecastWinterService;
import com.sapient.hackathon.service.SettlementIntervalService;

@Service
public class SettlementIntervalServiceImpl implements SettlementIntervalService{

	@Autowired
	SettlementIntervalDao settlementIntervalDao;

	@Override
	public int getSettelementInterval(Instant startDateTime, Instant endDateTime) {
		// TODO Auto-generated method stub
		return settlementIntervalDao.getSettelementInterval(startDateTime, endDateTime);
	}
	


}
