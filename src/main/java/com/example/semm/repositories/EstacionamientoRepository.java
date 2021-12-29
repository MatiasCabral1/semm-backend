package com.example.semm.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.semm.models.Estacionamiento;
@Repository
public interface EstacionamientoRepository extends JpaRepository <Estacionamiento, Long>{
	@Query(
			value = "SELECT * FROM Estacionamiento e WHERE e.iniciado = 1 and e.username = :username", 
			nativeQuery = true)
	Optional<Estacionamiento> findByEstado(@Param("username") String username);
	
}	
