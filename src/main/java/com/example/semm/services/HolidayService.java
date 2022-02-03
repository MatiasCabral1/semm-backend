package com.example.semm.services;

import java.util.ArrayList;

import com.example.semm.models.Holiday;

public interface HolidayService {
	ArrayList<Holiday> getAll();

	Holiday save(Holiday feriado);
}
