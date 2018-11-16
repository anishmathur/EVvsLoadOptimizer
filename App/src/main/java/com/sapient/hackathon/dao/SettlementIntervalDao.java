package com.sapient.hackathon.dao;

import java.util.Date;
import java.util.List;

import com.sapient.hackathon.domain.SettlementInterval;

public interface SettlementIntervalDao {

	public List<SettlementInterval> getSettelementInterval(Date startDateTime, Date endDateTime);
	public List<SettlementInterval> getSettelementInterval(Date startDateTime);
	public SettlementInterval getSettelementInterval(Integer settlementInterval);
}
