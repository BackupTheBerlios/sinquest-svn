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

package de.u808.simpleinquest.test.repository;

import static org.junit.Assert.assertNotNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.u808.simpleinquest.domain.User;
import de.u808.simpleinquest.repository.UserDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:test-applicationContext.xml"}) 
public class UserDAOTests {
	
	@Autowired
	private static ApplicationContext applicationContext;
    
    @Autowired
    private static UserDAO userDAO;
    private static String TEST_USER_NAME = "test";
    private static User user;
    
	@BeforeClass
	public static void prepare(){
        User user = new User();
        user.setFirstName("Andreas");
        user.setLastName("Friedel");
        user.setUsername(TEST_USER_NAME);
        user.setPassword("123");
        UserDAOTests.user = userDAO.makePersistent(user);
	}

	@AfterClass
	public static void discard(){
		userDAO.makeTransient(user);
	}
	
	@Test
	public void fetchTestUser(){
		User testUser = userDAO.findByUsername(TEST_USER_NAME);
		assertNotNull(testUser);
	}

}
