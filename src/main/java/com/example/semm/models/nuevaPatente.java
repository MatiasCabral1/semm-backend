package com.example.semm.models;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class nuevaPatente implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotBlank
	private String user_name;
	@NotBlank
	@Pattern(regexp = "([a-zA-Z]{3}\\d{3})|([a-zA-Z]{2}\\d{3}[a-zA-Z]{2})",message ="la patente debe tener el formato AAA999 o AA000AA")
	private String numero;
	
	public nuevaPatente(String user_name, String number) {
		super();
		this.user_name = user_name;
		this.numero = number;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getNumber() {
		return numero;
	}
	public void setNumber(String number) {
		this.numero = number;
	}
	
}
