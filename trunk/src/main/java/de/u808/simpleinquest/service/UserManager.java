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
