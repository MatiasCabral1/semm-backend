package com.example.semm.services;

import java.util.ArrayList;
import java.util.List;

import com.example.semm.models.Historial;

public interface HistorialService {
	ArrayList<Historial> listar();
	Historial guardaHistorial(Historial historial);
	List<Historial> listaPorId(Long id);
}
