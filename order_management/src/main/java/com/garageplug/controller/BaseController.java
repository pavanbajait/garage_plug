package com.garageplug.controller;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

public class BaseController {
	  Logger logger = LoggerFactory.getLogger(BaseController.class);

	  public void processDeferredResult(final DeferredResult<ResponseEntity<?>> df,
	      CompletableFuture<ResponseEntity<?>> cf, String apiEndPoint, Long startTime) {
	    cf.thenAccept(result -> {
	      if (df.hasResult()) {
	        return;
	      }
	      df.setResult(result);
	    });
	    logger.info("Request processed apiEndPoint: {},  execTime: {}", apiEndPoint,
	        System.currentTimeMillis() - startTime);
	  }
}
