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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import de.u808.simpleinquest.domain.User;
import de.u808.simpleinquest.service.UserManager;

public class HibernateDAOUserDetailsService implements UserDetailsService{
	
	//private UserDAO userDAO;
	
	private UserManager userManager;
	
	private static Log log = LogFactory.getLog(HibernateDAOUserDetailsService.class);

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		User user = userManager.fetchUser(username);
		
		if (null == user) {
			log.error("User with login: " + username + " not found in database. Authentication failed for user " + username);
			throw new UsernameNotFoundException("user not found in database");
		}
		else{
			return new org.springframework.security.userdetails.User(user.getUsername(), user.getPassword(), true, true, true, true, new GrantedAuthority[] { new GrantedAuthorityImpl("ROLE_USER") });
		}
		
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

}
