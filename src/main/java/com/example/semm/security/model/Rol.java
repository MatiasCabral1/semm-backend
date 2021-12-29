package com.example.semm.security.model;

import javax.persistence.*;

import com.example.semm.security.enums.RolNombre;
import com.sun.istack.NotNull;

@Entity
public class Rol {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull
	@Enumerated(EnumType.STRING)
	private RolNombre rolNombre;
	public Rol() {
		super();
	}
	public Rol(RolNombre rolNombre) {
		super();
		this.rolNombre = rolNombre;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public RolNombre getRolNombre() {
		return rolNombre;
	}
	public void setRolNombre(@NotNull RolNombre rolNombre) {
		this.rolNombre = rolNombre;
	}
	
	

}
