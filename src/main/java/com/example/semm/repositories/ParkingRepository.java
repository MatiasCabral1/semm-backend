package com.example.semm.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.semm.models.Parking;
import com.example.semm.models.Patent;
@Repository
public interface ParkingRepository extends JpaRepository <Parking, Long>{
	@Query(
			value = "SELECT * FROM Parking e WHERE e.started = 1 and e.username = :username", 
			nativeQuery = true)
	Optional<Parking> findByStartedAndUSer(@Param("username") String username);
	
	@Query(value="SELECT * FROM Parking e WHERE e.started = 1 and e.patent=?1 and e.username =?2", 
			nativeQuery = true)
	Optional<Parking> getParkingStartedWithPatent(String patent, String username);
	
	@Query(value="SELECT * FROM Parking e WHERE e.started = 1 and e.patent=?1", 
			nativeQuery = true)
	Parking findByPatentStarted(String patent);
	
	
}	
