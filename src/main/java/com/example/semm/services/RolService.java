package com.example.semm.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.semm.models.Rol;
import com.example.semm.repositories.RolRepository;
import com.example.semm.security.enums.RolName;

@Service
@Transactional
public class RolService {
	@Autowired
	RolRepository rolRepository;

	public ArrayList<Rol> getAll() {
		return (ArrayList<Rol>) rolRepository.findAll();
	}

	public Optional<Rol> getByRolName(RolName rolNombre) {
		return rolRepository.findByRolNombre(rolNombre);
	}

	public void save(Rol rol) {
		rolRepository.save(rol);
	}
}
