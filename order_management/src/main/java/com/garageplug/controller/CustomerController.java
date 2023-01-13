package com.garageplug.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.FieldError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.garageplug.dos.CustomerLoginDO;
import com.garageplug.entities.Customer;
import com.garageplug.entities.Orders;
import com.garageplug.service.CustomerService;
import com.garageplug.service.CustomerServiceImpl;
import com.garageplug.config.StringUtils;
import com.garageplug.constants.ErrorConstants;
import com.garageplug.constants.ErrorMessages;
import com.garageplug.constants.ErrorResponse;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/login")
	public DeferredResult<ResponseEntity<?>> userLoginHandler( @RequestBody CustomerLoginDO details){
		DeferredResult<ResponseEntity<?>> df = new DeferredResult<ResponseEntity<?>>();
	    
	    try {
	      logger.debug("Received customer login request for email : "+ details.getEmail() );
	      CompletableFuture<ResponseEntity<?>> cf = new CompletableFuture<ResponseEntity<?>>();
	      String apiEndPoint = "/v1/customer/login";
	      long startTime = System.currentTimeMillis();
	      customerService.customerlogin(details, cf);
	      processDeferredResult(df, cf, apiEndPoint, startTime);
	    } catch (Exception e) {
	      logger.error("Received customer login request request failed by ", StringUtils.printStackTrace(e));
	      ErrorResponse errorResponse = new ErrorResponse();
	        errorResponse.setCode(ErrorConstants.UNKNOWN_ERROR_CODE);
	        errorResponse.setMessage(ErrorMessages.UNKNOWN_ERROR_MESSAGE);
	      
	      df.setErrorResult(ResponseEntity.ok(errorResponse));
	    }
	    return df;
		 
	}
	
	@DeleteMapping("/logout")
	public DeferredResult<ResponseEntity<?>> userLogoutHandler(@RequestParam String key ) {
		DeferredResult<ResponseEntity<?>> df = new DeferredResult<ResponseEntity<?>>();
	    
	    try {
	      logger.debug("Received customer logout request ") ;
	      CompletableFuture<ResponseEntity<?>> cf = new CompletableFuture<ResponseEntity<?>>();
	      String apiEndPoint = "/v1/customer/logout";
	      long startTime = System.currentTimeMillis();
	      customerService.customerlogout(key, cf);
	      processDeferredResult(df, cf, apiEndPoint, startTime);
	    } catch (Exception e) {
	      logger.error("Received customer logout request request failed by ", StringUtils.printStackTrace(e));
	      ErrorResponse errorResponse = new ErrorResponse();
	        errorResponse.setCode(ErrorConstants.UNKNOWN_ERROR_CODE);
	        errorResponse.setMessage(ErrorMessages.UNKNOWN_ERROR_MESSAGE);
	      
	      df.setErrorResult(ResponseEntity.ok(errorResponse));
	    }
	    return df;
		
	}
	
	@PostMapping("/create")
	public DeferredResult<ResponseEntity<?>> createCustomer(@RequestBody Customer customer) {
		DeferredResult<ResponseEntity<?>> df = new DeferredResult<ResponseEntity<?>>();
	    
	    try {
	      logger.debug("Received customer create request with customer emailId : "+customer.getEmail()) ;
	      CompletableFuture<ResponseEntity<?>> cf = new CompletableFuture<ResponseEntity<?>>();
	      String apiEndPoint = "/v1/customer/create";
	      long startTime = System.currentTimeMillis();
	      customerService.createCustomer(customer, cf);
	      processDeferredResult(df, cf, apiEndPoint, startTime);
	    } catch (Exception e) {
	      logger.error("Received customer create request request failed by ", StringUtils.printStackTrace(e));
	      ErrorResponse errorResponse = new ErrorResponse();
	        errorResponse.setCode(ErrorConstants.UNKNOWN_ERROR_CODE);
	        errorResponse.setMessage(ErrorMessages.UNKNOWN_ERROR_MESSAGE);
	      
	      df.setErrorResult(ResponseEntity.ok(errorResponse));
	    }
	    return df;
		
	}
	
	@PutMapping("/update")
	public DeferredResult<ResponseEntity<?>> updateCustomer(@RequestBody Customer customer,@RequestParam String key)
	{
		DeferredResult<ResponseEntity<?>> df = new DeferredResult<ResponseEntity<?>>();
	    
	    try {
	      logger.debug("Received customer update request for customer emailID : "+customer.getEmail()) ;
	      CompletableFuture<ResponseEntity<?>> cf = new CompletableFuture<ResponseEntity<?>>();
	      String apiEndPoint = "/v1/customer/update";
	      long startTime = System.currentTimeMillis();
	      customerService.updateCustomer(customer, key, cf);
	      processDeferredResult(df, cf, apiEndPoint, startTime);
	    } catch (Exception e) {
	      logger.error("Received customer update request request failed by ", StringUtils.printStackTrace(e));
	      ErrorResponse errorResponse = new ErrorResponse();
	        errorResponse.setCode(ErrorConstants.UNKNOWN_ERROR_CODE);
	        errorResponse.setMessage(ErrorMessages.UNKNOWN_ERROR_MESSAGE);
	      
	      df.setErrorResult(ResponseEntity.ok(errorResponse));
	    }
	    return df;
		
	}
	
	@GetMapping("/view-all-customers")
	public DeferredResult<ResponseEntity<?>> viewAllCustomers( ) {
		DeferredResult<ResponseEntity<?>> df = new DeferredResult<ResponseEntity<?>>();
	    
	    try {
	      logger.debug("Received view all customers request") ;
	      CompletableFuture<ResponseEntity<?>> cf = new CompletableFuture<ResponseEntity<?>>();
	      String apiEndPoint = "/v1/customer/view-all-customers";
	      long startTime = System.currentTimeMillis();
	      customerService.viewAllCustomers(cf);
	      processDeferredResult(df, cf, apiEndPoint, startTime);
	    } catch (Exception e) {
	      logger.error("Received view all customers request failed by ", StringUtils.printStackTrace(e));
	      ErrorResponse errorResponse = new ErrorResponse();
	        errorResponse.setCode(ErrorConstants.UNKNOWN_ERROR_CODE);
	        errorResponse.setMessage(ErrorMessages.UNKNOWN_ERROR_MESSAGE);
	      
	      df.setErrorResult(ResponseEntity.ok(errorResponse));
	    }
	    return df;
//		List<Customer> customers= customerService.viewAllCustomers();
		
		
	}
	
	
}