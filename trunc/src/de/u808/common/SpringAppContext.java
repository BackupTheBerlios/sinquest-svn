package de.u808.common;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

public class SpringAppContext implements ServletContextAware {
	
	private static ServletContext servletContext;

	public void setServletContext(ServletContext arg0) {
		servletContext = arg0;
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}
}
