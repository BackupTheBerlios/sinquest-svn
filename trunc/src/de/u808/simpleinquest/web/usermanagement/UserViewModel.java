package de.u808.simpleinquest.web.usermanagement;

import java.util.List;

import de.u808.simpleinquest.domain.User;

public class UserViewModel {
	
	private List<User> users;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
