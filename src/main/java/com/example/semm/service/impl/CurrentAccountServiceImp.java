package com.example.semm.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import com.example.semm.models.CurrentAccount;
import com.example.semm.repositories.CurrentAccountRepository;
import com.example.semm.services.CurrentAccountService;
import com.example.semm.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrentAccountServiceImp implements CurrentAccountService{
    @Autowired
    CurrentAccountRepository cuentaCorrienteRepository;
    
    @Override
    public ArrayList<CurrentAccount> getAll(){
        return (ArrayList<CurrentAccount>)cuentaCorrienteRepository.findAll();
    }
    
    @Override
    public CurrentAccount save(CurrentAccount cc){
        return cuentaCorrienteRepository.save(cc);
    }

    @Override
    public Optional<CurrentAccount> getById(Long id){
        return cuentaCorrienteRepository.findById(id);
    }


	@Override
	public CurrentAccount update(CurrentAccount cc) {
		return cuentaCorrienteRepository.save(cc);
		
	}
	
	@Override
    public boolean delete(Long id){
        try {
            cuentaCorrienteRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
	

}
