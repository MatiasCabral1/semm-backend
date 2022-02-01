package com.example.semm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.semm.models.History;


public interface HistoryRepository extends CrudRepository<History, Long>{
	@Query(value="SELECT * FROM History h WHERE h.current_account_id =?1 ORDER BY h.date_transaction desc", 
			nativeQuery = true)
	List<History> getByCurrentAccountId(Long id);

}
