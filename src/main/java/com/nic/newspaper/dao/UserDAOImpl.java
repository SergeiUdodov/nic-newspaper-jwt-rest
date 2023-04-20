package com.nic.newspaper.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nic.newspaper.entity.Role;
import com.nic.newspaper.entity.User;

import jakarta.persistence.EntityManager;

@Repository
public class UserDAOImpl implements UserDAO {

	private EntityManager entityManager;

	@Autowired
	public UserDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public User findByUserEmail(String userEmail) {
		
		Session currentSession = entityManager.unwrap(Session.class);

		Query<User> theQuery = currentSession.createQuery("from User where email=:uEmail", User.class);
		theQuery.setParameter("uEmail", userEmail);
		User theUser = null;
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}

		return theUser;
	}

	@Override
	public void save(User theUser) {

		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.persist(theUser);

	}

	@Override
	public void updateUser(User theUser) {

		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.saveOrUpdate(theUser);

	}

	@Override
	public Role findRoleByName(String theRoleName) {

		Session currentSession = entityManager.unwrap(Session.class);

		Query<Role> theQuery = currentSession.createQuery("from Role where name=:roleName", Role.class);
		theQuery.setParameter("roleName", theRoleName);

		Role theRole = null;

		try {
			theRole = theQuery.getSingleResult();
		} catch (Exception e) {
			theRole = null;
		}

		return theRole;
	}

}
