package com.example.semm.services;

import java.util.ArrayList;
import java.util.Optional;

import com.example.semm.security.model.User;

public interface UserService {
	ArrayList<User> getAll();
	User saveUser(User user);
	User  update(User user);
	boolean delete(Long id);
	Optional<User> getById(Long id);

}
