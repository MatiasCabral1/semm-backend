package com.example.semm.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.semm.models.Parking;
import com.example.semm.models.Patent;
import com.example.semm.repositories.ParkingRepository;
import com.example.semm.services.ParkingService;
@Service
public class ParkingServiceImp implements ParkingService {
	@Autowired
	ParkingRepository estRepository;

	@Override
	public ArrayList<Parking> getAll() {
		return (ArrayList<Parking>)estRepository.findAll();
	}

	@Override
	public Parking saveParking(Parking estacionamiento) {
		return estRepository.save(estacionamiento);
	}

	@Override
	public Parking update(Parking estacionamiento) {
		return estRepository.save(estacionamiento);
	}

	@Override
	public boolean delete(Long id) {
		try {
			estRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
	}

	@Override
	public Optional<Parking> getById(Long id) {
		return estRepository.findById(id);
	}
	
	public Optional<Parking> getParkingStartedByUser(String username) {
		return estRepository.findByStartedAndUSer(username);
	}
	
	public boolean parkingStartedWithPatent(String patent, String username) {
		//return -> true -> hay un estacionamiento iniciado con esa patente.
		return !estRepository.getParkingStartedWithPatent(patent,username).isEmpty();
	}
	
	@Transactional(readOnly = true)
	public Parking findByPatentStarted(String patente) {
		return estRepository.findByPatentStarted(patente);
	}
}
