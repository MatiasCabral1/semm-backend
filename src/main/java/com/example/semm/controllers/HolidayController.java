package com.example.semm.controllers;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.semm.models.Holiday;
import com.example.semm.security.dto.Message;
import com.example.semm.service.impl.HolidayServiceImp;

@RestController
@RequestMapping("/holiday")
@CrossOrigin(origins = "http://localhost:4200")
public class HolidayController {

	@Autowired
	HolidayServiceImp holidayServiceImp;
	
	private Logger logger = LoggerFactory.getLogger(HolidayController.class);

	@GetMapping
	public ArrayList<?> getAllHoldays() {
		// list of holidays
		this.logger.debug("Running getAllHoldays()");
		try {
			return holidayServiceImp.getAll();
		} catch (Exception e) {
			this.logger.error("Error found{}", e);
			return null;
		}
		
	}

	@PostMapping
	public ResponseEntity<?> saveHoliday(@RequestBody Holiday f) {
		// save a holiday
		this.logger.debug("Running saveHoliday()");
		try {
			Holiday holiday = this.holidayServiceImp.save(f);
			return new ResponseEntity<Holiday>(holiday, HttpStatus.CREATED);
		} catch (Exception e) {
			this.logger.error("Error found{}", e);
			return new ResponseEntity<Message>(new Message("Error found:" + e),HttpStatus.NOT_FOUND);
		}

	}

}
