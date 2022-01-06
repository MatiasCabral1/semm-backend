package com.example.semm.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.semm.models.Patente;

public interface PatenteRepository extends CrudRepository<Patente, Long>{
	@Query(value="SELECT * FROM Patente p WHERE p.numero=?1 and p.id_usuario =?2", 
			nativeQuery = true)
	Patente existsByPatenteAndUsuario(String patente, Long idUser);
}
