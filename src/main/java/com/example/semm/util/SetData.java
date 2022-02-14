package com.example.semm.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.semm.models.City;
import com.example.semm.models.Holiday;
import com.example.semm.models.Rol;
import com.example.semm.security.enums.RolName;
import com.example.semm.service.impl.HolidayServiceImp;
import com.example.semm.services.CityService;
import com.example.semm.services.RolService;

@Component
public class SetData implements CommandLineRunner {

	@Autowired
	RolService rolService;
	@Autowired
	CityService ciudadService;
	@Autowired
	HolidayServiceImp HolidayServiceImp;

	@Override
	public void run(String... args) throws Exception {
		String listaFeriados = "01/01,28/02,01/03,24/03,02/04,15/04,01/05,25/05,20/06,09/07,08/12,25/12,17/06,15/08,10/10,20/11,07/10,21/11,09/12";

		if (rolService.getAll().isEmpty()) {
			Rol rolAdmin = new Rol(RolName.ROLE_ADMIN);
			Rol rolUser = new Rol(RolName.ROLE_USER);
			rolService.save(rolAdmin);
			rolService.save(rolUser);
		}

		List<City> listaCiudades = ciudadService.getAll();
		if (listaCiudades.isEmpty()) {
			City nuevaCiudad = new City("8-20", 10);
			ciudadService.saveCity(nuevaCiudad);
		}

		if (HolidayServiceImp.getAll().isEmpty()) {
			String[] list = listaFeriados.split((","));
			for (String elem : list) {
				HolidayServiceImp.save(new Holiday(elem));
			}
		}
	}
}
