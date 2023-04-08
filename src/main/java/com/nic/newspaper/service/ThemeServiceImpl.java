package com.nic.newspaper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nic.newspaper.dao.ArticleDao;
import com.nic.newspaper.dao.ThemeDao;
import com.nic.newspaper.entity.Theme;

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
	public Theme save(Theme theTheme) {
		return themeDao.save(theTheme);
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
