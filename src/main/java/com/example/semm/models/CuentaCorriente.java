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

	public double consumo(Optional<Usuario> per, Optional<Estacionamiento> est) {
		 Date today = new Date();
		int hora = Integer.parseInt(est.get().getHoraInicio().split(":")[0]);
        int minuto = Integer.parseInt(est.get().getHoraInicio().split(":")[1]);
        System.out.println("hora obtenida: "+ hora + ":" + minuto);
        int horaFinalizado = today.getHours() - hora;
        if(horaFinalizado > 0) {
        	System.out.println("se debitara el monto correspondiente a "+ horaFinalizado);
        	this.setSaldo(this.getSaldo()-(10*horaFinalizado));
        }else {
        	System.out.println("se debitara el monto correspondiente a una hora");
        	this.setSaldo(this.getSaldo()-10);
        }
		
        return this.getSaldo();
	}
	
}
