package com.nic.newspaper.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nic.newspaper.entity.Theme;

import jakarta.persistence.EntityManager;

@Repository
public class ThemeDaoImpl implements ThemeDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Theme> findAllThemes() {

		Session currentSession = entityManager.unwrap(Session.class);

		Query<Theme> theQuery = currentSession.createQuery("from Theme", Theme.class);

		List<Theme> themes = theQuery.getResultList();

		return themes;
	}

//	@Override
//	public Theme findThemeById(long themeId) {
//
//		Session currentSession = entityManager.unwrap(Session.class);
//
//		Theme theTheme = currentSession.get(Theme.class, themeId);
//
//		return theTheme;
//	}

	@Override
	public Theme save(Theme theTheme) {

		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.persist(theTheme);

		return currentSession.get(Theme.class, theTheme.getId());

	}

//	@Override
//	public List<Theme> findThemesByNames(String[] names) {
//
//		List<Theme> result = new ArrayList<>();
//
//		for (String name : names) {
//			Session currentSession = entityManager.unwrap(Session.class);
//			Query<Theme> theQuery = currentSession.createQuery("from Theme where name=:tName", Theme.class);
//			theQuery.setParameter("tName", name);
//			Theme theme = null;
//			try {
//				theme = theQuery.getSingleResult();
//				result.add(theme);
//			} catch (Exception e) {
//				theme = null;
//			}
//		}
//
//		return result;
//	}

	@Override
	public Theme findThemeByName(String name) {

		Session currentSession = entityManager.unwrap(Session.class);
		Query<Theme> theQuery = currentSession.createQuery("from Theme where name=:tName", Theme.class);
		theQuery.setParameter("tName", name);
		Theme theme = null;
		try {
			theme = theQuery.getSingleResult();
		} catch (Exception e) {
			theme = null;
		}

		return theme;
	}

	@Override
	public void deleteThemeById(long themeId) {
		Session currentSession = entityManager.unwrap(Session.class);

		Query theQuery = currentSession.createQuery("delete from Theme where id=:themeId");
		theQuery.setParameter("themeId", themeId);

		theQuery.executeUpdate();

	}

}
