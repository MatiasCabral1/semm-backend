package com.example.semm.services;

import java.util.ArrayList;
import java.util.Optional;

import com.example.semm.models.Ciudad;

public interface CiudadService {	
	ArrayList<Ciudad> listar();
	Ciudad guardarCiudad(Ciudad ciudad);
	Ciudad  actualizar(Ciudad ciudad);
	boolean eliminar(Long id);
	Optional<Ciudad> listaPorId(Long id);
}
