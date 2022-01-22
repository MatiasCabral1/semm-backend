package com.example.semm.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.semm.models.Holiday;
import com.example.semm.repositories.HolidayRepository;
import com.example.semm.services.HolidayService;

@Service
public class HolidayServiceImp implements HolidayService{
	@Autowired
    HolidayRepository feriadoRepository;

	@Override
	public ArrayList<Holiday> getAll() {
		return (ArrayList<Holiday>)feriadoRepository.findAll();
	}

	@Override
	public Holiday save(Holiday feriado) {
		return feriadoRepository.save(feriado);
	}
	
	public Optional<Holiday> getByDate(String fecha) {
		return feriadoRepository.getByDate(fecha);
	}
}
