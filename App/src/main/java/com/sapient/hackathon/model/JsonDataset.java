package com.sapient.hackathon.model;

import java.util.List;

public class JsonDataset {

	public String label;
	public Double value;
	
	public JsonDataset(String label, Double value) {
		super();
		this.label = label;
		this.value = value;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
}
