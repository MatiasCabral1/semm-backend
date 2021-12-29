package com.example.semm.services;

import java.util.ArrayList;
import java.util.Optional;

import com.example.semm.models.Estacionamiento;

public interface EstacionamientoService {
	ArrayList<Estacionamiento> listar();
	Estacionamiento guardarEstacionamiento(Estacionamiento estacionamiento);
	Estacionamiento actualizar(Estacionamiento estacionamiento);
	boolean eliminar(Long id);
	Optional<Estacionamiento> listaPorId(Long id);
}
