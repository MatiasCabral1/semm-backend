package com.example.semm.models;

import javax.validation.constraints.NotBlank;

public class nuevaPatente {
	@NotBlank
	private String user_name;
	@NotBlank
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
