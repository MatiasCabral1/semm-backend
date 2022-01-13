package com.example.semm.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.semm.models.Feriado;

public interface FeriadoRepository extends CrudRepository<Feriado, Long>{
	Optional<Feriado> getByFecha(String fecha);
}
