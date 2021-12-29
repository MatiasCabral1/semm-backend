package com.example.semm.models;

import javax.persistence.*;

import com.example.semm.security.model.Usuario;

@Entity
@Table (name = "ciudad")
public class Ciudad {
	
	@Id
	@Column(unique = true,nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column
	private String horariosEstacionamiento;
	
	
	//private lista diasOperables;
	@Column
	private double valorHora;
	
	@OneToOne(optional = true, mappedBy="ciudad")
	private Estacionamiento estacionamiento;
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	


	public String getHorariosEstacionamiento() {
		return horariosEstacionamiento;
	}


	public void setHorariosEstacionamiento(String horariosEstacionamiento) {
		this.horariosEstacionamiento = horariosEstacionamiento;
	}


	public double getValorHora() {
		return valorHora;
	}


	public void setValorHora(double valorHora) {
		this.valorHora = valorHora;
	}
	
	

}
