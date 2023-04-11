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

	// define field for entitymanager
	private EntityManager entityManager;

	// set up constructor injection
	@Autowired
	public UserDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public User findByUserEmail(String userEmail) {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		// now retrieve/read from database using username
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
	public User save(User theUser) {

		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		// save User
		currentSession.persist(theUser);

		return currentSession.get(User.class, theUser.getId());
	}

	@Override
	public User updateUser(User theUser) {

		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.saveOrUpdate(theUser);

		return currentSession.get(User.class, theUser.getId());
	}

	@Override
	public Role findRoleByName(String theRoleName) {

		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		// now retrieve/read from database using name
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
