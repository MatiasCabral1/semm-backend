package com.example.semm.security.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.example.semm.models.CurrentAccount;
import com.example.semm.models.Parking;
import com.example.semm.models.Patent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

@Entity
public class User {
	
	public Parking getEstacionamiento() {
		return parking;
	}

	public void setParking(Parking estacionamiento) {
		this.parking = estacionamiento;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String name;
	@NotNull
	@Column(unique = true)
	private String username; // el nombre de usuario seria el telefono por ahora. Se utiliza para el logeo.
	@NotNull
	private String email;
	@NotNull
	private String password;
	
	@JsonIgnore
	@OneToMany(mappedBy="user")
	Set<Patent> patentList;
	
	@OneToOne(mappedBy = "user",  fetch = FetchType.EAGER)
	private CurrentAccount currentAccount;
	
	@JsonIgnore
	@OneToOne
	private Parking parking;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id"),
	inverseJoinColumns = @JoinColumn(name = "rol_id"))
	private Set<Rol> roles = new HashSet<>();
	
	public User() {}
	
	public User(@NotNull String name,@NotNull String phone, @NotNull String email, @NotNull String password) {
		super();
		this.name = name;
		this.username = phone;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<Patent> getPatentList() {
		return patentList;
	}

	public void setPatenteList(Set<Patent> patenteList) {
		this.patentList = patenteList;
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


	public CurrentAccount getCurrentAccount() {
		return currentAccount;
	}

	public void setCurrentAccount(CurrentAccount currentAccount) {
		this.currentAccount = currentAccount;
	}


}
