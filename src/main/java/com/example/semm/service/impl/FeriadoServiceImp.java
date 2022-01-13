package com.example.semm.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.semm.models.Feriado;
import com.example.semm.repositories.FeriadoRepository;
import com.example.semm.services.FeriadoService;

@Service
public class FeriadoServiceImp implements FeriadoService{
	@Autowired
    FeriadoRepository feriadoRepository;

	@Override
	public ArrayList<Feriado> listar() {
		return (ArrayList<Feriado>)feriadoRepository.findAll();
	}

	@Override
	public Feriado guardaHistorial(Feriado feriado) {
		return feriadoRepository.save(feriado);
	}
	
	public Optional<Feriado> getPorFecha(String fecha) {
		return feriadoRepository.getByFecha(fecha);
	}
}
