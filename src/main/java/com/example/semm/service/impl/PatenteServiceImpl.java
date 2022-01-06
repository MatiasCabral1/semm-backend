package com.example.semm.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.semm.models.Patente;
import com.example.semm.repositories.PatenteRepository;
import com.example.semm.repositories.personaRepository;
import com.example.semm.services.PatenteService;
@Service
public class PatenteServiceImpl implements PatenteService{
	@Autowired
    PatenteRepository patenteRepository;
	
	@Override
	public ArrayList<Patente> listar() {
		return (ArrayList<Patente>)patenteRepository.findAll();
	}

	@Override
	public Patente guardarPatente(Patente patente) {
		return patenteRepository.save(patente);
	}

	@Override
	public Patente actualizar(Patente patente) {
		return patenteRepository.save(patente);
	}

	@Override
	public boolean eliminar(Long id) {
		try {
            patenteRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
	}

	@Override
	public Optional<Patente> listaPorId(Long id) {
		return patenteRepository.findById(id);
	}
	
	public Patente existsByPatenteAndUsuario(String patente, Long idUser) {
		return patenteRepository.existsByPatenteAndUsuario(patente, idUser);
	}

}
