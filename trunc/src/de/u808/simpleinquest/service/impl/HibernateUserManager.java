package de.u808.simpleinquest.service.impl;

import java.util.List;

import de.u808.simpleinquest.domain.User;
import de.u808.simpleinquest.repository.UserDAO;
import de.u808.simpleinquest.service.UserManager;

public class HibernateUserManager implements UserManager {
	
	private UserDAO userDAO;

	public User fetchUser(long id) {
		return userDAO.findById(id);
	}

	public User fetchUser(String userName) {		
		return userDAO.findByUsername(userName);
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public List<User> fetchAllUser() {
		return userDAO.findAll();
	}

	public User makePersistent(User user) {
		return userDAO.makePersistent(user);
	}

	public void makeTransient(User user) {
		userDAO.makeTransient(user);
	}

}
