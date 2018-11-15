package com.sapient.hackathon.domain;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SETTLEMENT_INTERVAL")
public class SettlementInterval {

	@Id
	@Column(name = "ID")
	private int id;

	@Column(name = "EFFECTIVE_START_TIME")
	private Instant effectiveStartTime;
	
	@Column(name = "EFFECTIVE_END_TIME")
	private Instant effectiveEndTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Instant getEffectiveStartTime() {
		return effectiveStartTime;
	}

	public void setEffectiveStartTime(Instant effectiveStartTime) {
		this.effectiveStartTime = effectiveStartTime;
	}

	public Instant getEffectiveEndTime() {
		return effectiveEndTime;
	}

	public void setEffectiveEndTime(Instant effectiveEndTime) {
		this.effectiveEndTime = effectiveEndTime;
	}

	
}
