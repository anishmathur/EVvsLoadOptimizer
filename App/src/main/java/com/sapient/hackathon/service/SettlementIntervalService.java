package com.sapient.hackathon.service;

import java.util.Date;
import java.util.List;

import com.sapient.hackathon.domain.SettlementInterval;

public interface SettlementIntervalService {
	public List<SettlementInterval> getSettelementInterval(Date startDateTime, Date endDateTime);
	public List<SettlementInterval> getSettelementInterval(Date startDateTime);
	public SettlementInterval getSettelementInterval(Integer settlementInterval);
};
