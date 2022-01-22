package com.example.semm.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.semm.models.Holiday;

public interface HolidayRepository extends CrudRepository<Holiday, Long>{
	Optional<Holiday> getByDate(String date);
}
