package com.sapient.hackathon.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.sapient.hackathon.dao.PermutationDao;
import com.sapient.hackathon.domain.Permutation;

@Repository
@Transactional
public class PermutationDaoImpl implements PermutationDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Permutation getPermutationForEVPercentandWindow(int evpercentage, int window) {
		return (Permutation) entityManager
				.createQuery(" from Permutation where evpercentage=:evpercentage and window=:window")
				.setParameter("evpercentage", evpercentage)
				.setParameter("window", window).getSingleResult();		
	}
}
