package com.garageplug.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.garageplug.entities.Customer;
import com.garageplug.entities.Orders;
import com.garageplug.config.StringUtils;
import com.garageplug.constants.ErrorConstants;
import com.garageplug.constants.ErrorMessages;
import com.garageplug.constants.ErrorResponse;
import com.garageplug.dos.CustomerLoginDO;
import com.garageplug.dos.OrderDO;
import com.garageplug.service.CustomerService;
import com.garageplug.service.OrderService;
import com.garageplug.service.OrderServiceImpl;

@RestController
@RequestMapping("/v1/order")
public class OrderController extends BaseController  {
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/create")
	public DeferredResult<ResponseEntity<?>> createOrder(@RequestBody OrderDO orderDO) {
		DeferredResult<ResponseEntity<?>> df = new DeferredResult<ResponseEntity<?>>();
	    
	    try {
	      logger.debug("Received create order request bu customerId : "+orderDO.getCustomerId()) ;
	      CompletableFuture<ResponseEntity<?>> cf = new CompletableFuture<ResponseEntity<?>>();
	      String apiEndPoint = "/v1/order/create";
	      long startTime = System.currentTimeMillis();
	      orderService.createOrder(orderDO, cf);
	      processDeferredResult(df, cf, apiEndPoint, startTime);
	    } catch (Exception e) {
	      logger.error("Received customer create request request failed by ", StringUtils.printStackTrace(e));
	      ErrorResponse errorResponse = new ErrorResponse();
	        errorResponse.setCode(ErrorConstants.UNKNOWN_ERROR_CODE);
	        errorResponse.setMessage(ErrorMessages.UNKNOWN_ERROR_MESSAGE);
	      
	      df.setErrorResult(ResponseEntity.ok(errorResponse));
	    }
	    return df;
//		Orders order = orderService.createOrder(orderDO);
//		return ResponseEntity.ok(order) ;
	}
	
	@GetMapping("/get-all-orders")
	public DeferredResult<ResponseEntity<?>> viewAllOrders(@RequestParam(required = false) String key) {
		DeferredResult<ResponseEntity<?>> df = new DeferredResult<ResponseEntity<?>>();
	    
	    try {
	      logger.debug("Received view all orders request") ;
	      CompletableFuture<ResponseEntity<?>> cf = new CompletableFuture<ResponseEntity<?>>();
	      String apiEndPoint = "/v1/order/get-all-orders";
	      long startTime = System.currentTimeMillis();
	      orderService.viewAllOrders(key,cf);
	      processDeferredResult(df, cf, apiEndPoint, startTime);
	    } catch (Exception e) {
	      logger.error("Received view all orders request failed by ", StringUtils.printStackTrace(e));
	      ErrorResponse errorResponse = new ErrorResponse();
	        errorResponse.setCode(ErrorConstants.UNKNOWN_ERROR_CODE);
	        errorResponse.setMessage(ErrorMessages.UNKNOWN_ERROR_MESSAGE);
	      
	      df.setErrorResult(ResponseEntity.ok(errorResponse));
	    }
	    return df;
//		List<Orders> orders= orderService.viewAllOrders(key);
//		
//		return new ResponseEntity<List<Orders>>(orders,HttpStatus.OK);
		
	}

	@GetMapping("/find-by-id/{id}")
	public DeferredResult<ResponseEntity<?>> viewOrder(@PathVariable("id") int orderId,
			@RequestParam(required = false) String key){
		DeferredResult<ResponseEntity<?>> df = new DeferredResult<ResponseEntity<?>>();
	    
	    try {
	      logger.debug("Received find order by orderId request") ;
	      CompletableFuture<ResponseEntity<?>> cf = new CompletableFuture<ResponseEntity<?>>();
	      String apiEndPoint = "/v1/order/find-by-id/{id}";
	      long startTime = System.currentTimeMillis();
	      orderService.viewOrder(orderId,key,cf);
	      processDeferredResult(df, cf, apiEndPoint, startTime);
	    } catch (Exception e) {
	      logger.error("Received find order by orderId  request failed by ", StringUtils.printStackTrace(e));
	      ErrorResponse errorResponse = new ErrorResponse();
	        errorResponse.setCode(ErrorConstants.UNKNOWN_ERROR_CODE);
	        errorResponse.setMessage(ErrorMessages.UNKNOWN_ERROR_MESSAGE);
	      
	      df.setErrorResult(ResponseEntity.ok(errorResponse));
	    }
	    return df;
//		Orders orders= orderService.viewOrder(orderId,key);
//		
//		return new ResponseEntity<Orders>(orders,HttpStatus.OK);
	}
	

}