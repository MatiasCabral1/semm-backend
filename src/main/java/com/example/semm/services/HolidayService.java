package com.example.semm.services;

import java.util.ArrayList;
import java.util.List;

import com.example.semm.models.Holiday;

public interface HolidayService {
	ArrayList<Holiday> getAll();
	Holiday save(Holiday feriado);
}
