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
