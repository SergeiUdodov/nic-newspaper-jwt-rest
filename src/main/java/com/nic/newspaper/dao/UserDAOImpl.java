package com.nic.newspaper.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nic.newspaper.entity.Role;
import com.nic.newspaper.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class UserDAOImpl implements UserDAO {

	private EntityManager entityManager;

	@Autowired
	public UserDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public User findByUserEmail(String userEmail) {
		
		TypedQuery<User> query = entityManager.createQuery("from User where email=:uEmail", User.class);
		query.setParameter("uEmail", userEmail);
		User theUser = null;
        try {
        	theUser = query.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}

		return theUser;
	}

	@Override
	public void save(User theUser) {
		
		entityManager.persist(theUser);

	}

	@Override
	public void updateUser(User theUser) {
		
		entityManager.merge(theUser);

	}

	@Override
	public Role findRoleByName(String theRoleName) {
		
		TypedQuery<Role> query = entityManager.createQuery("from Role where name=:roleName", Role.class);
		query.setParameter("roleName", theRoleName);
		Role theRole = null;
        try {
        	theRole = query.getSingleResult();
		} catch (Exception e) {
			theRole = null;
		}

		return theRole;
	}

}
