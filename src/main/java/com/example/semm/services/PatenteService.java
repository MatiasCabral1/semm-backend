package com.example.semm.services;

import java.util.ArrayList;
import java.util.Optional;

import com.example.semm.models.Patente;


public interface PatenteService {
	ArrayList<Patente> listar();
	Patente guardarPatente(Patente patente);
	Patente actualizar(Patente patente);
	boolean eliminar(Long id);
	Optional<Patente> listaPorId(Long id);
}
