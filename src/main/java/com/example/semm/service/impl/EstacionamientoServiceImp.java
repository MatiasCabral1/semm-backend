package com.example.semm.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.semm.models.Estacionamiento;
import com.example.semm.models.Patente;
import com.example.semm.repositories.EstacionamientoRepository;
import com.example.semm.services.EstacionamientoService;
@Service
public class EstacionamientoServiceImp implements EstacionamientoService {
	@Autowired
	EstacionamientoRepository estRepository;

	@Override
	public ArrayList<Estacionamiento> listar() {
		return (ArrayList<Estacionamiento>)estRepository.findAll();
	}

	@Override
	public Estacionamiento guardarEstacionamiento(Estacionamiento estacionamiento) {
		return estRepository.save(estacionamiento);
	}

	@Override
	public Estacionamiento actualizar(Estacionamiento estacionamiento) {
		return estRepository.save(estacionamiento);
	}

	@Override
	public boolean eliminar(Long id) {
		try {
			estRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
	}

	@Override
	public Optional<Estacionamiento> listaPorId(Long id) {
		return estRepository.findById(id);
	}
	
	public Optional<Estacionamiento> getPorEstado(String username) {
		return estRepository.findByEstado(username);
	}
}
