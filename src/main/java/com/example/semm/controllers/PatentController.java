package com.example.semm.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.example.semm.models.Patent;
import com.example.semm.models.NewPatentDTO;
import com.example.semm.models.Parking;
import com.example.semm.security.dto.Message;
import com.example.semm.security.model.User;
import com.example.semm.service.impl.ParkingServiceImp;
import com.example.semm.service.impl.PatentServiceImpl;
import com.example.semm.service.impl.UserServiceImp;

@RestController
@RequestMapping("/patent")
@CrossOrigin(origins="http://localhost:4200")
public class PatentController {
	@Autowired
    PatentServiceImpl patentServiceImp;
	
	@Autowired
     UserServiceImp userService;
	
	@Autowired
    ParkingServiceImp parkingService;
	@Autowired
	private ModelMapper modelMapper;
    
    @GetMapping
    public ArrayList<Patent> getAllPatents(){
    	//list of patents
        return patentServiceImp.getAll();
    }
    
    /*@GetMapping("/patent/{id}")
    public ArrayList<Patent> obtenerPatentePorIdUsuario(@PathVariable("id") Long id){
    	//listado de patentes
      ArrayList<Patent> patentes = patentServiceImp.getAll();
      ArrayList<Patent> patentesResult = new ArrayList<Patent>();
      for (Patent pat: patentes) {
    	  if(pat.getUsuario().getId() == id){
    		  patentesResult.add(pat);
    	  }
      }
      return patentesResult;
    }*/

  
    @PostMapping("/save") 
    public ResponseEntity<?> savePatent(@Valid @RequestBody NewPatentDTO newPatent, BindingResult result){
    	//creo una patente
    	Optional<User> user= userService.existByUsername(newPatent.getUser_name());
    	Patent queryResult = patentServiceImp.existsByPatentAndUser(newPatent.getNumber(),user.get().getId());
    	if(queryResult!=null) {
    		 return new ResponseEntity<Message>(new Message("la patente "+newPatent.getNumber()+" ya existe en su listado de patente"), HttpStatus.BAD_REQUEST);
    	}

    	Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(e -> e.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
    	
    	Patent p = new Patent(newPatent.getNumber(),user.get());
    	user.get().getPatentList().add(p);
    	userService.update(user.get());
    	p = this.patentServiceImp.savePatent(p);
    	return new ResponseEntity<Patent>(p, HttpStatus.CREATED);
        
    }
    
    @PostMapping("/delete")
    public ResponseEntity<?> deletePatent(@RequestBody NewPatentDTO newPatent){
    	//delete patent of a user
    	Optional<User> user= userService.existByUsername(newPatent.getUser_name());
    	
    	if(this.parkingService.parkingStartedWithPatent(newPatent.getNumber(),newPatent.getUser_name())) 
    		return new ResponseEntity<Message>(new Message("No se puede eliminar una patente con estacionamiento iniciado"), HttpStatus.BAD_REQUEST);
    	
    	Patent queryResult = patentServiceImp.existsByPatentAndUser(newPatent.getNumber(),user.get().getId());
    	if(queryResult!=null) {
    		System.out.println("patente encontrada");
    		user.get().getPatentList().remove(queryResult);
        	userService.update(user.get());
        	patentServiceImp.delete(queryResult.getId());
        	return new ResponseEntity<Message>(new Message("la patente fue eliminada exitosamente"), HttpStatus.OK);
    	}else {
    		return new ResponseEntity<Message>(new Message("la patente "+newPatent.getNumber()+" no se encuentra registrada"), HttpStatus.BAD_REQUEST);
    	}
    	
        
    }
    @GetMapping( path = "/{id}")
    public ResponseEntity<Optional<Patent>> getPatentbyId(@PathVariable("id") Long id) {
    	//get patente by id
        Optional<Patent> patent = this.patentServiceImp.getById(id);
        return new ResponseEntity<Optional<Patent>>(patent, HttpStatus.OK);
    }

    @DeleteMapping (path = "/{id}")
    public ResponseEntity<Patent> deleteById(@PathVariable("id") Long id){
    	//delete by id
        boolean ok= this.patentServiceImp.delete(id);
        if (!ok) {
        	System.out.println("No es posible eliminar, no se encuentra el usuario con id " + id);
            return new ResponseEntity<Patent>(HttpStatus.NOT_FOUND);
        } else {
        	System.out.println("Se elimino el usuario con id " + id);
        	return new ResponseEntity<Patent>(HttpStatus.NO_CONTENT);
        }
    }
    
    @PutMapping // update patent
    public ResponseEntity<?> updatePatent(@Valid @RequestBody NewPatentDTO patentDTO, BindingResult result) {
    System.out.println("Actualizando la patente " + patentDTO.getId());
    
    if (result.hasErrors()) {
    	return new ResponseEntity<Message>(new Message(result.getFieldError().getDefaultMessage()),
				HttpStatus.BAD_REQUEST);
	}
    
    Optional<Patent> patentPrevious = patentServiceImp.getById(patentDTO.getId());
    System.out.println("id de patente previa: "+ patentPrevious.get().getNumber());
    Parking startedPatent=parkingService.findByPatentStarted(patentPrevious.get().getNumber());
	if(startedPatent!=null){
		return new ResponseEntity<Message>(new Message("No se puede editar esta patente porque tiene un estacionamiento iniciado"), HttpStatus.BAD_REQUEST);
	}
    
	//convert DTO to Entity
	Patent patentRequest = modelMapper.map(patentDTO, Patent.class);
	Patent patent = patentServiceImp.update(patentRequest);

	if (patent == null) {
		return ResponseEntity.notFound().build();
	} else {
		
		return new ResponseEntity<Message>(new Message("patente actualizada!"), HttpStatus.CREATED);
	}
		
    }
}
