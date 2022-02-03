package com.example.semm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.semm.models.History;
import com.example.semm.repositories.HistoryRepository;
import com.example.semm.services.HistoryService;

@Service
public class HistoryServiceImpl implements HistoryService {
	@Autowired
	HistoryRepository historialRepository;

	@Override
	public ArrayList<History> getAll() {
		return (ArrayList<History>) historialRepository.findAll();
	}

	@Override
	public History saveHistory(History historial) {
		return historialRepository.save(historial);
	}

	@Override
	public List<History> getByCurrentAccountId(Long id) {
		return historialRepository.getByCurrentAccountId(id);
	}

}
