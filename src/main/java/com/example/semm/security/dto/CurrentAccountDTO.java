package com.example.semm.security.dto;

import java.io.Serializable;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CurrentAccountDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private Long id;
	@NotNull
	@NotBlank
	private String phone;
	@NotNull(message = "Debe ingresar un monto")
	@Positive(message = "debe ingresar un monto positivo")
	@DecimalMin("100")
    private double balance;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
}
