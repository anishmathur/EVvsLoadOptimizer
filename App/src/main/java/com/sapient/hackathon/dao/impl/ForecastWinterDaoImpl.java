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
public class ForecastWinterDaoImpl implements ForecastWinterDao {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<ForecastWinter> getForecastForWinter() {
		return entityManager.createQuery("from ForecastWinter").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ForecastWinter> getForecastForAwinterDay() {
		return entityManager
				.createQuery("from ForecastWinter where settlementInterval>=70297 and settlementInterval<=70320")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ForecastWinter> getForecastForAwinterDay(int permutation, int startSettlementIntervalId, int endSettlementIntervalId) {
		return entityManager
				.createQuery("from ForecastWinter where permutation=:permutation and settlementInterval>= :startSettlementIntervalId and settlementInterval<= :endSettlementIntervalId")
				.setParameter("permutation", permutation)
				.setParameter("startSettlementIntervalId", startSettlementIntervalId)
				.setParameter("endSettlementIntervalId", endSettlementIntervalId).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ForecastWinter> getForecastForANormalDay() {
		return entityManager
				.createQuery("from ForecastWinter where settlementInterval>=50297 and settlementInterval<=50320")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public Double getMaxYhat(int permutation, int startSettlementIntervalId, int endSettlementIntervalId) {

			return (Double) entityManager
				.createQuery("select max(yHat) from ForecastWinter where permutation= :permutation and settlementInterval>= :startSettlementIntervalId and settlementInterval<= :endSettlementIntervalId")
				.setParameter("permutation", permutation)
				.setParameter("startSettlementIntervalId", startSettlementIntervalId)
				.setParameter("endSettlementIntervalId", endSettlementIntervalId).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getSettlementInterval(Double yHat) {

		return entityManager.createQuery("select settlementInterval from ForecastWinter where yHat= :yHat ")
				.setParameter("yHat", yHat).getResultList();
	}
}
