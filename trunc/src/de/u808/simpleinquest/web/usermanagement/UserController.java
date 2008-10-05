package de.u808.simpleinquest.web.usermanagement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import de.u808.simpleinquest.service.UserManager;

public class UserController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private UserManager userManager;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserViewModel model = new UserViewModel();
		model.setUsers(userManager.fetchAllUser());
		return new ModelAndView("userList", "model", model);		
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

}
