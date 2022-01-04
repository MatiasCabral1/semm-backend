package com.example.semm.controllers;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import com.example.semm.models.Ciudad;
import com.example.semm.models.CuentaCorriente;
import com.example.semm.models.Estacionamiento;
import com.example.semm.models.Patente;
import com.example.semm.security.controller.AuthController;
import com.example.semm.security.dto.LoginUsuario;
import com.example.semm.security.dto.Mensaje;
import com.example.semm.security.dto.NuevoUsuario;
import com.example.semm.security.enums.RolNombre;
import com.example.semm.security.model.Rol;
import com.example.semm.security.model.Usuario;
import com.example.semm.service.impl.EstacionamientoServiceImp;
import com.example.semm.service.impl.personaServiceImp;
import com.example.semm.services.CiudadService;
import com.example.semm.services.CuentaCorrienteService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persona")
@CrossOrigin(origins="http://localhost:4200")
public class personaController {
    @Autowired
    personaServiceImp personaServiceImp;
    
    @Autowired
    CuentaCorrienteService ccServiceImp;
    
    @Autowired
    EstacionamientoServiceImp estService;
    
    @Autowired
    CiudadService ciudadService;
    
    private final static Logger logger = LoggerFactory.getLogger(personaController.class);
    
    @GetMapping
    public ArrayList<Usuario> obtenerPersona(){
    	//listado de usuarios
        return personaServiceImp.listar();
    }

    
    //@PostMapping // este metodo no se utiliza. Se guarda guarda un usuario con la ruta -> 8080/auth/nuevo -> es un metodo del controller de usuario en security.
    //public ResponseEntity<Persona> guardaPersona(@RequestBody Persona p){
    	//creo un usuario
    	//Persona per= this.personaServiceImp.guardarPersona(p);
    	//return new ResponseEntity<Persona>(per, HttpStatus.CREATED);
    //    return null;
    //}

    @GetMapping( path = "/{id}")
    public ResponseEntity<Optional<Usuario>> obtenerUsuarioPorId(@PathVariable("id") Long id) {
    	//listo un usuario por id
        Optional<Usuario> per = this.personaServiceImp.listaPorId(id);
        return new ResponseEntity<Optional<Usuario>>(per, HttpStatus.OK);
    }
    
    @GetMapping( path = "/getDatos/{username}")
    public ResponseEntity<Optional<Usuario>> getPorUsername(@PathVariable("username") String username) {
    	//listo un usuario por id
        Optional<Usuario> per = this.personaServiceImp.listaPorUsername(username);
        return new ResponseEntity<Optional<Usuario>>(per, HttpStatus.OK);
    }
    
    @PostMapping("/patentes")
    public ResponseEntity<Set<Patente>> obtenerPatentesPorId(@RequestBody String username) {
    	//listo las patentes de un usuario por el user_id
    	logger.error("error");
        Optional<Usuario> per = this.personaServiceImp.listaPorUsername(username);
        Set<Patente> listaPatentes= per.get().getPatenteList();
        return new ResponseEntity<Set<Patente>>(listaPatentes, HttpStatus.OK);
    }
    
    @PostMapping("/getSaldo")
    public ResponseEntity<CuentaCorriente> getSaldo(@RequestBody String username) {
        Optional<Usuario> per = this.personaServiceImp.listaPorUsername(username);
        return new ResponseEntity<CuentaCorriente>(per.get().getCuentaCorriente(), HttpStatus.OK);
    }
    
    @PostMapping("/debitar")
    public Boolean guardaPatente(@RequestBody String username){
    	 System.out.println("EJECUTANDO DEBITO");
        Optional<Usuario> per = this.personaServiceImp.listaPorUsername(username);
        Optional<Estacionamiento> est = this.estService.listaPorId(per.get().getEstacionamiento().getId());
        ArrayList <Ciudad> ciudad = this.ciudadService.listar();
        System.out.println("nuevo saldo: "+ per.get().getCuentaCorriente().consumo(per,est,ciudad.get(0)));
        this.personaServiceImp.actualizar(per.get());
        this.ccServiceImp.actualizar(per.get().getCuentaCorriente());
        return true;
    }
    
    @DeleteMapping (path = "/{id}")
    public ResponseEntity<Usuario> eliminarPorId(@PathVariable("id") Long id){
    	//eliminar por id
        boolean ok= this.personaServiceImp.eliminar(id);
        if (!ok) {
        	System.out.println("No es posible eliminar, no se encuentra el usuario con id " + id);
            return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
        } else {
        	System.out.println("Se elimino el usuario con id " + id);
        	return new ResponseEntity<Usuario>(HttpStatus.NO_CONTENT);
        }
    }
    
    @PutMapping
    public ResponseEntity<Usuario> updateUser(@RequestBody Usuario user) {
    System.out.println("Actualizando el usuario " + user.getId());
    Optional<Usuario> currentUser = personaServiceImp.listaPorId(user.getId());
    
    if (currentUser.isEmpty()) {
	    System.out.println("User with id " + user.getId() + " not found");	    
	    return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
    }else {
    	this.personaServiceImp.actualizar(user);
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }
    	
    }
}
