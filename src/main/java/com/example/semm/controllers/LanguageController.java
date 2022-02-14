package com.example.semm.controllers;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/language")
@CrossOrigin(origins = "http://localhost:4200")
public class LanguageController {
	 	@GetMapping
	    public Locale getLanguage() {
	        // get local language
	        return LocaleContextHolder.getLocale();
	    }
}
