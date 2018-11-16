package com.sapient.hackathon.domain;


import java.util.Date;

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
	private Date effectiveStartTime;
	
	@Column(name = "EFFECTIVE_END_TIME")
	private Date effectiveEndTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getEffectiveStartTime() {
		return effectiveStartTime;
	}

	public void setEffectiveStartTime(Date effectiveStartTime) {
		this.effectiveStartTime = effectiveStartTime;
	}

	public Date getEffectiveEndTime() {
		return effectiveEndTime;
	}

	public void setEffectiveEndTime(Date effectiveEndTime) {
		this.effectiveEndTime = effectiveEndTime;
	}

	

	
}
