package com.example.semm.controllers;
import java.util.ArrayList;
import java.util.Optional;

import com.example.semm.models.Ciudad;
import com.example.semm.models.CuentaCorriente;
import com.example.semm.service.impl.CiudadServiceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ciudad")
public class CiudadController {
    @Autowired
    CiudadServiceImp ciudadServiceImp;
    
    @GetMapping
    public ArrayList<Ciudad> obtenerCiudad(){
    	//listado de ciudades
        return ciudadServiceImp.listar();
    }

    @PostMapping
    public ResponseEntity<Ciudad> guardaCiudad(@RequestBody Ciudad p){
    	//creo una ciudad
    	Ciudad per= this.ciudadServiceImp.guardarCiudad(p);
    	return new ResponseEntity<Ciudad>(per, HttpStatus.CREATED);
        
    }

    @GetMapping( path = "/{id}")
    public ResponseEntity<Optional<Ciudad>> obtenerUsuarioPorId(@PathVariable("id") Long id) {
    	//listo una ciudad por id
        Optional<Ciudad> per = this.ciudadServiceImp.listaPorId(id);
        return new ResponseEntity<Optional<Ciudad>>(per, HttpStatus.OK);
    }

    
    @PutMapping
    public ResponseEntity<Ciudad> updateUser(@RequestBody Ciudad ciudad) {
    System.out.println("Actualizando la ciudad " + ciudad.getId());
    Optional<Ciudad> currentCity = ciudadServiceImp.listaPorId(ciudad.getId());
    
    if (currentCity.isEmpty()) {
	    System.out.println("Ciudad with id " + ciudad.getId() + " not found");	    
	    return new ResponseEntity<Ciudad>(HttpStatus.NOT_FOUND);
    }else {
    	this.ciudadServiceImp.actualizar(ciudad);
    	return new ResponseEntity<Ciudad>(ciudad, HttpStatus.OK);
    }
    	
    }
    
    @DeleteMapping (path = "/{id}")
    public ResponseEntity<Ciudad> eliminarPorId(@PathVariable("id") Long id){
        boolean ok= this.ciudadServiceImp.eliminar(id);
        if (ok) {
        	System.out.println("No es posible eliminar, no se encuentra la ciudad con id " + id);
            return new ResponseEntity<Ciudad>(HttpStatus.NOT_FOUND);
        } else {
        	System.out.println("Se elimino la ciudad con id " + id);
        	return new ResponseEntity<Ciudad>(HttpStatus.NO_CONTENT);
        }
    }
}


    

