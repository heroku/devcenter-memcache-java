package com.heroku.devcenter.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

	@Bean
	public MemcacheConfig getMemcachedConfig() {
		MemcacheConfig mcConfig = new MemcacheConfig();
		mcConfig.setServers(System.getenv("MEMCACHE_SERVERS"));
		mcConfig.setUsername(System.getenv("MEMCACHE_USERNAME"));
		mcConfig.setPassword(System.getenv("MEMCACHE_PASSWORD"));
		return mcConfig;
	}
}
