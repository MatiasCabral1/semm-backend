package com.example.semm.security.dto;

import java.io.Serializable;

public class TimePriceDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double hours;
	private double minutes;
	private double price;
	private String patent;

	public TimePriceDTO(String patent, double hours, double minutes, double price) {
		this.hours = hours;
		this.minutes = minutes;
		this.price = price;
		this.patent = patent;
	}

	public String getPatent() {
		return patent;
	}

	public void setPatent(String patent) {
		this.patent = patent;
	}

	public void setHours(double hours) {
		this.hours = hours;
	}

	public void setMinutes(double minutes) {
		this.minutes = minutes;
	}

	public TimePriceDTO(double horas, double minutos, double precio) {
		this.hours = horas;
		this.minutes = minutos;
		this.price = precio;
	}

	public double getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public double getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
