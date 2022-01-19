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
    	String listaFeriados = "1/1,28/2,1/3,24/3,2/4,15/4,1/5,25/5,20/6,9/7,8/12,25/12,17/6,15/8,10/10,20/11,7/10,21/11,9/12";   

    	
       /* Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
        Rol rolUser = new Rol(RolNombre.ROLE_USER);
        rolService.save(rolAdmin);
        rolService.save(rolUser);
       */
    	
    	/*List<Ciudad> listaCiudades = ciudadService.listar();
    	if(listaCiudades.isEmpty()) {
    		Ciudad nuevaCiudad = new Ciudad("8-20",10);
        	ciudadService.guardarCiudad(nuevaCiudad);
    	}
    	*/
    	if(FeriadoServiceImp.listar().isEmpty()) {
    		String[] list = listaFeriados.split((","));
    		for(String elem: list) {
    			FeriadoServiceImp.save(new Feriado(elem));
    			System.out.println("lista spliteada: "+ elem);
    		}
    	}
    }
}
