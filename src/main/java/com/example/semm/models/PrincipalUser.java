package com.example.semm.models;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class PrincipalUser implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String username;
	private String email;
	private String password;
	private Collection <? extends GrantedAuthority> authorities;
	
	public PrincipalUser(String nombre, String nombreUsuario, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.name = nombre;
		this.username = nombreUsuario;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}
	
	public static PrincipalUser build(User usuario) {
		List<GrantedAuthority> authorities = 
				usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol.getRolNombre().name())).collect(Collectors.toList());
	return new PrincipalUser(usuario.getName(), usuario.getUsername(),usuario.getEmail(),usuario.getPassword(),authorities);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	
}
