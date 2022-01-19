package com.example.semm.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.example.semm.security.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Historial {
	@Id
	@Column(unique = true,nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String fechaOperacion;
	
	@Column 
	private String tipoOperacion;
	
	@Column 
	private double saldoAnterior;
	
	@Column 
	private double monto;
	
	public Historial(String horaInicio, String tipoOperacion, double saldoAnterior, double monto,
			CuentaCorriente cuentaCorriente) {
		this.fechaOperacion = horaInicio;
		this.tipoOperacion = tipoOperacion;
		this.saldoAnterior = saldoAnterior;
		this.monto = monto;
		this.cuentaCorriente = cuentaCorriente;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	@OneToOne(fetch = FetchType.EAGER)
	private CuentaCorriente cuentaCorriente;

	public Historial(String horaInicio, String tipoOperacion, double saldoAnterior) {
		this.fechaOperacion = horaInicio;
		this.tipoOperacion = tipoOperacion;
		this.saldoAnterior = saldoAnterior;
	}

	public double getSaldoAnterior() {
		return saldoAnterior;
	}

	public void setSaldoAnterior(double saldoAnterior) {
		this.saldoAnterior = saldoAnterior;
	}

	public CuentaCorriente getCuentaCorriente() {
		return cuentaCorriente;
	}

	public void setCuentaCorriente(CuentaCorriente cuentaCorriente) {
		this.cuentaCorriente = cuentaCorriente;
	}


	
	public Historial(Long id, String horaInicio, String tipoOperacion) {
		this.id = id;
		this.fechaOperacion = horaInicio;
		this.tipoOperacion = tipoOperacion;
	}
	
	public Historial () { }
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHoraInicio() {
		return fechaOperacion;
	}

	

	public void setHoraInicio(String horaInicio) {
		this.fechaOperacion = horaInicio;
	}

	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

}
