package com.example.semm.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import com.example.semm.models.Ciudad;
import com.example.semm.repositories.CiudadRepository;
import com.example.semm.services.CiudadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CiudadServiceImp implements CiudadService{
    @Autowired
    CiudadRepository ciudadRepository;

    public ArrayList<Ciudad> listar(){
        return (ArrayList<Ciudad>)ciudadRepository.findAll();
    }

    public Ciudad guardarCiudad(Ciudad c){
        return ciudadRepository.save(c);
    }


    public Optional<Ciudad> listaPorId(Long id){
        return ciudadRepository.findById(id);
    }

    public boolean eliminar(Long id){
        try {
            ciudadRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

	@Override
	public Ciudad actualizar(Ciudad ciudad) {
		return ciudadRepository.save(ciudad);
	}

}
