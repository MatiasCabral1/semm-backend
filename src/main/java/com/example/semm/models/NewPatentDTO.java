package com.example.semm.models;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.example.semm.security.dto.NewUserDTO;
import com.example.semm.security.model.User;

public class NewPatentDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	@NotNull(message = "no puede ser null")
	private NewUserDTO user;
	@NotBlank
	@Pattern(regexp = "([a-zA-Z]{3}\\d{3})|([a-zA-Z]{2}\\d{3}[a-zA-Z]{2})",message ="la patente debe tener el formato AAA999 o AA000AA")
	private String number;
	
	public NewUserDTO getUser() {
		return user;
	}

	public void setUser(NewUserDTO user) {
		this.user = user;
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
