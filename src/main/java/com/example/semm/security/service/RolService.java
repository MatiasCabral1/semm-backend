package com.example.semm.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.semm.security.enums.RolNombre;
import com.example.semm.security.model.Rol;
import com.example.semm.security.repositories.RolRepository;

@Service
@Transactional
public class RolService {
	@Autowired
	RolRepository rolRepository;
	
	public Optional<Rol> getByRolNombre(RolNombre rolNombre){
		return rolRepository.findByRolNombre(rolNombre);
	}
	
	public void save(Rol rol){
        rolRepository.save(rol);
    }
}
