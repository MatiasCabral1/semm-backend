package com.example.semm.security.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.semm.models.CuentaCorriente;

public class datosCuentaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotBlank
    private String nombre;
	@NotBlank(message = "El telefono NO puede ser vacio")
    private String nombreUsuario;//nombre de usuario = telefono
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getEmail() {
		return email;
	}

	public datosCuentaDTO(@NotBlank String nombre,
			@NotBlank(message = "El telefono NO puede ser vacio") String nombreUsuario,
			@NotBlank(message = "el correo NO puede ser vacio") String email, CuentaCorriente cuentaCorriente) {
		this.nombre = nombre;
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.cuentaCorriente = cuentaCorriente;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public CuentaCorriente getCuentaCorriente() {
		return cuentaCorriente;
	}

	public void setCuentaCorriente(CuentaCorriente cuentaCorriente) {
		this.cuentaCorriente = cuentaCorriente;
	}

	@NotBlank(message = "el correo NO puede ser vacio")
    private String email;
    
    private CuentaCorriente cuentaCorriente;

}
