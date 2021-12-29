package com.example.semm.security.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.example.semm.models.CuentaCorriente;
import com.example.semm.models.Estacionamiento;
import com.example.semm.models.Patente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

@Entity
public class Usuario {
	
	public Estacionamiento getEstacionamiento() {
		return estacionamiento;
	}

	public void setEstacionamiento(Estacionamiento estacionamiento) {
		this.estacionamiento = estacionamiento;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String nombre;
	@NotNull
	@Column(unique = true)
	private String nombreUsuario; // el nombre de usuario seria el telefono por ahora. Se utiliza para el logeo.
	@NotNull
	private String email;
	@NotNull
	private String password;
	
	@JsonIgnore
	@OneToMany(mappedBy="usuario")
	Set<Patente> patenteList;
	
	@JsonIgnore
	@OneToOne
	private CuentaCorriente cuentaCorriente;
	
	@JsonIgnore
	@OneToOne
	private Estacionamiento estacionamiento;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id"),
	inverseJoinColumns = @JoinColumn(name = "rol_id"))
	private Set<Rol> roles = new HashSet<>();
	
	public Usuario() {}
	
	public Usuario(@NotNull String nombre,@NotNull String nombreUsuario, @NotNull String email, @NotNull String password) {
		super();
		this.nombre = nombre;
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Set<Patente> getPatenteList() {
		return patenteList;
	}

	public void setPatenteList(Set<Patente> patenteList) {
		this.patenteList = patenteList;
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

	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}


	public CuentaCorriente getCuentaCorriente() {
		return cuentaCorriente;
	}

	public void setCuentaCorriente(CuentaCorriente cuentaCorriente) {
		this.cuentaCorriente = cuentaCorriente;
	}


}
