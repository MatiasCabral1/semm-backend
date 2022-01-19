package com.example.semm.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.semm.models.Ciudad;
import com.example.semm.models.Estacionamiento;
import com.example.semm.models.Feriado;
import com.example.semm.security.dto.Mensaje;
import com.example.semm.security.dto.TiempoPrecioDTO;
import com.example.semm.security.model.Usuario;
import com.example.semm.service.impl.EstacionamientoServiceImp;
import com.example.semm.service.impl.personaServiceImp;
import com.example.semm.services.CiudadService;
import com.example.semm.services.FeriadoService;

@RestController
@RequestMapping("/estacionamiento")
@CrossOrigin(origins="http://localhost:4200")
public class EstacionamientoController {
	@Autowired
	EstacionamientoServiceImp estServiceImp;
	
	@Autowired
    personaServiceImp personaService;
	
	@Autowired
    CiudadService ciudadService;
	
	@Autowired
    EstacionamientoServiceImp estService;
	
	@Autowired
	FeriadoService feriadoService;
	
	@GetMapping
    public ArrayList<Estacionamiento> obtenerCiudad(){
    	//listado de estacionamientos
        return estServiceImp.listar();
    }
	
	@PostMapping("/getEstado")
    public boolean getEstado(@RequestBody String username){
    	//listado de estacionamientos
		System.out.println("Metodo: /getEstado");
		System.out.println("Nombre de usuario recibido: " + username);
		Optional<Estacionamiento> est = estServiceImp.getPorEstado(username);
		if (est.isEmpty()) {    
			System.out.println("no hay un estacionamiento iniciado para el usuario: "+username);
    		return false;			
        }else {
    		System.out.println("se ejecuto el getEstado: " + estServiceImp.getPorEstado(username));
        	return true;
        }

    }
	
	@PostMapping("/getTime")
    public TiempoPrecioDTO getTime(@RequestBody String username){
    	//listado de estacionamientos
		//retorna en milisegundos el tiempo transcurrido.
		System.out.println("Metodo: /getTime");
		Optional<Usuario> per = this.personaService.listaPorUsername(username);
		Optional<Estacionamiento> est = estServiceImp.getPorEstado(username);
		ArrayList <Ciudad> ciudad = this.ciudadService.listar();
		if(per.isEmpty()) {
			System.out.println("El nombre de usuario ingresado no existe.");	
			return null;
		}else {
			TiempoPrecioDTO datos = est.get().getDatosCobroActual(ciudad.get(0));
			return datos;
		}
		
    }
	

    @PostMapping("/nuevo")
    public ResponseEntity<?> guardaEstacionamiento(@RequestBody Estacionamiento e){
    	//creo un estacionamiento
    	//verifica que el la patente del estacionamiento recibido no se haya iniciado anteriormente con otro o el mismo usuario. 
    	//en caso de que ya haya sido iniciada se devuelve "no content"
    	boolean existe = false;
    	System.out.println("Metodo: /nuevo");
    	Optional<Usuario> per= personaService.listaPorUsername(e.getUsername());
    	
    	//pregunta si la patente que se ingreso ya fue iniciada
    	//verificar si hay un estacionamiento con la patente ingresada y estado iniciado = true.
    	ArrayList<Estacionamiento> listaEst = estService.listar();
    	for(Estacionamiento est: listaEst) {
    		if(est.getPatente().equals(e.getPatente()) && est.getIniciado() == true) {
    			System.out.println("ERROR: la patente ya tiene un estacionamiento iniciado.");
    			existe = true;
    		}
    	}
    	if(!existe) {
    	    	Optional<Ciudad>ciudad=ciudadService.listaPorId(Long.parseLong("1"));
    	    	Optional<Usuario>usuario=personaService.listaPorUsername(e.getUsername());
    	    	Iterable<Feriado>feriados=feriadoService.listar();
    	    	Mensaje msj= Estacionamiento.validaciones(ciudad.get(), feriados);
    	    	double saldoCuenta=usuario.get().getCuentaCorriente().getSaldo();
    	       if(saldoCuenta>= ciudad.get().getValorHora() ) {
    	    	   if(msj==null) {
    	    		   Estacionamiento est= this.estServiceImp.guardarEstacionamiento(e);
    	    		   per.get().setEstacionamiento(e);
	    	           this.personaService.actualizar(per.get());
	    	           return new ResponseEntity<Estacionamiento>(est, HttpStatus.CREATED);
    	    	     }
    	    	   else return new ResponseEntity<Mensaje>(msj, HttpStatus.BAD_REQUEST);
    	       }
    	     else return new ResponseEntity<Mensaje>(new Mensaje("El saldo de la cuenta es insuficiente"), HttpStatus.BAD_REQUEST);
    	    }
    	else return new ResponseEntity<Mensaje>(new Mensaje("La patente ya tiene un estacionamiento iniciado"), HttpStatus.BAD_REQUEST);
    		}

    @GetMapping( path = "/{id}")
    public ResponseEntity<Optional<Estacionamiento>> obtenerEstacionamientoPorId(@PathVariable("id") Long id) {
    	//listo una estacionamiento por id
    	
        Optional<Estacionamiento> per = this.estServiceImp.listaPorId(id);
        return new ResponseEntity<Optional<Estacionamiento>>(per, HttpStatus.OK);
    }
    
    @PostMapping( path = "/finalizarEstacionamiento")
    public ResponseEntity<Estacionamiento> finalizarEstacionamiento( @RequestBody String username) {
    	//listo una estacionamiento por id
    	System.out.println("Metodo: /finalizarEstacionamiento");
    	Optional<Estacionamiento> currentEst = estServiceImp.getPorEstado(username);
    	if (currentEst.isEmpty()) {    
    		System.out.println("No hay ningun estacionamiento iniciado");
    	    return new ResponseEntity<Estacionamiento>(HttpStatus.NOT_FOUND);
        }else {
        	currentEst.get().setIniciado(false);
        	this.estServiceImp.actualizar(currentEst.get());
        	System.out.println("Estado actualizado");
        	return new ResponseEntity<Estacionamiento>(currentEst.get(), HttpStatus.OK);
        }

    }

    
    @PutMapping
    public ResponseEntity<Estacionamiento> updateEstacionamiento(@RequestBody Estacionamiento est) {
    System.out.println("Actualizando el estacionamiento " + est.getId());
    Optional<Estacionamiento> currentEst = estServiceImp.listaPorId(est.getId());
    
    if (currentEst.isEmpty()) {
	    System.out.println("Ciudad with id " + est.getId() + " not found");	    
	    return new ResponseEntity<Estacionamiento>(HttpStatus.NOT_FOUND);
    }else {
    	this.estServiceImp.actualizar(est);
    	return new ResponseEntity<Estacionamiento>(est, HttpStatus.OK);
    }
    	
    }
    
    @DeleteMapping (path = "/{id}")
    public ResponseEntity<Estacionamiento> eliminarPorId(@PathVariable("id") Long id){
        boolean ok= this.estServiceImp.eliminar(id);
        if (ok) {
        	System.out.println("No es posible eliminar, no se encuentra el estacionamiento con id " + id);
            return new ResponseEntity<Estacionamiento>(HttpStatus.NOT_FOUND);
        } else {
        	System.out.println("Se elimino el estacionamiento con id " + id);
        	return new ResponseEntity<Estacionamiento>(HttpStatus.NO_CONTENT);
        }
    }
}
