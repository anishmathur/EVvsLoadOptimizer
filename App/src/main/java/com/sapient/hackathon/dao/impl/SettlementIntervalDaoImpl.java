package com.sapient.hackathon.dao.impl;

import java.time.Instant;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.sapient.hackathon.dao.ForecastWinterDao;
import com.sapient.hackathon.dao.SettlementIntervalDao;
import com.sapient.hackathon.domain.ForecastWinter;

@Repository
@Transactional
public class SettlementIntervalDaoImpl implements SettlementIntervalDao{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public int getSettelementInterval(Instant startDateTime, Instant endDateTime) {
		return entityManager.createQuery("from SettelementInterval si where si.effectiveStartTime LIKE :startDateTime and effectiveEndTime LIKE :endDateTime")
				.setParameter("startDateTime", startDateTime)
				.setParameter("endDateTime", endDateTime)
				.getFirstResult();
	}

	
	
	
}
