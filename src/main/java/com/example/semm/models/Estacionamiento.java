  package com.example.semm.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.semm.security.model.Usuario;

@Entity
@Table(name = "estacionamiento")
public class Estacionamiento {
	@Id
	@Column(unique = true,nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String horaInicio;

	@Column
	private Double precioFinal;
	
	@Column
	private String patente;
	
	@Column
	private Boolean iniciado;
	
	private String username;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Estacionamiento(Long id, String horaInicio, Double precioFinal, String patente, Boolean iniciado,
			Ciudad ciudad, Usuario usuario) {
		this.id = id;
		this.horaInicio = horaInicio;
		this.precioFinal = precioFinal;
		this.patente = patente;
		this.iniciado = iniciado;
		this.ciudad = ciudad;
		this.usuario = usuario;
	}

	public Boolean getIniciado() {
		return iniciado;
	}

	public void setIniciado(Boolean iniciado) {
		this.iniciado = iniciado;
	}

	@Override
	public String toString() {
		return "Estacionamiento [id=" + id + ", horaInicio=" + horaInicio + ", precioFinal=" + precioFinal
				+ ", patente=" + patente + ", iniciado=" + iniciado + ", ciudad=" + ciudad + ", usuario=" + usuario
				+ "]";
	}

	@OneToOne
	private Ciudad ciudad;
	
	public Estacionamiento(Long id, String horaInicio, Double precioFinal, String patente, Ciudad ciudad,
			Usuario usuario) {
		this.id = id;
		this.horaInicio = horaInicio;
		this.precioFinal = precioFinal;
		this.patente = patente;
		this.ciudad = ciudad;
		this.usuario = usuario;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	@OneToOne(optional = true, mappedBy="estacionamiento")
	private Usuario usuario;
		

	public Estacionamiento() {
	}

	public Estacionamiento(Long id, String horaInicio, Double precioFinal, String patente, Usuario usuario) {
		this.id = id;
		this.horaInicio = horaInicio;
		this.precioFinal = precioFinal;
		this.patente = patente;
		this.usuario = usuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Double getPrecioFinal() {
		return precioFinal;
	}

	public void setPrecioFinal(Double precioFinal) {
		this.precioFinal = precioFinal;
	}

	public String getPatente() {
		return patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Long getTiempoTranscurrido() {
		Date inicio = new Date(this.getHoraInicio());
		Date actual = new Date();
		Long tiempoTranscurrido= actual.getTime() -inicio.getTime();
	return tiempoTranscurrido;
	}
	
}
