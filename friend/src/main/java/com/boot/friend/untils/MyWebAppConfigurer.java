package com.boot.friend.untils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

	private static final Logger log = LoggerFactory.getLogger(MyWebAppConfigurer.class);
	@Value(value = "${customs.static-locations}")
	private String locations;

	@Value(value = "${spring.mvc.static-path-pattern}")
	private String static_path_pattern;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		log.info("locations=[{}];static_path_pattern=[{}]", locations, static_path_pattern);
		registry.addResourceHandler(static_path_pattern).addResourceLocations(locations);
		super.addResourceHandlers(registry);
	}
}
