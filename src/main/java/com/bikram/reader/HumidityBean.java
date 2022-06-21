package com.bikram.reader;

import org.springframework.stereotype.Component;

@Component
public class HumidityBean {

	private double value;
	private String time;
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
