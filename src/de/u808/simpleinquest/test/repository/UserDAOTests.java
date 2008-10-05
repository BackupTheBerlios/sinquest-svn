package de.u808.simpleinquest.test.repository;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.u808.simpleinquest.domain.User;
import de.u808.simpleinquest.repository.UserDAO;

public class UserDAOTests {
	
    private ApplicationContext ctx = null;
    private User user = null;
    private UserDAO userDAO = null;
    private String TEST_USER_NAME = "andy";
    
	@Before
	public void prepare(){
		String[] paths = {"de/u808/simpleinquest/applicationContext.xml"};
        ctx = new ClassPathXmlApplicationContext(paths);
        
        userDAO = (UserDAO) ctx.getBean("userDAO");
        user = new User();
        user.setFirstName("Andreas");
        user.setLastName("Friedel");
        user.setUsername(TEST_USER_NAME);
        user.setPassword("123");
        userDAO.makePersistent(user);
	}
	
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@After
	public void discard(){
		//userDAO.makeTransient(user);
		//userDAO = null;
	}
	
	@Test
	public void fetchTestUser(){
		User testUser = userDAO.findByUsername(TEST_USER_NAME);
		assertNotNull(testUser);
	}

}
