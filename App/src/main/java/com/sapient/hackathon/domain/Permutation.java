package com.sapient.hackathon.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PERMUTATION")
public class Permutation {

	@Id
	@Column(name = "ID")
	private int id;

	@Column(name = "EV_PERCENTAGE")
	private int evpercentage;

	@Column(name = "WINDOW")
	private int window;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEvpercentage() {
		return evpercentage;
	}

	public void setEvpercentage(int evpercentage) {
		this.evpercentage = evpercentage;
	}

	public int getWindow() {
		return window;
	}

	public void setWindow(int window) {
		this.window = window;
	}

}
