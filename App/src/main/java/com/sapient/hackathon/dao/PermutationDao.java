package com.sapient.hackathon.dao;

import com.sapient.hackathon.domain.Permutation;

public interface PermutationDao {

	public Permutation getPermutationForEVPercentandWindow(int evpercentage, int window);
}
