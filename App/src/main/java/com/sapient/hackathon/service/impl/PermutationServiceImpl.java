package com.sapient.hackathon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapient.hackathon.dao.PermutationDao;
import com.sapient.hackathon.domain.Permutation;
import com.sapient.hackathon.service.PermutationService;

@Service
public class PermutationServiceImpl implements PermutationService{
	
	@Autowired
	PermutationDao permutationDao;

	@Override
	public Permutation getPermutationForEVPercentandWindow(int evpercentage, int window) {

		return permutationDao.getPermutationForEVPercentandWindow(evpercentage, window);
	}
	
	
	
}
