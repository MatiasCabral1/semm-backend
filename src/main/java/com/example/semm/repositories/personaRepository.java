package com.example.semm.repositories;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;

import com.example.semm.security.model.Usuario;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface personaRepository extends CrudRepository<Usuario, Long> {
    //public abstract ArrayList<Persona> findBypriority(Integer prioridad);
}
