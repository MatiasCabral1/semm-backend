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

import com.example.semm.models.City;
import com.example.semm.models.Parking;
import com.example.semm.models.Holiday;
import com.example.semm.security.dto.Message;
import com.example.semm.security.dto.TimePriceDTO;
import com.example.semm.security.model.User;
import com.example.semm.service.impl.ParkingServiceImp;
import com.example.semm.service.impl.UserServiceImp;
import com.example.semm.services.CityService;
import com.example.semm.services.HolidayService;

@RestController
@RequestMapping("/parking")
@CrossOrigin(origins="http://localhost:4200")
public class ParkingController {
	@Autowired
	ParkingServiceImp parkingServiceImp;
	
	@Autowired
    UserServiceImp userService;
	
	@Autowired
    CityService cityService;
	
	@Autowired
	HolidayService holidayService;
	
	@GetMapping
    public ArrayList<Parking> getAllCity(){
    	//listado de estacionamientos
        return parkingServiceImp.getAll();
    }
	
	@GetMapping("/getParkingStartedByUser/{username}")
    public boolean getParkingStartedByUser(@PathVariable("username") String username){
    	//list of parking of user "username"
		System.out.println("Metodo: /getParkingStartedByUser");
		Optional<Parking> queryResult = parkingServiceImp.getParkingStartedByUser(username);
		if (queryResult.isEmpty()) {    
			System.out.println("no hay un estacionamiento iniciado para el usuario: "+username);
    		return false;			
        }else {
    		System.out.println("se ejecuto el getEstado: " + parkingServiceImp.getParkingStartedByUser(username));
        	return true;
        }

    }
	
	@GetMapping("/getTime/{username}")
    public TimePriceDTO getTime(@PathVariable("username") String username){
		//retorna en milisegundos el tiempo transcurrido.
		System.out.println("Metodo: /getTime");
		Optional<User> user = this.userService.existByUsername(username);
		Optional<Parking> queryResult = parkingServiceImp.getParkingStartedByUser(username);
		ArrayList <City> city = this.cityService.getAll();
		if(user.isEmpty()) {
			System.out.println("El nombre de usuario ingresado no existe.");	
			return null;
		}else {
			TimePriceDTO data = queryResult.get().getCurrentPaymentDetails(city.get(0));
			return data;
		}
		
    }
	

    @PostMapping("/save")
    public ResponseEntity<?> saveParking(@RequestBody Parking parking){
    	//creo un estacionamiento
    	//verifica que el la patente del estacionamiento recibido no se haya iniciado anteriormente con otro o el mismo usuario. 
    	//en caso de que ya haya sido iniciada se devuelve "no content"
    	System.out.println("Metodo: /nuevo");
    	boolean exist = false;
    	Optional<User> user= userService.existByUsername(parking.getUsername());
    	
    	// verifico si la patente ingresada no tiene un estacionamiento iniciado
    	Parking startedParking = this.parkingServiceImp.findByPatentStarted(parking.getPatent());
    	if(startedParking == null) {
    	    	Optional<City> cityObt = cityService.getById(Long.parseLong("1"));
    	    	Optional<User> userObt = userService.existByUsername(parking.getUsername());
    	    	Iterable<Holiday> holidaysObt = holidayService.getAll();
    	    	Message msj= Parking.validations(cityObt.get(), holidaysObt);
    	    	double accountBalance = userObt.get().getCurrentAccount().getBalance();
    	       if(accountBalance>= cityObt.get().getValueHours() ) {
    	    	   if(msj==null) {
    	    		   parking.setUser(userObt.get());
    	    		   this.parkingServiceImp.saveParking(parking);
    	    		   user.get().setParking(parking);
	    	           this.userService.update(user.get());
	    	           return new ResponseEntity<Message>(new Message("Estacionamiento iniciado exitosamente"), HttpStatus.CREATED);
    	    	     }
    	    	   else return new ResponseEntity<Message>(msj, HttpStatus.BAD_REQUEST);
    	       }
    	     else return new ResponseEntity<Message>(new Message("El saldo de la cuenta es insuficiente"), HttpStatus.BAD_REQUEST);
    	    }
    	else return new ResponseEntity<Message>(new Message("La patente ya tiene un estacionamiento iniciado"), HttpStatus.BAD_REQUEST);
    		}

    @GetMapping( path = "/{id}")
    public ResponseEntity<Optional<Parking>> getParkingById(@PathVariable("id") Long id) {
    	//listo una estacionamiento por id
    	
        Optional<Parking> user = this.parkingServiceImp.getById(id);
        return new ResponseEntity<Optional<Parking>>(user, HttpStatus.OK);
    }
    
    @GetMapping(path = "/endParking/{username}")
    public ResponseEntity<?> endParking(@PathVariable("username") String username) {
    	//listo una estacionamiento por id
    	System.out.println("Metodo: /endParking");
    	Optional<Parking> queryResult = parkingServiceImp.getParkingStartedByUser(username);
    	if (queryResult.isEmpty()) {    
    	    return new ResponseEntity<Parking>(HttpStatus.NOT_FOUND);
        }else {
        	Optional<City> cityObt = cityService.getById(Long.parseLong("1"));
	    	Iterable<Holiday> holidays = holidayService.getAll();
	    	Message msj= Parking.validations(cityObt.get(), holidays);
	    	if(msj==null) {
	    		queryResult.get().setStarted(false);
	    		this.userService.debitBalance(username);
	        	this.parkingServiceImp.update(queryResult.get());
	        	return new ResponseEntity<Parking>(queryResult.get(), HttpStatus.OK);
	    	}
	    	   else return new ResponseEntity<Message>(msj, HttpStatus.BAD_REQUEST);
        }

    }

    
    @PutMapping
    public ResponseEntity<Parking> updateParking(@RequestBody Parking parking) {
    System.out.println("Actualizando el estacionamiento " + parking.getId());
    Optional<Parking> currentParking = parkingServiceImp.getById(parking.getId());
    
    if (currentParking.isEmpty()) {
	    System.out.println("parking with id " + parking.getId() + " not found");	    
	    return new ResponseEntity<Parking>(HttpStatus.NOT_FOUND);
    }else {
    	this.parkingServiceImp.update(parking);
    	return new ResponseEntity<Parking>(parking, HttpStatus.OK);
    }
    	
    }
    
    @DeleteMapping (path = "/{id}")
    public ResponseEntity<Parking> deleteById(@PathVariable("id") Long id){
        boolean ok= this.parkingServiceImp.delete(id);
        if (ok) {
        	System.out.println("No es posible eliminar, no se encuentra el estacionamiento con id " + id);
            return new ResponseEntity<Parking>(HttpStatus.NOT_FOUND);
        } else {
        	System.out.println("Se elimino el estacionamiento con id " + id);
        	return new ResponseEntity<Parking>(HttpStatus.NO_CONTENT);
        }
    }
}
