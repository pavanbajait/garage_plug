package com.garageplug.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.garageplug.dos.CustomerLoginDO;
import com.garageplug.entities.CurrentUserSession;
import com.garageplug.entities.Customer;
import com.garageplug.enums.CustomerType;
import com.garageplug.repository.CurrentUserSessionDAO;
import com.garageplug.repository.CustomerDAO;
import com.garageplug.config.StringUtils;
import com.garageplug.constants.ErrorConstants;
import com.garageplug.constants.ErrorMessages;
import com.garageplug.constants.ErrorResponse;
import com.garageplug.constants.OkResponseDO;
import com.garageplug.constants.ResponseError;
import com.garageplug.constants.ResponseSuccess;

import net.bytebuddy.utility.RandomString;

@Service
public class CustomerServiceImpl implements CustomerService{
	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private CurrentUserSessionDAO currentUserSessionDAO;

//	@Override
//	@Async("asyncExecutor")
//	public Customer createCustomer(Customer customer) {
//		customer.setCustomerType(CustomerType.REGULAR);
//		Customer customerDBO =customerDAO.save(customer);
//		return customerDBO;
//	}
//	
//	@Override
//	@Async("asyncExecutor")
//	public Customer updateCustomer(Customer customer, String key)throws CustomerException {
//		CurrentUserSession custUserSession=	currentUserSessionDAO.findByuuid(key).orElseThrow(()-> new CustomerException("Customer Not Logged in"));
//		if(customer.getId()==custUserSession.getId()) {
//			return customerDAO.save(customer);
//		}
//		else
//			throw new CustomerException("Please Enter a correct id");
//
//	}
//
//	@Override
//	@Async("asyncExecutor")
//	public String customerlogin(CustomerLoginDO details) throws CustomerException {
//		Customer existingCustomer= customerDAO.findByEmail(details.getEmail());
//		
//		if(existingCustomer == null) {	
//			throw new CustomerException("Please Enter a valid customer Email");	 
//		}
//		
//		Optional<CurrentUserSession> validCustomerSessionOpt =  currentUserSessionDAO.findById(existingCustomer.getId());
//		
//		if(validCustomerSessionOpt.isPresent()) {
//			throw new CustomerException("User already Logged In with this credential");
//		}
//		
//		if(existingCustomer.getPassword().equals(details.getPassword())) {			
//			String key= RandomString.make(6);			
//			CurrentUserSession currentUserSession = new CurrentUserSession();
//			currentUserSession.setId(existingCustomer.getId());
//			currentUserSession.setLocalDateTime(LocalDateTime.now());
//			currentUserSession.setUuid(key);
//			
//			currentUserSessionDAO.save(currentUserSession);
//
//			return currentUserSession.toString();
//		}
//		else
//			throw new CustomerException("Please Enter a valid password");	
//	}
//
//	@Override
//	@Async("asyncExecutor")
//	public String customerlogout(String key) throws CustomerException {
//		Optional<CurrentUserSession> validCustomerSession = currentUserSessionDAO.findByuuid(key);
//		
//		if(validCustomerSession.get() == null) {
//			throw new CustomerException("User Not Logged In with this number");			
//		}	
//		currentUserSessionDAO.delete(validCustomerSession.get());
//	
//		return "Logged Out !";
//	}
//
//	@Override
//	@Async("asyncExecutor")
//	public List<Customer> viewAllCustomers() throws CustomerException {
//		List<Customer> list = customerDAO.findAll();
//		if(list.size()<0) {
//			throw new CustomerException("Please add customers");	
//		}else
//			return list;
//	}

	@Override
	@Async("asyncExecutor")
	public void createCustomer(Customer customer, CompletableFuture<ResponseEntity<?>> cf) {
		ErrorResponse errorResponse;
	    try {
	    	customer.setCustomerType(CustomerType.REGULAR);
			customerDAO.save(customer);
		    cf.complete(ResponseEntity
		          .ok(new OkResponseDO<>(ResponseSuccess.SUCCESS_CUSTOMER_CREATION.getMessage())));
	    } catch (Exception e) {
	      logger.error("Exception while creating customer request ",
	          StringUtils.printStackTrace(e));
	      errorResponse =
	          new ErrorResponse(ErrorConstants.UNKNOWN_ERROR_CODE, ErrorMessages.UNKNOWN_ERROR_MESSAGE);
	      cf.complete(ResponseEntity.ok(errorResponse));
	    }
	}

	@Override
	@Async("asyncExecutor")
	public void updateCustomer(Customer customer, String key, CompletableFuture<ResponseEntity<?>> cf) {
		ErrorResponse errorResponse;
	    try {
	    	Optional<CurrentUserSession> custUserSession=	currentUserSessionDAO.findByuuid(key);
	    	if(customer.getId()!=custUserSession.get().getId()) {
	    		errorResponse = new ErrorResponse(ResponseError.CUSTOMER_NOT_LOGGED_IN.getCode(), ResponseError.CUSTOMER_NOT_LOGGED_IN.getMessage());
	    		cf.complete(ResponseEntity.ok(errorResponse));
	            return;
	    	}
	    	customerDAO.save(customer);
		    cf.complete(ResponseEntity
		          .ok(new OkResponseDO<>(ResponseSuccess.SUCCESS_CUSTOMER_UPDATION.getMessage())));
	    } catch (Exception e) {
	      logger.error("Exception while update customer request ",
	          StringUtils.printStackTrace(e));
	      errorResponse =
	          new ErrorResponse(ErrorConstants.UNKNOWN_ERROR_CODE, ErrorMessages.UNKNOWN_ERROR_MESSAGE);
	      cf.complete(ResponseEntity.ok(errorResponse));
	    }
		
	}

	@Override
	@Async("asyncExecutor")
	public void customerlogin(CustomerLoginDO details, CompletableFuture<ResponseEntity<?>> cf) {
		ErrorResponse errorResponse;
	    try {
	    	Customer existingCustomer= customerDAO.findByEmail(details.getEmail());
			
			if(existingCustomer == null) {	
				errorResponse = new ErrorResponse(ResponseError.INVALID_EMAIL_ID.getCode(), ResponseError.INVALID_EMAIL_ID.getMessage());
	    		cf.complete(ResponseEntity.ok(errorResponse));
	            return;
			}
			
			Optional<CurrentUserSession> validCustomerSessionOpt =  currentUserSessionDAO.findById(existingCustomer.getId());
			
			if(validCustomerSessionOpt.isPresent()) {
				errorResponse = new ErrorResponse(ResponseError.CUSTOMER_AREADY_LOGGED_IN.getCode(), ResponseError.CUSTOMER_AREADY_LOGGED_IN.getMessage());
	    		cf.complete(ResponseEntity.ok(errorResponse));
	            return;
			}
			
			if(!existingCustomer.getPassword().equals(details.getPassword())) {			
				errorResponse = new ErrorResponse(ResponseError.INVALID_PASSWORD.getCode(), ResponseError.INVALID_PASSWORD.getMessage());
	    		cf.complete(ResponseEntity.ok(errorResponse));
	            return;
			}
			String key= RandomString.make(6);			
			CurrentUserSession currentUserSession = new CurrentUserSession();
			currentUserSession.setId(existingCustomer.getId());
			currentUserSession.setLocalDateTime(LocalDateTime.now());
			currentUserSession.setUuid(key);
			
			currentUserSessionDAO.save(currentUserSession);
			cf.complete(ResponseEntity
			          .ok(new OkResponseDO<>(ResponseSuccess.SUCCESS_CUSTOMER_LOGIN.getMessage())));
		    
	    } catch (Exception e) {
	      logger.error("Exception while login customer request ",
	          StringUtils.printStackTrace(e));
	      errorResponse =
	          new ErrorResponse(ErrorConstants.UNKNOWN_ERROR_CODE, ErrorMessages.UNKNOWN_ERROR_MESSAGE);
	      cf.complete(ResponseEntity.ok(errorResponse));
	    }
	}

	@Override
	@Async("asyncExecutor")
	public void customerlogout(String key, CompletableFuture<ResponseEntity<?>> cf) {
		ErrorResponse errorResponse;
	    try {
	    	Optional<CurrentUserSession> validCustomerSession = currentUserSessionDAO.findByuuid(key);
			
			if(validCustomerSession.get() == null) {
				errorResponse = new ErrorResponse(ResponseError.CUSTOMER_NOT_LOGGED_IN.getCode(), ResponseError.CUSTOMER_NOT_LOGGED_IN.getMessage());
	    		cf.complete(ResponseEntity.ok(errorResponse));
	            return;		
			}
			
	    	currentUserSessionDAO.delete(validCustomerSession.get());
			cf.complete(ResponseEntity
			          .ok(new OkResponseDO<>(ResponseSuccess.SUCCESS_CUSTOMER_LOGOUT.getMessage())));
		   
	    } catch (Exception e) {
	      logger.error("Exception while logout customer request ",
	          StringUtils.printStackTrace(e));
	      errorResponse =
	          new ErrorResponse(ErrorConstants.UNKNOWN_ERROR_CODE, ErrorMessages.UNKNOWN_ERROR_MESSAGE);
	      cf.complete(ResponseEntity.ok(errorResponse));
	    }
	}

	@Override
	@Async("asyncExecutor")
	public void viewAllCustomers(CompletableFuture<ResponseEntity<?>> cf) {
		ErrorResponse errorResponse;
	    try {
	    	List<Customer> list = customerDAO.findAll();
			if(list.size()<=0) {
				errorResponse = new ErrorResponse(ResponseError.CUSTOMER_NOT_FOUND.getCode(), ResponseError.CUSTOMER_NOT_FOUND.getMessage());
	    		cf.complete(ResponseEntity.ok(errorResponse));
	            return;	
			}
			cf.complete(ResponseEntity
			          .ok(new OkResponseDO<>(list)));
	    } catch (Exception e) {
	      logger.error("Exception while GET ALL customer request ",
	          StringUtils.printStackTrace(e));
	      errorResponse =
	          new ErrorResponse(ErrorConstants.UNKNOWN_ERROR_CODE, ErrorMessages.UNKNOWN_ERROR_MESSAGE);
	      cf.complete(ResponseEntity.ok(errorResponse));
	    }
		
	}

}
