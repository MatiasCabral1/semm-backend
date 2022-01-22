package com.example.semm.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.semm.security.model.User;
import com.example.semm.security.model.PrincipalUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	UserService usuarioService;
	@Override
	public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
		User usuario = usuarioService.getByNombreUsuario(nombreUsuario).get();
		return PrincipalUser.build(usuario);
	}

}
