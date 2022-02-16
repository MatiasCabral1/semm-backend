package com.example.semm.controllers;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/language")
@CrossOrigin(origins = "http://localhost:4200")
public class LanguageController {
	private Logger logger = LoggerFactory.getLogger(LanguageController.class);
	 	@GetMapping
	    public Locale getLanguage() {
	 		this.logger.debug("Running getLanguage()");
	 		try {
	 			return LocaleContextHolder.getLocale();
			} catch (Exception e) {
				this.logger.error("Error found{}", e);
				return null;
			}
	        
	    }
}
