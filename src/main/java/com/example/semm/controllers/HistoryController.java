package com.example.semm.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.example.semm.models.History;
import com.example.semm.security.dto.Message;
import com.example.semm.service.impl.HistoryServiceImpl;

@RestController
@RequestMapping("/history")
@CrossOrigin(origins = "http://localhost:4200")
public class HistoryController {

	@Autowired
	HistoryServiceImpl historyServiceImp;
	
	private Logger logger = LoggerFactory.getLogger(HistoryController.class);

	@GetMapping
	public ArrayList<History> getAllHistorys() {
		// list of historys
		return historyServiceImp.getAll();
	}

	@PostMapping
	public ResponseEntity<?> saveHistory(@RequestBody History h) {
		// save a history
		this.logger.debug("Running saveHistory()");
		try {
			return new ResponseEntity<History>(this.historyServiceImp.saveHistory(h), HttpStatus.CREATED);
		} catch (Exception e) {
			this.logger.error("Error found{}", e);
			return new ResponseEntity<Message>(new Message("Error found:" + e),HttpStatus.NOT_FOUND);
		}
		

	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getHistoryByCurrentAccountId(@PathVariable("id") Long id) {
		// get history list by current account id
		this.logger.debug("Running getHistoryByCurrentAccountId()");
		
		try {
			List<History> history = this.historyServiceImp.getByCurrentAccountId(id);
			return new ResponseEntity<Iterable<History>>(history, HttpStatus.OK);
		} catch (Exception e) {
			this.logger.error("Error found{}", e);
			return new ResponseEntity<Message>(new Message("Error found:" + e),HttpStatus.NOT_FOUND);
		}
	}

}
