package com.example.semm.security.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NuevoUsuario implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotBlank
    private String nombre;
	@NotNull(message = "Debe ingresar un telefono")
	@NotBlank(message = "El telefono NO puede ser vacio")
	@Size(min=10, max=10,message = "El telefono debe tener 10 digitos")
    private String nombreUsuario;//nombre de usuario = telefono
    @NotNull(message = "Debe ingresar un correo")
	@NotBlank(message = "el correo NO puede ser vacio")
	@Email(message = "el correo debe tener el formato texto@texto.texto")
    private String email;
    @NotNull(message = "Debe ingresar una clave")
    private String password;
    
    private Set<String> roles = new HashSet<>();

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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
