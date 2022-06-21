package com.bikram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bikram.reader.HumidityBean;
import com.bikram.reader.TempBean;
import com.bikram.reader.TemparatureReader;

@RestController
public class TempController {
	@Autowired
	private TemparatureReader reader;
	@GetMapping("/getTemp")
	public TempBean getTemp() {
		return reader.getTemperature();
	}
	
	@GetMapping("/health")
	public String index() {
		return "ON";
	}
	
	@GetMapping("/getHum")
	public HumidityBean getHum() {
		return reader.getHumidity();
	}
}
