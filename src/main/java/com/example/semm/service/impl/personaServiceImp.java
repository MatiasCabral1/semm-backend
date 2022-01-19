package com.example.semm.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.semm.models.Ciudad;
import com.example.semm.models.CuentaCorriente;
import com.example.semm.models.Estacionamiento;
import com.example.semm.models.Historial;
import com.example.semm.repositories.personaRepository;
import com.example.semm.security.dto.CuentaCorrienteDTO;
import com.example.semm.security.dto.LoginUsuario;
import com.example.semm.security.dto.TiempoPrecioDTO;
import com.example.semm.security.model.Usuario;
import com.example.semm.services.CiudadService;
import com.example.semm.services.CuentaCorrienteService;
import com.example.semm.services.PersonaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class personaServiceImp implements PersonaService{
    @Autowired
    personaRepository personaRepository;
    
    @Autowired
    CuentaCorrienteService ccServiceImp;
    
    @Autowired
    EstacionamientoServiceImp estService;
    
    @Autowired
    HistorialServiceImpl historialService;
    
    @Autowired
    CiudadService ciudadService;
    
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
	
	public void debitarConsumo(String username) {
		//actualizo la cuenta del usuario, y guardo la operacion en el historial.
		System.out.println("EJECUTANDO DEBITO");
        Optional<Usuario> per = this.listaPorUsername(username);
        Optional<Estacionamiento> est = this.estService.listaPorId(per.get().getEstacionamiento().getId());
        ArrayList <Ciudad> ciudad = this.ciudadService.listar();
       TiempoPrecioDTO result=  per.get().getCuentaCorriente().consumo(per,est,ciudad.get(0));
        this.actualizar(per.get());
        this.ccServiceImp.actualizar(per.get().getCuentaCorriente());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String fecha = sdf.format(new Date()); 
        System.out.println("date para historial formateado: "+ fecha);
        historialService.guardaHistorial(new Historial(fecha,"Consumo", per.get().getCuentaCorriente().getSaldo(),result.getPrecio(),per.get().getCuentaCorriente()));
	}
	
	public void cargarSaldo(CuentaCorrienteDTO cc) {
		Optional<Usuario> per = this.listaPorUsername(cc.getTelefono());
        Optional <CuentaCorriente> cuentaCorriente = this.ccServiceImp.listaPorId(cc.getId());
        cuentaCorriente.get().cargar(cc.getSaldo());
        this.ccServiceImp.actualizar(cuentaCorriente.get());
        per.get().getCuentaCorriente().setSaldo(cuentaCorriente.get().getSaldo());
        this.actualizar(per.get());
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String fecha = sdf.format(new Date()); 
        System.out.println("date para historial formateado: "+ fecha);
        historialService.guardaHistorial(new Historial(fecha,"Carga", per.get().getCuentaCorriente().getSaldo(),cc.getSaldo(),per.get().getCuentaCorriente()));
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
