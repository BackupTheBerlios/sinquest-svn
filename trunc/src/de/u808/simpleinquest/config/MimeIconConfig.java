package de.u808.simpleinquest.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.config.java.annotation.Bean;
import org.springframework.config.java.annotation.Configuration;

@Configuration
public class MimeIconConfig {

    @Bean
    public Map<String, Class> mimeIconMap(){
        return new LinkedHashMap<String, Class>();
    }
}
