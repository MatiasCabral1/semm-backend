package com.example.semm.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.semm.models.Patent;

public interface PatentRepository extends CrudRepository<Patent, Long>{
	@Query(value="SELECT * FROM Patent p WHERE p.number=?1 and p.user_id =?2", 
			nativeQuery = true)
	Patent existsByPatenteAndUsuario(String patent, Long idUser);
	
	@Query(value="SELECT * FROM Patent p WHERE p.user_id =?1", 
			nativeQuery = true)
	Set<Patent> getByIdUser(Long idUser);
	
}
