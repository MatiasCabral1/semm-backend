package com.example.semm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.semm.models.PrincipalUser;
import com.example.semm.models.User;
import com.example.semm.service.impl.UserServiceImp;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	UserServiceImp usuarioService;
	@Override
	public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
		User usuario = usuarioService.getByNombreUsuario(nombreUsuario).get();
		return PrincipalUser.build(usuario);
	}

}
