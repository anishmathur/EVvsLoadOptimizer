package com.sapient.hackathon.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapient.hackathon.dao.SettlementIntervalDao;
import com.sapient.hackathon.domain.SettlementInterval;
import com.sapient.hackathon.service.SettlementIntervalService;

@Service
public class SettlementIntervalServiceImpl implements SettlementIntervalService{

	@Autowired
	SettlementIntervalDao settlementIntervalDao;

	@Override
	public List<SettlementInterval> getSettelementInterval(Date startDateTime, Date endDateTime) {
		// TODO Auto-generated method stub
		return settlementIntervalDao.getSettelementInterval(startDateTime, endDateTime);
	}
	
	
	

	@Override
	public List<SettlementInterval> getSettelementInterval(Date startDateTime) {
		// TODO Auto-generated method stub
		return settlementIntervalDao.getSettelementInterval(startDateTime);
	}


	@Override
	public SettlementInterval getSettelementInterval(Integer settlementInterval) {
		// TODO Auto-generated method stub
		return settlementIntervalDao.getSettelementInterval(settlementInterval);
	}
	
	
	
}
