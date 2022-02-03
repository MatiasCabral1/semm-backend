package com.example.semm.services;

import java.util.ArrayList;
import java.util.Optional;

import com.example.semm.models.City;

public interface CityService {
	ArrayList<City> getAll();

	City saveCity(City ciudad);

	City update(City ciudad);

	boolean delete(Long id);

	Optional<City> getById(Long id);
}
