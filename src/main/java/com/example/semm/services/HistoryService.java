package com.example.semm.services;

import java.util.ArrayList;
import java.util.List;

import com.example.semm.models.History;

public interface HistoryService {
	ArrayList<History> getAll();

	History saveHistory(History historial);

	List<History> getByCurrentAccountId(Long id);
}
