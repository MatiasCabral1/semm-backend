package com.example.semm.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.semm.models.Ciudad;
import com.example.semm.security.enums.RolNombre;
import com.example.semm.security.model.Rol;
import com.example.semm.security.service.RolService;
import com.example.semm.services.CiudadService;

@Component
public class CreateRoles implements CommandLineRunner {

    @Autowired
    RolService rolService;
    @Autowired
    CiudadService ciudadService;

    @Override
    public void run(String... args) throws Exception {
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
         
    }
}
