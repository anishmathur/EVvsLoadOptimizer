package com.sapient.hackathon.model;

import java.util.List;

public class Dataset {

	public String name;
	public List<Double> value;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Double> getValue() {
		return value;
	}
	public void setValue(List<Double> value) {
		this.value = value;
	}	
}
