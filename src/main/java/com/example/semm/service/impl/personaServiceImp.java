package com.example.semm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.semm.repositories.personaRepository;
import com.example.semm.security.dto.LoginUsuario;
import com.example.semm.security.model.Usuario;
import com.example.semm.services.PersonaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class personaServiceImp implements PersonaService{
    @Autowired
    personaRepository personaRepository;
    
    @Override
    public ArrayList<Usuario> listar(){
        return (ArrayList<Usuario>)personaRepository.findAll();
    }
    
    
    @Override
    public Usuario guardarPersona(Usuario p){
        //return personaRepository.save(p);
    	return null;
    }

    @Override
    public Optional<Usuario> listaPorId(Long id){
        return personaRepository.findById(id);
    }
    

    @Override
    public boolean eliminar(Long id){
        try {
            personaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

	@Override
	public Usuario actualizar(Usuario persona) {
		return personaRepository.save(persona);	
	}

	public Optional <Usuario> listaPorUsername(String username){
    	//este metodo recibe telefono y passwd
    	//verifica si el usuario esta registrado.
		// este metodo es malo porque solo verifica que el nombre de usuario sea el mismo que inicio sesion.
		//por ahora es la unica solucion que se me ocurrio. La mas optima seria que se busque al usuario por id.
		//Si el userName es unico. Entonces este metodo no tendria ningun problema. verificar eso del enunciado.
    	Iterable<Usuario> listaPer= personaRepository.findAll();
    	Optional<Usuario> personaR = null;
    	for (Usuario per:listaPer) {
    		if(per.getNombreUsuario().equals(username)) {
    			personaR = Optional.ofNullable(per);
    		}
    	}
    	return personaR;
    }


	
}
