package com.example.semm.models;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import javax.persistence.*;

import com.example.semm.security.dto.TimePriceDTO;
import com.example.semm.security.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table (name = "currentAccount")
public class CurrentAccount {

	@Id
	@Column(unique = true,nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column 
	private double balance;
	
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column 
	private String phone;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public TimePriceDTO debit(Optional<User> user, Optional<Parking> parking, City city) {
		TimePriceDTO result = parking.get().getCurrentPaymentDetails(city);
		this.setBalance(this.getBalance() - result.getPrice());	
        return result;
	}
	
	public void loadBalance(double amount) {
		this.setBalance(this.getBalance() + amount);
	}
	
}
