package com.example.semm.controllers;
import java.util.ArrayList;
import java.util.Optional;

import com.example.semm.models.Ciudad;
import com.example.semm.models.CuentaCorriente;
import com.example.semm.service.impl.CuentaCorrienteServiceImp;

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
@RequestMapping("/cuentaCorriente")
public class CuentaCorrienteController {
    @Autowired
    CuentaCorrienteServiceImp cuentaCorrienteService;
    
    @GetMapping
    public ArrayList<CuentaCorriente> obtenerCuentas(){
        return cuentaCorrienteService.listar();
    }

    @PostMapping
    public CuentaCorriente guardaUnaCuenta(@RequestBody CuentaCorriente cc){
        return this.cuentaCorrienteService.registrar(cc);
    }

    @GetMapping( path = "/{id}")
    public Optional <CuentaCorriente> obtenerCuentaPorId(@PathVariable("id") Long id) {
        return this.cuentaCorrienteService.listaPorId(id);
    }

    


    @DeleteMapping (path = "/{id}")
    public ResponseEntity<CuentaCorriente> eliminarPorId(@PathVariable("id") Long id){
        boolean ok= this.cuentaCorrienteService.eliminar(id);
        if (ok) {
        	System.out.println("No es posible eliminar, no se encuentra la cuenta con id " + id);
            return new ResponseEntity<CuentaCorriente>(HttpStatus.NOT_FOUND);
        } else {
        	System.out.println("Se elimino el usuario con id " + id);
        	return new ResponseEntity<CuentaCorriente>(HttpStatus.NO_CONTENT);
        }
    }
    
    
    @PutMapping
    public ResponseEntity<CuentaCorriente> updateUser(@RequestBody CuentaCorriente cc) {
    System.out.println("Actualizando la ciudad " + cc.getId());
    Optional<CuentaCorriente> currentUser = cuentaCorrienteService.listaPorId(cc.getId());
    
    if (currentUser.isEmpty()) {
	    System.out.println("Ciudad with id " + cc.getId() + " not found");	    
	    return new ResponseEntity<CuentaCorriente>(HttpStatus.NOT_FOUND);
    }else {
    	this.cuentaCorrienteService.actualizar(cc);
    	return new ResponseEntity<CuentaCorriente>(cc, HttpStatus.OK);
    }
    	
    }
}
