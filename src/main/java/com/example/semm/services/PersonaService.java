package com.example.semm.services;

import java.util.ArrayList;
import java.util.Optional;

import com.example.semm.security.model.Usuario;

public interface PersonaService {
	ArrayList<Usuario> listar();
	Usuario guardarPersona(Usuario persona);
	Usuario  actualizar(Usuario persona);
	boolean eliminar(Long id);
	Optional<Usuario> listaPorId(Long id);

}
