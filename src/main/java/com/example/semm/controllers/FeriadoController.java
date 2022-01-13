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

import com.example.semm.models.Feriado;
import com.example.semm.service.impl.FeriadoServiceImp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/feriado")
@CrossOrigin(origins="http://localhost:4200")
public class FeriadoController {
	
	@Autowired
    FeriadoServiceImp FeriadoServiceImp;

	@GetMapping
    public ArrayList<Feriado> obtenerFeriado(){
    	//listado de ciudades
        return FeriadoServiceImp.listar();
	}

	@PostMapping
	public ResponseEntity<Feriado> guardaHistorial(@RequestBody Feriado f){
		//creo una ciudad
		Feriado feriado= this.FeriadoServiceImp.guardaHistorial(f);
		return new ResponseEntity<Feriado>(feriado, HttpStatus.CREATED);
	    
	}
	
	@PostMapping("/saveList")
	public ResponseEntity<Feriado> saveFromList(@RequestBody String f) throws JsonMappingException, JsonProcessingException{
		//creo una ciudad
		// con esta solucion se crea un string que seria una lista de fechas(feriados). 
		// con el ".contains" se consulta si una fecha se encuentra dentro de esa lista de feriados.
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = mapper.writeValueAsString(f);
		System.out.println("contenido recibido: "+jsonStr);
		Feriado result = mapper.readValue(jsonStr, Feriado.class);
		// convertir una lista JSON a Java
		//Feriado result = mapper.readValue(f.split("["), Feriado.class);
		String fecha = "1 de Enero Sábado";
		System.out.println("contenido result: "+result.getFecha());
		System.out.println("contenido result boolean: "+result.getFecha().contains(fecha));
		//Feriado feriado= this.FeriadoServiceImp.guardaHistorial(f);
		return new ResponseEntity<Feriado>( HttpStatus.CREATED);
	    
	}
	
	@GetMapping( path = "/{fecha}")
    public boolean obtenerFeriadoPorFecha(@PathVariable("fecha") String fecha) {
		System.out.println("se ejecuto el metodo getFecha");
		String fechaConsulta = fecha.split("-")[0] +"/"+ fecha.split("-")[1];
		System.out.println("fecha consulta: "+ fechaConsulta);
        Optional<Feriado> f = this.FeriadoServiceImp.getPorFecha(fechaConsulta);
        System.out.println("formato de fecha obtenida"+ f);
        if (f.isEmpty()) {    
			System.out.println(" Es un dia habil");
    		return false;			
        }else {
    		System.out.println(" No es un dia habil");
        	return true;
        }
    }
}
