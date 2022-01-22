package com.example.semm.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.semm.security.model.User;
import com.example.semm.security.repositories.UserRepository;

@Service
@Transactional
public class UserService {
	
	@Autowired
	UserRepository usuarioRepository;
	
	public Optional<User>getByNombreUsuario(String nombreUsuario){
		return usuarioRepository.findByUsername(nombreUsuario);
	}
	
	public boolean existsByUsername(String nombreUsuario) {
		return usuarioRepository.existsByUsername(nombreUsuario);
	}
	
	public boolean existsByEmail(String email) {
		return usuarioRepository.existsByEmail(email);
	}
	
	public void save(User usuario) {
		usuarioRepository.save(usuario);
	}
}
