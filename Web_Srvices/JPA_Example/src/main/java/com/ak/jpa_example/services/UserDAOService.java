package com.ak.jpa_example.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.ak.jpa_example.entities.User;

@Repository
@Transactional   //Control transaction Boundaries.
public class UserDAOService {

	@PersistenceContext  //Causes it to get auto wired.
	private EntityManager entityManager;

	public long insert(User user) {
		//Persist on an object causes the data changes to be tracked and persisted automatically.
		entityManager.persist(user);
		return user.getId();
	}
}
