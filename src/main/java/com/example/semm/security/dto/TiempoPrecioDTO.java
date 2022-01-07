package com.example.semm.security.dto;

import java.io.Serializable;

public class TiempoPrecioDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double horas;
	private double minutos;
	private double precio;
	
	public TiempoPrecioDTO(double horas, double minutos, double precio) {
		this.horas = horas;
		this.minutos = minutos;
		this.precio = precio;
	}
	public double getHoras() {
		return horas;
	}
	public void setHoras(int horas) {
		this.horas = horas;
	}
	public double getMinutos() {
		return minutos;
	}
	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
}
