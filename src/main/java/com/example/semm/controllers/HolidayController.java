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

import com.example.semm.models.Holiday;
import com.example.semm.service.impl.HolidayServiceImp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/holiday")
@CrossOrigin(origins="http://localhost:4200")
public class HolidayController {
	
	@Autowired
    HolidayServiceImp holidayServiceImp;

	@GetMapping
    public ArrayList<Holiday> getAllHoldays(){
    	//list of holidays
        return holidayServiceImp.getAll();
	}

	@PostMapping
	public ResponseEntity<Holiday> saveHoliday(@RequestBody Holiday f){
		//save a holiday
		Holiday holiday= this.holidayServiceImp.save(f);
		return new ResponseEntity<Holiday>(holiday, HttpStatus.CREATED);
	    
	}
	
	
}

