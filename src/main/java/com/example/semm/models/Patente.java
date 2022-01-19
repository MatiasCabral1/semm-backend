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

import com.example.semm.security.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "patente")
public class Patente {

	@Id
	@Column(unique = true,nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String numero;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	Usuario usuario;


	public Patente(Long id, String numero, Usuario usuario) {
		super();
		this.id = id;
		this.numero = numero;
		this.usuario = usuario;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Patente(String numero, Usuario usuario) {
		super();
		this.numero = numero;
		this.usuario = usuario;
	}

	public Patente() {
		super();
	}

	public Patente(String numero) {
		super();
		this.numero = numero;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	
}
