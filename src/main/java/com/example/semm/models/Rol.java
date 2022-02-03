package com.example.semm.models;

import javax.persistence.*;

import com.example.semm.security.enums.RolName;
import com.sun.istack.NotNull;

@Entity
public class Rol {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull
	@Enumerated(EnumType.STRING)
	private RolName rolNombre;

	public Rol() {
		super();
	}

	public Rol(RolName rolNombre) {
		super();
		this.rolNombre = rolNombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RolName getRolNombre() {
		return rolNombre;
	}

	public void setRolNombre(@NotNull RolName rolNombre) {
		this.rolNombre = rolNombre;
	}

}
