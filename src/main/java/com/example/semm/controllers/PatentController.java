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
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.semm.models.Patent;
import com.example.semm.models.User;
import com.example.semm.models.Parking;
import com.example.semm.security.dto.Message;
import com.example.semm.security.dto.NewPatentDTO;
import com.example.semm.service.impl.ParkingServiceImp;
import com.example.semm.service.impl.PatentServiceImpl;
import com.example.semm.service.impl.UserServiceImp;

@RestController
@RequestMapping("/patent")
@CrossOrigin(origins = "http://localhost:4200")
public class PatentController {
	@Autowired
	PatentServiceImpl patentServiceImp;

	@Autowired
	UserServiceImp userService;

	@Autowired
	ParkingServiceImp parkingService;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private MessageSource msg;

	@GetMapping
	public ArrayList<Patent> getAllPatents() {
		// list of patents
		return patentServiceImp.getAll();
	}

	@PostMapping("/save")
	public ResponseEntity<?> savePatent(@Valid @RequestBody NewPatentDTO newPatent, BindingResult result) {
		// creo una patente
		Optional<User> user = userService.existByUsername(newPatent.getUser().getUsername());
		Patent queryResult = patentServiceImp.existsByPatentAndUser(newPatent.getNumber(), user.get().getId());
		if (queryResult != null) {
			return new ResponseEntity<Message>(
					new Message(msg.getMessage("patent.exists", new String[] {newPatent.getNumber()}, LocaleContextHolder.getLocale())),
					HttpStatus.BAD_REQUEST);
		}

		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(e -> e.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		Patent p = new Patent(newPatent.getNumber(), user.get());
		user.get().getPatentList().add(p);
		userService.update(user.get());
		p = this.patentServiceImp.savePatent(p);
		return new ResponseEntity<Patent>(p, HttpStatus.CREATED);

	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long patentId) {
		Optional<Patent> patent = this.patentServiceImp.getById(patentId);

		if (this.parkingService.parkingStartedWithPatent(patent.get().getNumber(), patent.get().getUser().getId()))
			return new ResponseEntity<Message>(
					new Message(msg.getMessage("patent.startedParking", null, LocaleContextHolder.getLocale())),HttpStatus.BAD_REQUEST);
		this.patentServiceImp.delete(patentId);
		return new ResponseEntity<Message>(
				new Message(msg.getMessage("patent.deleted", new String[] {patent.get().getNumber()}, LocaleContextHolder.getLocale())), HttpStatus.OK);

	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Optional<Patent>> getPatentbyId(@PathVariable("id") Long id) {
		// get patente by id
		Optional<Patent> patent = this.patentServiceImp.getById(id);
		return new ResponseEntity<Optional<Patent>>(patent, HttpStatus.OK);
	}

	@PutMapping("/{id}") // update patent
	public ResponseEntity<?> updatePatent(@Valid @RequestBody NewPatentDTO patentDTO, BindingResult result,
			@PathVariable(value = "id") Long patentId) {
		
		if (result.hasErrors()) {
			return new ResponseEntity<Message>(new Message(result.getFieldError().getDefaultMessage()),
					HttpStatus.BAD_REQUEST);
		}
		
		Optional<Patent> patentPrevious = patentServiceImp.getById(patentDTO.getId());
		Parking startedPatent = parkingService.findByPatentStarted(patentPrevious.get().getNumber());
		if (startedPatent != null) {
			return new ResponseEntity<Message>(
					new Message(msg.getMessage("patent.update.parking.started", new String[] {patentPrevious.get().getNumber()}, LocaleContextHolder.getLocale())),
					HttpStatus.BAD_REQUEST);
		}

		// convert DTO to Entity
		Patent patentRequest = modelMapper.map(patentDTO, Patent.class);

		// busco el User y asocio la relacion
		System.out.println("id del usuario en patente : " + patentRequest.getUser().getUsername());
		Optional<User> idUser = userService.existByUsername(patentRequest.getUser().getUsername());
		patentRequest.setUser(idUser.get());
		
		//verifica si la patente ya esta registrada
		Patent exist = this.patentServiceImp.existsByPatentAndUser(patentDTO.getNumber(),idUser.get().getId());
		if(exist != null) {
			return new ResponseEntity<Message>(
					new Message(msg.getMessage("patent.exists", new String[] {exist.getNumber()}, LocaleContextHolder.getLocale())),
					HttpStatus.BAD_REQUEST);
		}

		System.out.println("contenido de id recibida como parametro: " + patentId);
		Patent patent = patentServiceImp.update(patentRequest, patentId);

		if (patent == null) {
			return ResponseEntity.notFound().build();
		} else {

			return new ResponseEntity<Message>(new Message(msg.getMessage("patent.updated", null, LocaleContextHolder.getLocale())), HttpStatus.CREATED);
		}

	}
}
