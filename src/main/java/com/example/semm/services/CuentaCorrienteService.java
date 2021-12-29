package com.example.semm.services;

import java.util.ArrayList;
import java.util.Optional;

import com.example.semm.models.CuentaCorriente;


public interface CuentaCorrienteService {
	ArrayList<CuentaCorriente> listar();
	CuentaCorriente registrar(CuentaCorriente cc);
	CuentaCorriente actualizar(CuentaCorriente cc);
	boolean eliminar(Long id);
	Optional<CuentaCorriente> listaPorId(Long id);
}
