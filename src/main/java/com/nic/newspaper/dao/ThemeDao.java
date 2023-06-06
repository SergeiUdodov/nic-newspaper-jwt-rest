package com.nic.newspaper.dao;

import java.util.List;

import com.nic.newspaper.entity.Theme;

public interface ThemeDao {

	public List<Theme> findAllThemes();

	public void save(Theme theTheme);

	public Theme findThemeByName(String name);

	public void deleteThemeById(long themeId);

}
