package com.example.semm.models;
import java.util.Date;
import java.util.Optional;

import javax.persistence.*;

import com.example.semm.security.dto.TiempoPrecioDTO;
import com.example.semm.security.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table (name = "cuentaCorriente")
public class CuentaCorriente {

	@Id
	@Column(unique = true,nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column 
	private double saldo;
	
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Column 
	private String telefono;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
	private Usuario usuario;
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
	
	


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

	public TiempoPrecioDTO consumo(Optional<Usuario> per, Optional<Estacionamiento> est, Ciudad ciudad) {
		TiempoPrecioDTO result = est.get().getDatosCobroActual(ciudad);
		this.setSaldo(this.getSaldo() - result.getPrecio());	
        return result;
	}
	
	public void cargar(double monto) {
		this.setSaldo(this.getSaldo() + monto);
	}
	
}
