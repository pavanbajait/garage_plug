package com.garageplug.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.garageplug.constants.PropertyConstants;

@Configuration
public class AsyncConfiguration {

	@Bean(name = "asyncExecutor")
	  public ThreadPoolTaskExecutor asyncExecutor() {
	    ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
	    threadPoolTaskExecutor
	        .setMaxPoolSize(PropertyConstants.DEFAULT_MAX_POOL_SIZE);
	    threadPoolTaskExecutor
	        .setCorePoolSize(PropertyConstants.DEFAULT_CORE_POOL_SIZE);
	    threadPoolTaskExecutor
	        .setQueueCapacity(PropertyConstants.DEFAULT_QUEUE_CAPACITY);
	    threadPoolTaskExecutor.setAwaitTerminationSeconds(
	        PropertyConstants.DEFAULT_AWAIT_TERMINATION_SECONDS);
	    return threadPoolTaskExecutor;
	  }
}
