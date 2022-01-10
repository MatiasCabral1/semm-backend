package com.example.semm.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.semm.models.Ciudad;
import com.example.semm.models.Historial;
import com.example.semm.service.impl.HistorialServiceImpl;

@RestController
@RequestMapping("/historial")
@CrossOrigin(origins="http://localhost:4200")
public class HistorialController {
	
	@Autowired
    HistorialServiceImpl historialServiceImp;
	 
	@GetMapping
	    public ArrayList<Historial> obtenerHistorial(){
	    	//listado de ciudades
	        return historialServiceImp.listar();
	}
	
	@PostMapping
    public ResponseEntity<Historial> guardaHistorial(@RequestBody Historial h){
    	//creo una ciudad
		Historial historial= this.historialServiceImp.guardaHistorial(h);
    	return new ResponseEntity<Historial>(historial, HttpStatus.CREATED);
        
    }
	
	@GetMapping( path = "/{id}")
    public ResponseEntity<Iterable<Historial>> obtenerHistorialPorId(@PathVariable("id") Long id) {
    	//recibe la id de una cuenta corriente. 
		List<Historial> historial= this.historialServiceImp.listaPorId(id);
		System.out.print("historial: "+ historial);
        return new ResponseEntity<Iterable<Historial>>(historial, HttpStatus.OK);
    }
	
	

}
