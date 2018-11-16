package com.sapient.hackathon.dao.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.sapient.hackathon.dao.ForecastWinterDao;
import com.sapient.hackathon.dao.SettlementIntervalDao;
import com.sapient.hackathon.domain.ForecastWinter;import com.sapient.hackathon.domain.SettlementInterval;

@Repository
@Transactional
public class SettlementIntervalDaoImpl implements SettlementIntervalDao{

	@PersistenceContext
	private EntityManager entityManager;


	@SuppressWarnings("unchecked")
	@Override
	public List<SettlementInterval> getSettelementInterval(Date startDateTime, Date endDateTime) {

		Date date1 = null;
		Date date2 = null;
		System.out.println("Start Date:" + startDateTime.toString());

		DateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		try {
			date1 = parseFormat.parse(startDateTime.toString());
			date2 = parseFormat.parse("2022-12-12 23:00:00.0");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<SettlementInterval> list = entityManager
				.createQuery("from SettlementInterval si " + "where si.effectiveStartTime = :startDateTime")
				.setParameter("startDateTime", date1)
				//.setParameter("endDateTime", date2)
				.getResultList();

		return list;
	}

	@SuppressWarnings("unchecked")
	public List<SettlementInterval> getSettelementInterval(Date startDateTime) {

		Date date1 = null;

		DateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		try {
			date1 = parseFormat.parse("2018-03-01T00:00:00.000-0500");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<SettlementInterval> list = entityManager
				.createQuery("from SettlementInterval si " + "where si.effectiveStartTime = :startDateTime")
				.setParameter("startDateTime", date1).getResultList();

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SettlementInterval getSettelementInterval(Integer settlementInterval) {
		List<SettlementInterval> list = entityManager
				.createQuery("from SettlementInterval si " + "where si.id = :settlementInterval")
				.setParameter("settlementInterval", settlementInterval).getResultList();

		return list.get(0);
	}

	
}
