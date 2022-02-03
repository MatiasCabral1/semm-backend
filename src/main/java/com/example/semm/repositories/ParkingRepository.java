package com.example.semm.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.semm.models.Parking;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {
	@Query(value = "SELECT * FROM Parking e WHERE e.started = 1 and e.user_id = :userId", nativeQuery = true)
	Optional<Parking> findByStartedAndUSer(@Param("userId") Long userId);

	@Query(value = "SELECT * FROM Parking e WHERE e.started = 1 and e.patent=?1 and e.user_id =?2", nativeQuery = true)
	Optional<Parking> getParkingStartedWithPatent(String patent, Long id);

	@Query(value = "SELECT * FROM Parking e WHERE e.started = 1 and e.patent=?1", nativeQuery = true)
	Parking findByPatentStarted(String patent);

}
