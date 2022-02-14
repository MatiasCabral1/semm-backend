package com.example.semm.security.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class NewParkingDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String date;
	private String patent;
	private Boolean started;
	@NotNull(message = "{parking.user.notNull}")
	private NewUserDTO user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPatent() {
		return patent;
	}

	public void setPatent(String patent) {
		this.patent = patent;
	}

	public Boolean getStarted() {
		return started;
	}

	public void setStarted(Boolean started) {
		this.started = started;
	}

	public NewUserDTO getUser() {
		return user;
	}

	public void setUser(NewUserDTO user) {
		this.user = user;
	}

}
