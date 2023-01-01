package com.nic.newspaper.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
	public List<User> findAll() {

		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// create a query
		Query<User> theQuery =
				currentSession.createQuery("from User", User.class);
		
		// execute query and get result list
		List<User> Users = theQuery.getResultList();
		
		// return the results		
		return Users;
	}


	@Override
	public User findById(int theId) {

		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// get the User
		User theUser =
				currentSession.get(User.class, theId);
		
		// return the User
		return theUser;
	}


	@Override
	public void save(User theUser) {

		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// save User
		currentSession.saveOrUpdate(theUser);
	}


	@Override
	public void deleteById(int theId) {
		
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
				
		// delete object with primary key
		Query theQuery = 
				currentSession.createQuery(
						"delete from User where id=:UserId");
		theQuery.setParameter("UserId", theId);
		
		theQuery.executeUpdate();
	}

}







