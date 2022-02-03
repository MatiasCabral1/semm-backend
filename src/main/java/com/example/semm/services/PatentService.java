package com.example.semm.services;

import java.util.ArrayList;
import java.util.Optional;

import com.example.semm.models.Patent;

public interface PatentService {
	ArrayList<Patent> getAll();

	Patent savePatent(Patent patent);

	Patent update(Patent patent);

	boolean delete(Long id);

	Optional<Patent> getById(Long id);
}
