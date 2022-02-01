package com.example.semm.controllers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.semm.models.CurrentAccount;
import com.example.semm.models.Rol;
import com.example.semm.models.User;
import com.example.semm.security.dto.JwtDTO;
import com.example.semm.security.dto.LoginUserDTO;
import com.example.semm.security.dto.Message;
import com.example.semm.security.dto.NewUserDTO;
import com.example.semm.security.enums.RolName;
import com.example.semm.security.jwt.JwtProvider;
import com.example.semm.service.impl.UserServiceImp;
import com.example.semm.services.CurrentAccountService;
import com.example.semm.services.RolService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	 @Autowired
	    PasswordEncoder passwordEncoder;

	    @Autowired
	    AuthenticationManager authenticationManager;

	    @Autowired
	    UserServiceImp userService;
	    
	    @Autowired
	    CurrentAccountService currentAccountService;


	    @Autowired
	    RolService rolService;

	    @Autowired
	    JwtProvider jwtProvider;
	    
	    private final static Logger logger = LoggerFactory.getLogger(AuthController.class);

	    @PostMapping("/nuevo")
	    public ResponseEntity<?> nuevo(@Valid @RequestBody NewUserDTO newUser, BindingResult bindingResult){
	    	
	    	// validaciones:
			
			if (bindingResult.hasErrors()) {
			     return new ResponseEntity<Message>(new Message(bindingResult.getFieldError().getDefaultMessage()), HttpStatus.BAD_REQUEST);  
		     }
			if(userService.existsByUsername(newUser.getUsername())) {
	            return new ResponseEntity<Message>(new Message("El usuario con ese telefono ya existe"), HttpStatus.BAD_REQUEST);
		 }
			
	        User user =
	                new User(newUser.getName(), newUser.getUsername(), newUser.getEmail(),
	                        passwordEncoder.encode(newUser.getPassword()));
	        Set<Rol> roles = new HashSet<>();
	        roles.add(rolService.getByRolName(RolName.ROLE_USER).get());
	        if(newUser.getRoles().contains("admin"))
	            roles.add(rolService.getByRolName(RolName.ROLE_ADMIN).get());
	        user.setRoles(roles);
	        userService.save(user);

	     // obtengo el id de user
			Optional<User> userQuery = userService.findById(user.getId());
	        CurrentAccount ca = new CurrentAccount();
	        ca.setBalance(0);
	        ca.setUser(user);
	        ca.setPhone(user.getUsername());
	        user.setCurrentAccount(ca);
	        currentAccountService.save(ca);
	        user.setCurrentAccount(ca);
	        userService.save(user);
	        return new ResponseEntity<User>(user, HttpStatus.CREATED);
	        }
	    

	    @PostMapping("/login")
	    public ResponseEntity<JwtDTO> login(@Valid @RequestBody LoginUserDTO loginUser, BindingResult bindingResult){
	        if(bindingResult.hasErrors())
	            return new ResponseEntity(new Message("Los datos ingresados son incorrectos"), HttpStatus.BAD_REQUEST);
	        Authentication authentication =
	                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        String jwt = jwtProvider.generateToken(authentication);
	        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
	        JwtDTO jwtDto = new JwtDTO(jwt, userDetails.getUsername(), userDetails.getAuthorities());
	        return new ResponseEntity<JwtDTO>(jwtDto, HttpStatus.OK);
	    }
}
