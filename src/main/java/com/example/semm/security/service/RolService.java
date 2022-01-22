package com.example.semm.security.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.semm.security.enums.RolName;
import com.example.semm.security.model.Rol;
import com.example.semm.security.repositories.RolRepository;

@Service
@Transactional
public class RolService {
	@Autowired
	RolRepository rolRepository;
	
	public ArrayList<Rol> getAll(){
		return (ArrayList<Rol>)rolRepository.findAll();
	}
	
	public Optional<Rol> getByRolName(RolName rolNombre){
		return rolRepository.findByRolNombre(rolNombre);
	}
	
	public void save(Rol rol){
        rolRepository.save(rol);
    }
}
