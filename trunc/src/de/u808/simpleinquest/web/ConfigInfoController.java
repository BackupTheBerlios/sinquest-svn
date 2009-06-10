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

package de.u808.simpleinquest.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContextAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import de.u808.simpleinquest.config.ConfigurationValidatorBean;

public class ConfigInfoController extends AbstractController implements ApplicationContextAware {

    private ConfigurationValidatorBean configurationValidatorBean;

    public ConfigurationValidatorBean getConfigurationValidatorBean() {
        return configurationValidatorBean;
    }

    public void setConfigurationValidatorBean(ConfigurationValidatorBean configurationValidatorBean) {
        this.configurationValidatorBean = configurationValidatorBean;
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return new ModelAndView("configInfo", "errors", configurationValidatorBean.validate()) {
        };
    }

}
