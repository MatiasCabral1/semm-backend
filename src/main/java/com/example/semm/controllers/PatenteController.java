package com.example.semm.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.semm.models.Patente;
import com.example.semm.models.nuevaPatente;
import com.example.semm.security.dto.Mensaje;
import com.example.semm.security.model.Usuario;
import com.example.semm.service.impl.PatenteServiceImpl;
import com.example.semm.service.impl.personaServiceImp;

@RestController
@RequestMapping("/patente")
@CrossOrigin(origins="http://localhost:4200")
public class PatenteController {
	@Autowired
    PatenteServiceImpl patenteServiceImp;
	
	@Autowired
     personaServiceImp personaService;
    
    @GetMapping
    public ArrayList<Patente> obtenerPatente(){
    	//listado de patentes
        return patenteServiceImp.listar();
    }
    
    @GetMapping("/patente/{id}")
    public ArrayList<Patente> obtenerPatentePorIdUsuario(@PathVariable("id") Long id){
    	//listado de patentes
      ArrayList<Patente> patentes = patenteServiceImp.listar();
      ArrayList<Patente> patentesResult = new ArrayList<Patente>();
      for (Patente pat: patentes) {
    	  if(pat.getUsuario().getId() == id){
    		  patentesResult.add(pat);
    	  }
      }
      return patentesResult;
    }

    //@PreAuthorize("hasRoles('ADMIN')") -> con este codigo solo los usuarios tipo "admin" pueden ejecutar este metodo.
    @PostMapping("/guardar")
    public ResponseEntity<?> guardaPatente(@Valid @RequestBody nuevaPatente nuevaPatente, BindingResult result){
    	//creo una patente
    	Optional<Usuario> per= personaService.listaPorUsername(nuevaPatente.getUser_name());
    	System.out.println("resultado de la consulta: "+patenteServiceImp.existsByPatenteAndUsuario(nuevaPatente.getNumber(),per.get().getId()));
    	Patente consulta = patenteServiceImp.existsByPatenteAndUsuario(nuevaPatente.getNumber(),per.get().getId());
    	if(consulta!=null) {
    		System.out.println("return patente registrada");
    		 return new ResponseEntity<Mensaje>(new Mensaje("la patente "+nuevaPatente.getNumber()+" ya existe en su listado de patente"), HttpStatus.BAD_REQUEST);
    	}

    	Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(e -> e.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
    	
    	Patente p = new Patente(nuevaPatente.getNumber(),per.get());
    	per.get().getPatenteList().add(p);
    	personaService.actualizar(per.get());
    	p = this.patenteServiceImp.guardarPatente(p);
    	return new ResponseEntity<Patente>(p, HttpStatus.CREATED);
        
    }
    @GetMapping( path = "/{id}")
    public ResponseEntity<Optional<Patente>> obtenerPatentePorId(@PathVariable("id") Long id) {
    	//listo una patente por id
        Optional<Patente> patente = this.patenteServiceImp.listaPorId(id);
        return new ResponseEntity<Optional<Patente>>(patente, HttpStatus.OK);
    }

    //@PreAuthorize("hasRoles('ADMIN')")
    @DeleteMapping (path = "/{id}")
    public ResponseEntity<Patente> eliminarPorId(@PathVariable("id") Long id){
    	//eliminar por id
        boolean ok= this.patenteServiceImp.eliminar(id);
        if (!ok) {
        	System.out.println("No es posible eliminar, no se encuentra el usuario con id " + id);
            return new ResponseEntity<Patente>(HttpStatus.NOT_FOUND);
        } else {
        	System.out.println("Se elimino el usuario con id " + id);
        	return new ResponseEntity<Patente>(HttpStatus.NO_CONTENT);
        }
    }
    
    //@PreAuthorize("hasRoles('ADMIN')")
    @PutMapping // actualizar patente
    public ResponseEntity<Patente> updatePatente(@RequestBody Patente patente) {
    System.out.println("Actualizando la patente " + patente.getId());
    Optional<Patente> currentUser = patenteServiceImp.listaPorId(patente.getId());
    
    if (currentUser.isEmpty()) {
	    System.out.println("patente with id " + patente.getId() + " not found");	    
	    return new ResponseEntity<Patente>(HttpStatus.NOT_FOUND);
    }else {
    	this.patenteServiceImp.actualizar(patente);
    	return new ResponseEntity<Patente>(patente, HttpStatus.OK);
    }
    	
    }
}
