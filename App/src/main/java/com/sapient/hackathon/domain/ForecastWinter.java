package com.sapient.hackathon.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FORECAST_WINTER")
public class ForecastWinter {

	@Id
	@Column(name = "ID")
	private int id;

	@Column(name = "SETTLEMENT_INTERVAL")
	private int settlementInterval;

	@Column(name = "PERMUTATION")
	private int permutation;

	@Column(name = "YHAT")
	private double yHat;

	public int getSettlementInterval() {
		return settlementInterval;
	}

	public void setSettlementInterval(int settlementInterval) {
		this.settlementInterval = settlementInterval;
	}

	public int getPermutation() {
		return permutation;
	}

	public void setPermutation(int permutation) {
		this.permutation = permutation;
	}

	public double getyHat() {
		return yHat;
	}

	public void setyHat(double yHat) {
		this.yHat = yHat;
	}
}
