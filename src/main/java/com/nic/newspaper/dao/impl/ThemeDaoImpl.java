package com.nic.newspaper.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nic.newspaper.dao.ThemeDao;
import com.nic.newspaper.entity.Theme;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class ThemeDaoImpl implements ThemeDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Theme> findAllThemes() {
		
		TypedQuery<Theme> query = entityManager.createQuery("from Theme", Theme.class);
        List<Theme> themes = query.getResultList();
        return themes;
	}

	@Override
	public void save(Theme theTheme) {
		
		entityManager.persist(theTheme);

	}

	@Override
	public Theme findThemeByName(String name) {
		
		TypedQuery<Theme> query = entityManager.createQuery("from Theme where name=:tName", Theme.class);
		query.setParameter("tName", name);
        Theme theme = null;
        try {
			theme = query.getSingleResult();
		} catch (Exception e) {
			theme = null;
		}

		return theme;
	}

	@Override
	public void deleteThemeById(long themeId) {
		
		jakarta.persistence.Query theQuery = entityManager.createQuery("delete from Theme where id=:themeId");
        theQuery.setParameter("themeId", themeId);
        theQuery.executeUpdate();

	}

}
