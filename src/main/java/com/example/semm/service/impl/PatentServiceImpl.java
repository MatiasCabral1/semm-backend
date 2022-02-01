package com.example.semm.service.impl;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.semm.models.Patent;
import com.example.semm.repositories.PatentRepository;
import com.example.semm.services.PatentService;
@Service
public class PatentServiceImpl implements PatentService{
	@Autowired
    PatentRepository patenteRepository;
	
	@Override
	public ArrayList<Patent> getAll() {
		return (ArrayList<Patent>)patenteRepository.findAll();
	}

	@Override
	public Patent savePatent(Patent patente) {
		return patenteRepository.save(patente);
	}

	@Override
	public Patent update(Patent patente) {
		return patenteRepository.save(patente);
	}

	@Override
	public boolean delete(Long id) {
		try {
            patenteRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
	}

	@Override
	public Optional<Patent> getById(Long id) {
		return patenteRepository.findById(id);
	}
	
	public Patent existsByPatentAndUser(String patente, Long idUser) {
		return patenteRepository.existsByPatenteAndUsuario(patente, idUser);
	}
	
	public Set<Patent> getByIdUser(Long idUser){
		return patenteRepository.getByIdUser(idUser);
	}
	
	@Transactional
	public Patent update(Patent patentRequest, Long id) {
		Optional<Patent> patent = this.patenteRepository.findById(id);
		if (patent.isPresent()) {
			patent.get().setNumber(patentRequest.getNumber());
			patent.get().setUser(patentRequest.getUser());
			return this.patenteRepository.save(patent.get());
		} else
			return null;
	}

}
