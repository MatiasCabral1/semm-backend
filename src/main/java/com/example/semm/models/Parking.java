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

import com.example.semm.security.dto.Message;
import com.example.semm.security.dto.TimePriceDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "parking")
public class Parking {
	@Id
	@Column(unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String date;

	@Column
	private String patent;

	@Column
	private Boolean started;

	@JsonIgnore
	@OneToOne
	private User user;

	public Parking(String date, String patent, Boolean started) {
		this.date = date;
		this.patent = patent;
		this.started = started;
	}

	public Parking(Long id, String date, String patent, Boolean started,
			City ciudad, User user) {
		this.id = id;
		this.date = date;
		this.patent = patent;
		this.started = started;
		this.user = user;
	}

	public Boolean getStarted() {
		return started;
	}

	public void setStarted(Boolean started) {
		this.started = started;
	}

	@Override
	public String toString() {
		return "Parking [id=" + id + ", Date=" + date
				+ ", Patent=" + patent + ", Started=" + started + ", User=" + user
				+ "]";
	}

	public Parking(Long id, String date, String patent,
			User user) {
		this.id = id;
		this.date = date;
		this.patent = patent;
		this.user = user;
	}

	public Parking() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPatent() {
		return patent;
	}

	public void setPatent(String patent) {
		this.patent = patent;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TimePriceDTO getCurrentPaymentDetails(City city) {
		@SuppressWarnings("deprecation")
		Date start = new Date(this.getDate());
		Date current = new Date();
		Long timeElapsed = current.getTime() - start.getTime();
		double seconds = timeElapsed / 1000;
		double hour = Math.floor(seconds / 3600);
		double minutes = Math.floor((seconds % 3600) / 60);
		double rest = Math.floor(((seconds % 3600) / 60) % 15);
		double fractionOfHour = Math.floor(((seconds % 3600) / 60) / 15);

		double finalPrice = city.getValueHours() / 4;
		System.out.println("minutes: " + minutes);
		System.out.println("hours: " + hour);
		if ((rest == 0) && (minutes != 0)) {
			// 15 minutos exactos
			return new TimePriceDTO(this.getPatent(), hour, minutes,
					(fractionOfHour * finalPrice) + (hour * city.getValueHours()));
		} else {
			// si pasaron unos minutos entonces se cobra los 15 completos.
			return new TimePriceDTO(this.getPatent(), hour, minutes,
					((fractionOfHour * finalPrice) + (hour * city.getValueHours()) + finalPrice));
		}
	}

	public static Message validations(City city, Iterable<Holiday> holidays) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
		String dateFormatted = sdf.format(new Date());
		Date fullDate = new Date();
		@SuppressWarnings("deprecation")
		int hourStart = fullDate.getHours();
		String hourStartCity = city.getParkingHours().split("-")[0];
		String hourEndCity = city.getParkingHours().split("-")[1];

		if ((hourStart >= Integer.valueOf(hourStartCity)) && (hourStart < Integer.valueOf(hourEndCity))) {
			if (!isNonWorkingDate(dateFormatted, holidays)) {
				if (!isWeekend(fullDate.toString())) {
					return null;
				} else
					return (new Message("No puede operar los fines de semana"));
			} else {
				return (new Message("No se puede operar los dias feriados"));
			}
		} else {
			return (new Message("El horario operable es de: " + hourStartCity + "a" + hourEndCity + "hs "));
		}

	}

	private static boolean isWeekend(String fecha) {
		String dia = fecha.split(" ")[0];
		return (dia.equals("Sun") || (dia.equals("Sat")));

	}

	private static boolean isNonWorkingDate(String fecha, Iterable<Holiday> feriados) {
		for (Holiday f : feriados) {
			if (f.getDate().equals(fecha)) {
				return true;
			}
		}
		return false;

	}

}
