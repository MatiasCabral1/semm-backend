package com.example.semm.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import com.example.semm.models.CuentaCorriente;
import com.example.semm.repositories.CuentaCorrienteRepository;
import com.example.semm.services.CuentaCorrienteService;
import com.example.semm.services.PersonaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CuentaCorrienteServiceImp implements CuentaCorrienteService{
    @Autowired
    CuentaCorrienteRepository cuentaCorrienteRepository;
    
    @Override
    public ArrayList<CuentaCorriente> listar(){
        return (ArrayList<CuentaCorriente>)cuentaCorrienteRepository.findAll();
    }
    
    @Override
    public CuentaCorriente registrar(CuentaCorriente cc){
        return cuentaCorrienteRepository.save(cc);
    }

    @Override
    public Optional<CuentaCorriente> listaPorId(Long id){
        return cuentaCorrienteRepository.findById(id);
    }


	@Override
	public CuentaCorriente actualizar(CuentaCorriente cc) {
		System.out.println("id"+cc.getId());
		System.out.println("saldo"+cc.getSaldo());
		return cuentaCorrienteRepository.save(cc);
		
	}
	
	@Override
    public boolean eliminar(Long id){
        try {
            cuentaCorrienteRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
	

}
