package com.example.semm.services;

import java.util.ArrayList;
import java.util.Optional;

import com.example.semm.models.CurrentAccount;


public interface CurrentAccountService {
	ArrayList<CurrentAccount> getAll();
	CurrentAccount save(CurrentAccount cc);
	CurrentAccount update(CurrentAccount cc);
	boolean delete(Long id);
	Optional<CurrentAccount> getById(Long id);
}
