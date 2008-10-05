package de.u808.simpleinquest.web.usermanagement;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import de.u808.simpleinquest.domain.User;
import de.u808.simpleinquest.service.UserManager;

public class UserEditController extends SimpleFormController{
	
	UserManager userManager;
	
	public UserEditController(){
		setCommandClass(User.class);
		setCommandName("user");
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		if(StringUtils.isNotEmpty(request.getParameter("id"))){
			return userManager.fetchUser(Long.valueOf(request.getParameter("id")));
		}
		else return super.formBackingObject(request);
	}
	
	protected ModelAndView onSubmit(Object command){
		User user = (User) command;
		user = userManager.makePersistent(user);
		return new ModelAndView(getSuccessView(), "userID", Long.toString(user.getId()));
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
}
