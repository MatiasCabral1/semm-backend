package com.example.semm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class History {
	@Id
	@Column(unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String dateTransaction;

	@Column
	private String typeTransaction;

	@Column
	private double balance;

	@Column
	private double amount;

	@OneToOne(fetch = FetchType.EAGER)
	private CurrentAccount currentAccount;

	public History(String dateTransaction, String typeTransaction, double balance, double amount,
			CurrentAccount currentAccount) {
		this.dateTransaction = dateTransaction;
		this.typeTransaction = typeTransaction;
		this.balance = balance;
		this.amount = amount;
		this.currentAccount = currentAccount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public History(String dateTransaction, String typeTransaction, double balance) {
		this.dateTransaction = dateTransaction;
		this.typeTransaction = typeTransaction;
		this.balance = balance;
	}

	public double balance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public CurrentAccount getCurrentAccount() {
		return currentAccount;
	}

	public void setCurrentAccount(CurrentAccount currentAccount) {
		this.currentAccount = currentAccount;
	}

	public History(Long id, String dateTransaction, String typeTransaction) {
		this.id = id;
		this.dateTransaction = dateTransaction;
		this.typeTransaction = typeTransaction;
	}

	public History() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDateTransaction() {
		return dateTransaction;
	}

	public void setDateTransaction(String dateTransaction) {
		this.dateTransaction = dateTransaction;
	}

	public String getTypeTransaction() {
		return typeTransaction;
	}

	public void setTypeTransaction(String typeTransaction) {
		this.typeTransaction = typeTransaction;
	}

}
