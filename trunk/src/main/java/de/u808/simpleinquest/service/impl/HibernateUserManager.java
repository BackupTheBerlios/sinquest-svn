/*
 * Copyright 2008-2009 Andreas Friedel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.u808.simpleinquest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import de.u808.simpleinquest.domain.User;
import de.u808.simpleinquest.repository.UserDAO;
import de.u808.simpleinquest.service.UserManager;

@Service("userManager")
public class HibernateUserManager implements UserManager {
	
	@Autowired
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
