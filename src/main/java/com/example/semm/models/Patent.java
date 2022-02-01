package com.example.semm.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import com.example.semm.security.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "patent")
public class Patent {

	@Id
	@Column(unique = true,nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String number;
	
	@JsonIgnore
	@ManyToOne
	User user;


	public Patent(Long id, String number, User user) {
		super();
		this.id = id;
		this.number = number;
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Patent(String number, User user) {
		super();
		this.number = number;
		this.user = user;
	}

	public Patent() {
		super();
	}

	public Patent(String numero) {
		super();
		this.number = numero;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	
}
