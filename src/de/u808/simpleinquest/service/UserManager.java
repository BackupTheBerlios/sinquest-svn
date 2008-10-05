package de.u808.simpleinquest.service;

import java.util.List;

import de.u808.simpleinquest.domain.User;

public interface UserManager {
	
	public User fetchUser(long id);
	
	public User fetchUser(String userName);
	
	public List<User> fetchAllUser();
	
	public User makePersistent(User user);
	
	public void makeTransient(User user);

}
