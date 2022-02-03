package com.example.semm.services;

import java.util.ArrayList;
import java.util.Optional;

import com.example.semm.models.Parking;

public interface ParkingService {
	ArrayList<Parking> getAll();

	Parking saveParking(Parking estacionamiento);

	Parking update(Parking estacionamiento);

	boolean delete(Long id);

	Optional<Parking> getById(Long id);
}
