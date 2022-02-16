package com.example.semm.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.example.semm.models.CurrentAccount;
import com.example.semm.models.Patent;
import com.example.semm.models.User;
import com.example.semm.security.dto.CurrentAccountDTO;
import com.example.semm.security.dto.Message;
import com.example.semm.security.dto.DataAccountUserDTO;
import com.example.semm.service.impl.ParkingServiceImp;
import com.example.semm.service.impl.UserServiceImp;
import com.example.semm.services.CityService;
import com.example.semm.services.CurrentAccountService;
import com.example.semm.service.impl.PatentServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    UserServiceImp userServiceImp;

    @Autowired
    CurrentAccountService ccServiceImp;

    @Autowired
    PatentServiceImpl patentServiceImp;

    @Autowired
    ParkingServiceImp parkingService;

    @Autowired
    CityService cityService;
    
    @Autowired
    MessageSource msg;

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @GetMapping
    public ArrayList<User> getAllUsers() {
        // get list of all users
        return userServiceImp.getAll();
    }

    @GetMapping(path = "/getUser/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        // get list of all users
    	this.logger.debug("Running getUserById()");
    	try {
    		Optional<User> user = userServiceImp.getById(id);
            if (user.isEmpty()) {
                return new ResponseEntity<Message>(new Message(msg.getMessage("user.notFound",null,LocaleContextHolder.getLocale())), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<User>(user.get(), HttpStatus.OK);
		} catch (Exception e) {
			this.logger.error("Error found{}", e);
			return new ResponseEntity<Message>(new Message("Error found:" + e),HttpStatus.NOT_FOUND);
		} 
    }

    @GetMapping(path = "/getData/{id}")
    public ResponseEntity<?> getDataById(@PathVariable("id") Long id) {
        // get the data of a user by id
    	this.logger.debug("Running getDataById()");
    	try {
    		Optional<User> user = this.userServiceImp.getById(id);
            if (user.isEmpty()) {
            	logger.info("Return not found");
                return new ResponseEntity<Message>(new Message(msg.getMessage("user.notFound", null, LocaleContextHolder.getLocale())),HttpStatus.NOT_FOUND);
            } else {
                DataAccountUserDTO accountData = new DataAccountUserDTO(user.get().getName(), user.get().getUsername(),
                        user.get().getEmail(), user.get().getCurrentAccount());
                logger.info("Correct execution");
                return new ResponseEntity<DataAccountUserDTO>(accountData, HttpStatus.OK);
            }
		} catch (Exception e) {
			this.logger.error("Error found{}", e);
			return new ResponseEntity<Message>(new Message("Error found:" + e),HttpStatus.NOT_FOUND);
		}
        

    }

    @GetMapping(path = "/patents/{id}")
    public ResponseEntity<?> getPatentsById(@PathVariable("id") Long id) {
        // get all the patents of a user by id
    	this.logger.debug("Running getPatentsById()");
        try {
        	Optional<User> user = this.userServiceImp.getById(id);
            Set<Patent> listPatents = this.patentServiceImp.getByIdUser(user.get().getId());
            logger.info("Correct execution");
            return new ResponseEntity<Set<Patent>>(listPatents, HttpStatus.OK);
		} catch (Exception e) {
			this.logger.error("Error found{}", e);
			return new ResponseEntity<Message>(new Message("Error found:" + e),HttpStatus.NOT_FOUND);
		}
    }

    @GetMapping(path = "/getCurrentAccount/{id}")
    public ResponseEntity<?> getCurrentAccount(@PathVariable("id") Long id) {
        // get the current account of a user by username(phone)
    	this.logger.debug("Running getCurrentAccount()");
        try {
        	Optional<User> user = this.userServiceImp.getById(id);
            if (user.isEmpty()) {
            	logger.info("Return not found");
                return new ResponseEntity<Message>(new Message(msg.getMessage("user.notFound", null,LocaleContextHolder.getLocale())),HttpStatus.NOT_FOUND);
            }
            logger.info("Correct execution");
            return new ResponseEntity<CurrentAccount>(user.get().getCurrentAccount(), HttpStatus.OK);
        
		} catch (Exception e) {
			this.logger.error("Error found{}", e);
			return new ResponseEntity<Message>(new Message("Error found:" + e),HttpStatus.NOT_FOUND);
		}}

    @PostMapping("/chargeBalance")
    public ResponseEntity<?> chargeBalance(@Valid @RequestBody CurrentAccountDTO ca, BindingResult result) {
        // add balance to the user's account in "ca.username"
    	this.logger.debug("Running chargeBalance()");
    	try {
    		Map<String, Object> response = new HashMap<>();
            if (result.hasErrors()) {
                List<String> errors = result.getFieldErrors().stream().map(e -> e.getDefaultMessage())
                        .collect(Collectors.toList());
                response.put("errors", errors);
                logger.info("Return BAD_REQUEST");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
            }
            this.userServiceImp.chargeBalance(ca);
            logger.info("Correct execution");
            return new ResponseEntity<Message>(new Message(msg.getMessage("currentAccount.balance.update", null, LocaleContextHolder.getLocale())), HttpStatus.OK);
        } catch (Exception e) {
			this.logger.error("Error found{}", e);
			return new ResponseEntity<Message>(new Message("Error found:" + e),HttpStatus.NOT_FOUND);
		}}
		
        

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        // delete a user by id
    	this.logger.debug("Running deleteById()");
        try {
        	boolean ok = this.userServiceImp.delete(id);
            if (!ok) {
            	logger.info("Return NOT_FOUND");
                return new ResponseEntity<Message>(new Message(msg.getMessage("user.notFound", null, LocaleContextHolder.getLocale())),HttpStatus.NOT_FOUND);
            } else {
            	logger.info("Correct execution");
                return new ResponseEntity<Message>(new Message(msg.getMessage("user.deleted", null, LocaleContextHolder.getLocale())),HttpStatus.NO_CONTENT);
            }
		} catch (Exception e) {
			this.logger.error("Error found{}", e);
			return new ResponseEntity<Message>(new Message("Error found:" + e),HttpStatus.NOT_FOUND);
		}
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        this.logger.debug("Running updateUser()");
       try {
    	   Optional<User> currentUser = userServiceImp.getById(user.getId());
           if (currentUser.isEmpty()) {
        	   logger.info("Return NOT_FOUND");
               return new ResponseEntity<Message>(new Message(msg.getMessage("user.notFound", null, LocaleContextHolder.getLocale())),HttpStatus.NOT_FOUND);
           } else {
               this.userServiceImp.update(user);
               logger.info("Correct execution");
               return new ResponseEntity<User>(user, HttpStatus.OK);
           }
	} catch (Exception e) {
		this.logger.error("Error found{}", e);
		return new ResponseEntity<Message>(new Message("Error found:" + e),HttpStatus.NOT_FOUND);
	}

    }
}
