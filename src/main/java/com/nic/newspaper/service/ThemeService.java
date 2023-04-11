package com.nic.newspaper.service;

import java.util.List;

import com.nic.newspaper.entity.Theme;

public interface ThemeService {

	public List<Theme> findAllThemes();

	public Theme save(Theme theTheme);

	public Theme findThemeByName(String name);

	public void deleteThemeById(long themeId);

}
