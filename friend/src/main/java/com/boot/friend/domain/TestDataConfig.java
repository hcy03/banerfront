package com.boot.friend.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ConfigurationProperties(prefix = "app")
@Configuration
@PropertySource(value = "classpath:config/test_data.properties")
public class TestDataConfig {

	private String initdata;

	public String getInitdata() {
		return initdata;
	}

	public void setInitdata(String initdata) {
		this.initdata = initdata;
	}

}
