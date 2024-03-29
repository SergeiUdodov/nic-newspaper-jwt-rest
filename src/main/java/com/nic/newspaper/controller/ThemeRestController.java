package com.nic.newspaper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nic.newspaper.entity.Theme;
import com.nic.newspaper.service.ThemeService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ThemeRestController {

	@Autowired
	private ThemeService themeService;

	@GetMapping("/themes")
	public List<Theme> findAllThemes() {

		return themeService.findAllThemes();
	}

	@DeleteMapping("/deleteTheme/{themeId}")
	public String deleteThemeById(@PathVariable long themeId) {

		themeService.deleteThemeById(themeId);

		return "Deleted Theme id - " + themeId;
	}

}
