/*
 * Copyright 2002-2007 the original author or authors.
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

package de.u808.simpleinquest.util;

import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.extras.complete.CompleteAutoloadTilesInitializer;
import org.apache.tiles.servlet.context.ServletTilesApplicationContext;
import org.apache.tiles.startup.DefaultTilesInitializer;
import org.apache.tiles.web.util.ServletContextAdapter;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

public class SpringTilesConfigurer implements ServletContextAware, InitializingBean, DisposableBean {

	protected final static Log logger = LogFactory.getLog(SpringTilesConfigurer.class);

    private final Properties tilesPropertyMap = new Properties();
    
    private DefaultTilesInitializer tilesInitializer = null;

    private ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void afterPropertiesSet() throws Exception {
		ServletContextAdapter adaptedContext = new ServletContextAdapter(new DelegatingServletConfig());
        this.initialize(new ServletTilesApplicationContext(adaptedContext));
	}

	public void initialize(TilesApplicationContext tilesApplicationContext) {
		tilesInitializer = new DefaultTilesInitializer();
		logger.info("Init Tiles");
		tilesInitializer.initialize(tilesApplicationContext);
		logger.info("Tiles initialized");
	}

	public void destroy() throws Exception {
		this.tilesInitializer.destroy();
	}

    /**
     * Internal implementation of the ServletConfig interface, to be passed
     * to the wrapped servlet. Delegates to ServletWrappingController fields
     * and methods to provide init parameters and other environment info.
     */
    private class DelegatingServletConfig implements ServletConfig {

        public String getServletName() {
            return "TilesConfigurer";
        }

        public ServletContext getServletContext() {
            return servletContext;
        }

        public String getInitParameter(String paramName) {
            return tilesPropertyMap.getProperty(paramName);
        }

        public Enumeration<?> getInitParameterNames() {
            return tilesPropertyMap.keys();
        }
    }

}
