package com.example.semm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.semm.models.Historial;
import com.example.semm.repositories.HistorialRepository;
import com.example.semm.services.HistorialService;
@Service

public class HistorialServiceImpl implements HistorialService {
	@Autowired
    HistorialRepository historialRepository;
	@Override
	public ArrayList<Historial> listar() {
		return (ArrayList<Historial>)historialRepository.findAll();
	}

	@Override
	public Historial guardaHistorial(Historial historial) {
		return historialRepository.save(historial);
	}

	@Override
	public List<Historial> listaPorId(Long id) {
		return historialRepository.existsByCuentaCorriente(id);
	}

}
