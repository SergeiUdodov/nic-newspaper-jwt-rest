package com.nic.newspaper.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nic.newspaper.dao.ArticleDao;
import com.nic.newspaper.dao.ThemeDao;
import com.nic.newspaper.entity.Theme;
import com.nic.newspaper.service.ThemeService;

import jakarta.transaction.Transactional;

@Service
public class ThemeServiceImpl implements ThemeService {

	@Autowired
	public ThemeDao themeDao;

	@Autowired
	public ArticleDao articleDao;

	@Override
	@Transactional
	public List<Theme> findAllThemes() {

		return themeDao.findAllThemes();
	}

	@Override
	@Transactional
	public void save(Theme theTheme) {
		themeDao.save(theTheme);
	}

	@Override
	@Transactional
	public Theme findThemeByName(String name) {
		return themeDao.findThemeByName(name);
	}

	@Override
	@Transactional
	public void deleteThemeById(long themeId) {
		themeDao.deleteThemeById(themeId);

	}

}
