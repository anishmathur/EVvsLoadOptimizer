package com.sapient.hackathon.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.sapient.hackathon.dao.ForecastWinterDao;
import com.sapient.hackathon.domain.ForecastWinter;

@Repository
@Transactional
public class ForecastWinterDaoImpl implements ForecastWinterDao{

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<ForecastWinter> getForecastForWinter() {
		return entityManager.createQuery("from ForecastWinter").getResultList();
	}
	
	@Override
	public List<ForecastWinter> getForecastForAwinterDay() {
		return entityManager.createQuery("from ForecastWinter where settlementInterval>=70297 and settlementInterval<=70320").getResultList();
	}

	@Override
	public List<ForecastWinter> getForecastForANormalDay() {
		return entityManager.createQuery("from ForecastWinter where settlementInterval>=50297 and settlementInterval<=50320").getResultList();
	}
}
