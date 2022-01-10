package com.example.semm.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.semm.models.Historial;


public interface HistorialRepository extends CrudRepository<Historial, Long>{
	@Query(value="SELECT * FROM Historial h WHERE h.cuenta_corriente_id =?1", 
			nativeQuery = true)
	List<Historial> existsByCuentaCorriente(Long id);
}
