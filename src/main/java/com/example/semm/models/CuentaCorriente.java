package com.example.semm.models;
import java.util.Date;
import java.util.Optional;

import javax.persistence.*;

import com.example.semm.security.model.Usuario;


@Entity
@Table (name = "cuentaCorriente")
public class CuentaCorriente {

	@Id
	@Column(unique = true,nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column 
	private double saldo;
	
	@OneToOne(optional = true, mappedBy="cuentaCorriente")
	private Usuario usuario;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}



	public void carga(double total) {
		// TODO Auto-generated method stub
		
	}

	public double consumo(Optional<Usuario> per, Optional<Estacionamiento> est, Ciudad ciudad) {
		 Date today = new Date();
		int hora = Integer.parseInt(est.get().getHoraInicio().split(":")[0]);
        int minuto = Integer.parseInt(est.get().getHoraInicio().split(":")[1]);
        int tiempoTranscurrido = today.getHours() - hora;
        if(today.getMinutes() - minuto < 0){
        	try {
				this.setSaldo(this.getSaldo() - (ciudad.getValorHora() + (ciudad.getValorHora()* tiempoTranscurrido-1)));
			} catch (Exception e) {
				this.setSaldo(this.getSaldo()-(10 + (10*tiempoTranscurrido)));
			}
        }else {
        	try {
				this.setSaldo(this.getSaldo() - (ciudad.getValorHora() + (ciudad.getValorHora()* tiempoTranscurrido)));
			} catch (Exception e) {
				this.setSaldo(this.getSaldo()-(10 + (10*tiempoTranscurrido)));
			}
        }
        	
        	
		
        return this.getSaldo();
	}
	
}
