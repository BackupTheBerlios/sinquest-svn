package de.u808.simpleinquest.repository;

import java.util.List;

import de.u808.simpleinquest.domain.User;

public interface UserDAO extends GenericDAO<User, Long> {

	public List<User> findByExample(User exampleInstance);
	public User findByUsername(String username);
}
