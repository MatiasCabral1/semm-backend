package com.example.semm.security.controller;

import java.util.HashSet;
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

import com.example.semm.models.Patente;
import com.example.semm.security.dto.JwtDto;
import com.example.semm.security.dto.LoginUsuario;
import com.example.semm.security.dto.Mensaje;
import com.example.semm.security.dto.NuevoUsuario;
import com.example.semm.security.enums.RolNombre;
import com.example.semm.security.jwt.JwtEntryPoint;
import com.example.semm.security.jwt.JwtProvider;
import com.example.semm.security.model.Rol;
import com.example.semm.security.model.Usuario;
import com.example.semm.security.service.RolService;
import com.example.semm.security.service.UsuarioService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	 @Autowired
	    PasswordEncoder passwordEncoder;

	    @Autowired
	    AuthenticationManager authenticationManager;

	    @Autowired
	    UsuarioService usuarioService;

	    @Autowired
	    RolService rolService;

	    @Autowired
	    JwtProvider jwtProvider;
	    
	    private final static Logger logger = LoggerFactory.getLogger(AuthController.class);

	    @PostMapping("/nuevo")
	    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
	    	
	    	// validaciones:
			
			if (bindingResult.hasErrors()) {
				if((nuevoUsuario.getEmail()==null)||(nuevoUsuario.getNombreUsuario()==null)||(nuevoUsuario.getPassword()==null)) {
					 return new ResponseEntity(new Mensaje("Los campos no pueden estar vacios"), HttpStatus.BAD_REQUEST);
				 }
				if((nuevoUsuario.getEmail().isBlank())||(nuevoUsuario.getNombreUsuario().isBlank())) {
					 return new ResponseEntity(new Mensaje("Los campos no pueden tener espacios en blanco"), HttpStatus.BAD_REQUEST);
				 }
				 if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario())) {
			            return new ResponseEntity(new Mensaje("El usuario con ese telefono ya existe"), HttpStatus.BAD_REQUEST);
				 }
				 if((nuevoUsuario.getNombreUsuario().length()<10)|| (nuevoUsuario.getNombreUsuario().length()>10 )){
					 return new ResponseEntity(new Mensaje("El telefono debe tener 10 digitos"), HttpStatus.BAD_REQUEST);
				 }
				
			     return new ResponseEntity(new Mensaje("email invalido"), HttpStatus.BAD_REQUEST);  
		     }
			
	        Usuario usuario =
	                new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(),
	                        passwordEncoder.encode(nuevoUsuario.getPassword()));
	        Set<Rol> roles = new HashSet<>();
	        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
	        if(nuevoUsuario.getRoles().contains("admin"))
	            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
	        usuario.setRoles(roles);
	        usuarioService.save(usuario);
	        return new ResponseEntity(new Mensaje("usuario guardado"), HttpStatus.CREATED);
	        }
	    

	    @PostMapping("/login")
	    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
	        if(bindingResult.hasErrors())
	            return new ResponseEntity(new Mensaje("Los datos ingresados son incorrectos"), HttpStatus.BAD_REQUEST);
	        Authentication authentication =
	                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        String jwt = jwtProvider.generateToken(authentication);
	        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
	        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
	        return new ResponseEntity(jwtDto, HttpStatus.OK);
	    }
}
