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
