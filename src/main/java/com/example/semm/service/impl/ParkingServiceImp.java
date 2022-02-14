package com.example.semm.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.semm.models.City;
import com.example.semm.models.Holiday;
import com.example.semm.models.Parking;
import com.example.semm.repositories.ParkingRepository;
import com.example.semm.security.dto.Message;
import com.example.semm.services.ParkingService;

@Service
public class ParkingServiceImp implements ParkingService {
	@Autowired
	ParkingRepository estRepository;
	
	@Autowired
	MessageSource msg;

	@Override
	public ArrayList<Parking> getAll() {
		return (ArrayList<Parking>) estRepository.findAll();
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

	public Optional<Parking> findByStartedAndUSer(Long id) {
		return estRepository.findByStartedAndUSer(id);
	}

	public boolean parkingStartedWithPatent(String patent, Long id) {
		// return -> true -> hay un estacionamiento iniciado con esa patente.
		return !estRepository.getParkingStartedWithPatent(patent, id).isEmpty();
	}

	@Transactional(readOnly = true)
	public Parking findByPatentStarted(String patente) {
		return estRepository.findByPatentStarted(patente);
	}
	
	public Message validation(City city, Iterable<Holiday> holidays) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
		String dateFormatted = sdf.format(new Date());
		Date fullDate = new Date();
		@SuppressWarnings("deprecation")
		int hourStart = fullDate.getHours();
		String hourStartCity = city.getParkingHours().split("-")[0];
		String hourEndCity = city.getParkingHours().split("-")[1];

		if ((hourStart >= Integer.valueOf(hourStartCity)) && (hourStart < Integer.valueOf(hourEndCity))) {
			if (!Parking.isNonWorkingDate(dateFormatted, holidays)) {
				if (!Parking.isWeekend(fullDate.toString())) {
					return null;
				} else
					return (new Message(msg.getMessage("parking.notValid.theWeekend",null,LocaleContextHolder.getLocale())));
			} else {
				return (new Message(msg.getMessage("parking.notValid.workingDays", null, LocaleContextHolder.getLocale())));
			}
		} else {
			return (new Message(msg.getMessage("city.notValid.operatingHours",
					new String[] { hourStartCity, hourEndCity }, LocaleContextHolder.getLocale())));
		}
	}
}
