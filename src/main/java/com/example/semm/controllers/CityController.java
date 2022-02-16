package com.example.semm.controllers;

import java.util.ArrayList;
import java.util.Optional;

import com.example.semm.models.City;
import com.example.semm.security.dto.Message;
import com.example.semm.service.impl.CityServiceImp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/city")
public class CityController {
    @Autowired
    CityServiceImp ciudadServiceImp;
    
    private Logger logger = LoggerFactory.getLogger(CityController.class);

    @GetMapping
    public ArrayList<City> getAllCitys() {
        // list of citys
        return ciudadServiceImp.getAll();
    }

    @PostMapping
    public ResponseEntity<City> saveCity(@RequestBody City city) {
        // save a city
        return new ResponseEntity<City>(this.ciudadServiceImp.saveCity(city), HttpStatus.CREATED);

    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getCityById(@PathVariable("id") Long id) {
        // get city by id
    	this.logger.debug("Running getCityById()");
    	try {
    		logger.info("Correct executed");
    		 return new ResponseEntity<Optional<City>>(this.ciudadServiceImp.getById(id), HttpStatus.OK);
		} catch (Exception e) {
			this.logger.error("Error found{}", e);
			return new ResponseEntity<Message>(new Message("Error found:" + e),HttpStatus.NOT_FOUND);
		}
       
    }

    @PutMapping
    public ResponseEntity<?> updateCity(@RequestBody City city) {
    	this.logger.debug("Running updateCity()");
    	try {
    		Optional<City> currentCity = ciudadServiceImp.getById(city.getId());

            if (currentCity.isEmpty()) {
            	logger.info("Ciudad with id " + city.getId() + " not found");
                return new ResponseEntity<City>(HttpStatus.NOT_FOUND);
            } else {
                this.ciudadServiceImp.update(city);
                return new ResponseEntity<City>(city, HttpStatus.OK);
            }
		} catch (Exception e) {
			this.logger.error("Error found{}", e);
			return new ResponseEntity<Message>(new Message("Error found:" + e),HttpStatus.NOT_FOUND);
		}
        

    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<City> deleteById(@PathVariable("id") Long id) {
        boolean ok = this.ciudadServiceImp.delete(id);
        if (ok) {
        	logger.info("No es posible eliminar, no se encuentra la ciudad con id: " + id);
            return new ResponseEntity<City>(HttpStatus.NOT_FOUND);
        } else {
        	logger.info("Se elimino la ciudad con id: " + id);
            return new ResponseEntity<City>(HttpStatus.NO_CONTENT);
        }
    }
}
