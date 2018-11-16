package com.sapient.hackathon.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.sapient.hackathon.dao.ForecastSummerDao;
import com.sapient.hackathon.domain.ForecastSummer;

@Repository
@Transactional
public class ForecastSummerDaoImpl implements ForecastSummerDao {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<ForecastSummer> getForecastForSummer() {
		return entityManager.createQuery("from ForecastSummer").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ForecastSummer> getForecastForASummerDay() {
		return entityManager
				.createQuery("from ForecastSummer where settlementInterval>=70297 and settlementInterval<=70320")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ForecastSummer> getForecastForASummerDay(int permutation,int startSettlementIntervalId, int endSettlementIntervalId) {
		return entityManager
				.createQuery("from ForecastSummer where permutation=:permutation and settlementInterval>= :startSettlementIntervalId and settlementInterval<= :endSettlementIntervalId")
				.setParameter("permutation", permutation)
				.setParameter("startSettlementIntervalId", startSettlementIntervalId)
				.setParameter("endSettlementIntervalId", endSettlementIntervalId).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ForecastSummer> getForecastForANormalDay() {
		return entityManager
				.createQuery("from ForecastSummer where settlementInterval>=50297 and settlementInterval<=50320")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public Double getMaxYhat(int permutation, int startSettlementIntervalId, int endSettlementIntervalId) {

			return (Double) entityManager
				.createQuery("select max(yHat) from ForecastSummer where permutation= :permutation and settlementInterval>= :startSettlementIntervalId and settlementInterval<= :endSettlementIntervalId")
				.setParameter("permutation", permutation)
				.setParameter("startSettlementIntervalId", startSettlementIntervalId)
				.setParameter("endSettlementIntervalId", endSettlementIntervalId).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getSettlementInterval(Double yHat) {

		return entityManager.createQuery("select settlementInterval from ForecastSummer where yHat= :yHat ")
				.setParameter("yHat", yHat).getResultList();
	}
}
