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

package de.u808.simpleinquest.service.impl;

import java.io.File;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import de.u808.simpleinquest.config.MimeIconMapBean;
import de.u808.simpleinquest.service.MimeIconRegistry;
import de.u808.simpleinquest.service.MimeTypeRegistry;

public class DefaultMimeIconRegistry implements MimeIconRegistry {

    private MimeIconMapBean mimeIconMapBean;

    private MimeTypeRegistry mimeTypeRegistry;

    public String getMimeIcon(String filename) {
        String mimeType = this.mimeTypeRegistry.getMimeType(filename);
        return getIcon(mimeType);
    }

    public String getMimeIcon(File file) {
        String mimeType = this.mimeTypeRegistry.getMimeType(file);
        return getIcon(mimeType);
    }

    private String getSuitableType(String[] types) {
        Map iconMap = this.mimeIconMapBean.getMimeIconMap().getMap();
        for (String type : types) {
            if (iconMap.containsKey(type)) {
                return type;
            }
        }
        return null;
    }

    private String getIcon(String mimeType) {
        String singleType = null;
        if (StringUtils.isNotEmpty(mimeType) && mimeType.contains(",")) {
            String[] types = mimeType.split(",");
            singleType = this.getSuitableType(types);
        } else {
            singleType = mimeType;
        }
        return this.mimeIconMapBean.getMimeIconMap().getIconURI(singleType);
    }

    public MimeIconMapBean getMimeIconMapBean() {
        return mimeIconMapBean;
    }

    public void setMimeIconMapBean(MimeIconMapBean mimeIconMapBean) {
        this.mimeIconMapBean = mimeIconMapBean;
    }

    public void setMimeTypeRegistry(MimeTypeRegistry mimeTypeRegistry) {
        this.mimeTypeRegistry = mimeTypeRegistry;
    }

}
