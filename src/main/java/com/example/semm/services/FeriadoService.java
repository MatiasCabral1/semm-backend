package com.example.semm.services;

import java.util.ArrayList;
import java.util.List;

import com.example.semm.models.Feriado;

public interface FeriadoService {
	ArrayList<Feriado> listar();
	Feriado guardaHistorial(Feriado feriado);
}
