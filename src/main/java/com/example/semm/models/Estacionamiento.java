  package com.example.semm.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.semm.security.dto.Mensaje;
import com.example.semm.security.dto.TiempoPrecioDTO;
import com.example.semm.security.model.Usuario;

@Entity
@Table(name = "estacionamiento")
public class Estacionamiento {
	@Id
	@Column(unique = true,nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String horaInicio;
	
	@Column
	private String patente;
	
	@Column
	private Boolean iniciado;
	
	private String username;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Estacionamiento(Long id, String horaInicio, String patente, Boolean iniciado,
			Ciudad ciudad, Usuario usuario) {
		this.id = id;
		this.horaInicio = horaInicio;
		this.patente = patente;
		this.iniciado = iniciado;
		this.usuario = usuario;
	}

	public Boolean getIniciado() {
		return iniciado;
	}

	public void setIniciado(Boolean iniciado) {
		this.iniciado = iniciado;
	}

	@Override
	public String toString() {
		return "Estacionamiento [id=" + id + ", horaInicio=" + horaInicio
				+ ", patente=" + patente + ", iniciado=" + iniciado + ", usuario=" + usuario
				+ "]";
	}

	
	public Estacionamiento(Long id, String horaInicio, String patente,
			Usuario usuario) {
		this.id = id;
		this.horaInicio = horaInicio;
		this.patente = patente;
		this.usuario = usuario;
	}



	@OneToOne(optional = true, mappedBy="estacionamiento")
	private Usuario usuario;
		

	public Estacionamiento() {
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}


	public String getPatente() {
		return patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public TiempoPrecioDTO getDatosCobroActual(Ciudad ciudad) {
		Date inicio = new Date(this.getHoraInicio());
		Date actual = new Date();
		Long tiempoTranscurrido= actual.getTime() -inicio.getTime();
		double segundos = tiempoTranscurrido / 1000; 
		double hora = Math.floor(segundos / 3600);
		 double minutos = Math.floor((segundos % 3600)/60);
        double resto=Math.floor(((segundos % 3600)/60)%15);
		double FraccionesDeHora=Math.floor(((segundos % 3600)/60)/15);
		
		double precioFraccion=ciudad.getValorHora()/4;
		System.out.println("minutos: "+ minutos);
		System.out.println("horas: "+ hora);
		if((resto==0)&&(minutos!=0)) {
			//15 minutos exactos
			return new TiempoPrecioDTO(hora,minutos,(FraccionesDeHora*precioFraccion)+(hora*ciudad.getValorHora()));
			
		}
		else {
			//si pasaron unos minutos entonces se cobra los 15 completos.
			return new TiempoPrecioDTO(hora,minutos,((FraccionesDeHora*precioFraccion)+(hora*ciudad.getValorHora())+precioFraccion));
		}		
	}
	
	public static Mensaje validaciones(Ciudad ciudad, Iterable<Feriado> feriados){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
		String fecha = sdf.format(new Date()); 
		Date fechaCompleta= new Date();
		@SuppressWarnings("deprecation")
		int horaInicio=fechaCompleta.getHours();
		String horaInicioCiudad=ciudad.getHorariosEstacionamiento().split("-")[0];
		String horaFinCiudad=ciudad.getHorariosEstacionamiento().split("-")[1];

		if((horaInicio>=Integer.valueOf (horaInicioCiudad))&&(horaInicio<Integer.valueOf (horaFinCiudad))){
		     if(!esFechaNoLaborable(fecha,feriados)) {
		    	 if(!esFinDeSemana(fechaCompleta.toString())) {
		    	 return null;
		    	 }
		    	 else return (new Mensaje("No puede operar los fines de semana"));
		     }
		     else {
		    	 return (new Mensaje("No se puede operar los dias feriados"));
		     }
		}
		else {
			return (new Mensaje("El horario operable es de: "+horaInicioCiudad +"a"+horaFinCiudad+"hs "));
		}

	}

	public static boolean esFinDeSemana(String fecha) {
		String dia=fecha.split(" ")[0];
		return (dia.equals("Sun") || (dia.equals("Sat")));

	}
	public static boolean esFechaNoLaborable(String fecha,Iterable<Feriado> feriados){
	    for( Feriado f: feriados) {
	    	if(f.getFecha().equals(fecha)) {
	    		return true;
	    	}
	    }
		return false;

	}
	
}
