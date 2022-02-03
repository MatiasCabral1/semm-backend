package com.example.semm.security.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.example.semm.models.CurrentAccount;

public class DataAccountUserDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String name;
	@NotBlank(message = "El telefono NO puede ser vacio")
	private String username;// nombre de usuario = telefono

	@NotBlank(message = "el correo NO puede ser vacio")
	private String email;

	private CurrentAccount currentAccount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public CurrentAccount getCurrentAccount() {
		return currentAccount;
	}

	public void setCurrentAccount(CurrentAccount currentAccount) {
		this.currentAccount = currentAccount;
	}

	public DataAccountUserDTO(@NotBlank String name,
			@NotBlank(message = "El telefono NO puede ser vacio") String username,
			@NotBlank(message = "el correo NO puede ser vacio") String email, CurrentAccount currentAccount) {
		this.name = name;
		this.username = username;
		this.email = email;
		this.currentAccount = currentAccount;
	}

}
