package com.example.semm.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.semm.models.Ciudad;
import com.example.semm.models.Feriado;
import com.example.semm.security.enums.RolNombre;
import com.example.semm.security.model.Rol;
import com.example.semm.security.service.RolService;
import com.example.semm.service.impl.FeriadoServiceImp;
import com.example.semm.services.CiudadService;

@Component
public class SetData implements CommandLineRunner {

    @Autowired
    RolService rolService;
    @Autowired
    CiudadService ciudadService;
    @Autowired
    FeriadoServiceImp FeriadoServiceImp;

    @Override
    public void run(String... args) throws Exception {
    	String listaFeriados = "01/01,28/02,01/03,24/03,02/04,15/04,01/05,25/05,20/06,09/07,08/12,25/12,17/06,15/08,10/10,20/11,07/10,21/11,09/12";   

    	
        Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
        Rol rolUser = new Rol(RolNombre.ROLE_USER);
        rolService.save(rolAdmin);
        rolService.save(rolUser);
       
    	
    	List<Ciudad> listaCiudades = ciudadService.listar();
    	if(listaCiudades.isEmpty()) {
    		Ciudad nuevaCiudad = new Ciudad("8-20",10);
        	ciudadService.guardarCiudad(nuevaCiudad);
    	}
    	
    	if(FeriadoServiceImp.listar().isEmpty()) {
    		String[] list = listaFeriados.split((","));
    		for(String elem: list) {
    			FeriadoServiceImp.save(new Feriado(elem));
    		}
    	}
    }
}
