package com.garageplug.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.garageplug.config.StringUtils;
import com.garageplug.constants.ErrorConstants;
import com.garageplug.constants.ErrorMessages;
import com.garageplug.constants.ErrorResponse;
import com.garageplug.constants.OkResponseDO;
import com.garageplug.constants.ResponseError;
import com.garageplug.converter.Converter;
import com.garageplug.dos.OrderDO;
import com.garageplug.entities.CurrentUserSession;
import com.garageplug.entities.Customer;
import com.garageplug.entities.Orders;
import com.garageplug.enums.CustomerType;
import com.garageplug.repository.CurrentUserSessionDAO;
import com.garageplug.repository.CustomerDAO;
import com.garageplug.repository.OrderDAO;
import com.garageplug.transactional.OrderHelper;

@Service
public class OrderServiceImpl implements OrderService {
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private Converter converter;
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private CurrentUserSessionDAO currentUserSessionDAO;
	
	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private OrderHelper orderhelper;
	
//	@Override
//	@Async("asyncExecutor")
//	public Orders createOrder(OrderDO orderDO) throws OrderException {
//		Orders order = converter.convertOrderDTOtoOrder(orderDO);
//		Optional<Customer> opt = customerDAO.findById(orderDO.getCustomerId());
//		if(!opt.isPresent()) {
//			throw new OrderException("Customer is not present With entered customerId");
//		}
//		Customer customer = opt.get();
//		customer.getOrderslist().add(order);
//		List<Orders> list = customer.getOrderslist();
//		if(list.size()>=10) {
//			customer.setCustomerType(CustomerType.GOLD);
//			order.setCustomer(customer);
//			order.setDiscount(10);
//			double discount = order.getTotalCost()/10;
//			order.setDiscountCost(discount);
//			order.setTotalCost(order.getTotalCost()-order.getDiscountCost());
//			return orderhelper.createOrderTransaction(customer, order);
//		}else if(list.size()>=20) {
//			customer.setCustomerType(CustomerType.PLATINUM);
//			return orderhelper.createOrderTransaction(customer, order);
//		}
//		return null;
//	}
//
//	@Override
//	@Async("asyncExecutor")
//	public List<Orders> viewAllOrders(String key) throws OrderException {
//			CurrentUserSession cus = currentUserSessionDAO.findByuuid(key).orElseThrow(() -> new OrderException("Please login First..."));
//			
//			Optional<Customer> customer = customerDAO.findById(cus.getId());
//			if(customer.isPresent())
//			{
//				List<Orders> orders= orderDAO.findAll();
//				if(orders.size()>0)
//				{
//					return orders;
//				}
//				else {
//					throw new OrderException("No order found");
//				}
//				
//			}
//			else
//				throw new OrderException("Access denied!");
//	}
//
//	@Override
//	@Async("asyncExecutor")
//	public Orders viewOrder(int orderId, String key) throws OrderException {
//		CurrentUserSession cus = currentUserSessionDAO.findByuuid(key).orElseThrow(() -> new OrderException("Please login First..."));
//		
//		Optional<Customer> customer = customerDAO.findById(cus.getId());
//		if(customer.isPresent())
//		{
//			Optional<Orders> optional=orderDAO.findById(orderId);
//			if(optional.isPresent())
//			{
//				return optional.get();
//			}
//			else {
//				throw new OrderException("Order not found");
//			}
//		}
//		else
//			throw new OrderException("Access denied!");
//	}

	@Override
	@Async("asyncExecutor")
	public void createOrder(OrderDO orderDO, CompletableFuture<ResponseEntity<?>> cf) {
		ErrorResponse errorResponse;
	    try {
	    	Orders order = converter.convertOrderDTOtoOrder(orderDO);
			Optional<Customer> opt = customerDAO.findById(orderDO.getCustomerId());
			if(!opt.isPresent()) {
				errorResponse = new ErrorResponse(ResponseError.CUSTOMER_NOT_PRESENT.getCode(), ResponseError.CUSTOMER_NOT_PRESENT.getMessage());
	    		cf.complete(ResponseEntity.ok(errorResponse));
	            return;
			}
			Customer customer = opt.get();
			customer.getOrderslist().add(order);
			List<Orders> list = customer.getOrderslist();
			if(list.size()>=10 && list.size()<20) {
				customer.setCustomerType(CustomerType.GOLD);
				order.setDiscount(10);
				double discount = order.getTotalCost()/10;
				order.setDiscountCost(discount);
				order.setTotalCost(order.getTotalCost()-order.getDiscountCost());	
				order.setCustomer(customer);
				Orders orders = orderhelper.createOrderTransaction(customer, order);
			}else if(list.size()>=20) {
				customer.setCustomerType(CustomerType.PLATINUM);
				order.setDiscount(20);
				double discount = order.getTotalCost()/5;
				order.setDiscountCost(discount);
				order.setTotalCost(order.getTotalCost()-order.getDiscountCost());	
				order.setCustomer(customer);
				Orders orders = orderhelper.createOrderTransaction(customer, order);
			}		
			order.setCustomer(customer);
			Orders orders = orderhelper.createOrderTransaction(customer, order);
			cf.complete(ResponseEntity
			          .ok(new OkResponseDO<>(orders)));
	    } catch (Exception e) {
	      logger.error("Exception while create order request ",
	          StringUtils.printStackTrace(e));
	      errorResponse =
	          new ErrorResponse(ErrorConstants.UNKNOWN_ERROR_CODE, ErrorMessages.UNKNOWN_ERROR_MESSAGE);
	      cf.complete(ResponseEntity.ok(errorResponse));
	    }
		
	}

	@Override
	@Async("asyncExecutor")
	public void viewAllOrders(String key, CompletableFuture<ResponseEntity<?>> cf) {
	
		ErrorResponse errorResponse;
	    try {
	    	Optional<CurrentUserSession> validCustomerSession = currentUserSessionDAO.findByuuid(key);
			
			if(validCustomerSession.get() == null) {
				errorResponse = new ErrorResponse(ResponseError.CUSTOMER_NOT_LOGGED_IN.getCode(), ResponseError.CUSTOMER_NOT_LOGGED_IN.getMessage());
	    		cf.complete(ResponseEntity.ok(errorResponse));
	            return;		
			}
//			Optional<Customer> customer = customerDAO.findById(validCustomerSession.get().getId());
			
//			List<Orders> orders= customer.getOrderslist();
			List<Orders> orders= orderDAO.findByCustomerId(validCustomerSession.get().getId());
			if(orders.size()<=0)
			{
				errorResponse = new ErrorResponse(ResponseError.ORDERS_NOT_FOUND.getCode(), ResponseError.ORDERS_NOT_FOUND.getMessage());
	    		cf.complete(ResponseEntity.ok(errorResponse));
	            return;	
			}
			
			cf.complete(ResponseEntity
			          .ok(new OkResponseDO<>(orders)));
	    } catch (Exception e) {
	      logger.error("Exception while GET ALL orders request ",
	          StringUtils.printStackTrace(e));
	      errorResponse =
	          new ErrorResponse(ErrorConstants.UNKNOWN_ERROR_CODE, ErrorMessages.UNKNOWN_ERROR_MESSAGE);
	      cf.complete(ResponseEntity.ok(errorResponse));
	    }
		
	}

	@Override
	@Async("asyncExecutor")
	public void viewOrder(int orderId, String key, CompletableFuture<ResponseEntity<?>> cf) {

		ErrorResponse errorResponse;
	    try {
	    	Optional<CurrentUserSession> validCustomerSession = currentUserSessionDAO.findByuuid(key);
			
			if(validCustomerSession.get() == null) {
				errorResponse = new ErrorResponse(ResponseError.CUSTOMER_NOT_LOGGED_IN.getCode(), ResponseError.CUSTOMER_NOT_LOGGED_IN.getMessage());
	    		cf.complete(ResponseEntity.ok(errorResponse));
	            return;		
			}
			Optional<Orders> orders = orderDAO.findById(orderId);
			if(orders==null) {
				errorResponse = new ErrorResponse(ResponseError.ORDERS_NOT_FOUND.getCode(), ResponseError.ORDERS_NOT_FOUND.getMessage());
	    		cf.complete(ResponseEntity.ok(errorResponse));
	            return;	
			}
			cf.complete(ResponseEntity
			          .ok(new OkResponseDO<>(orders)));
	    } catch (Exception e) {
	      logger.error("Exception while GET ALL orders request ",
	          StringUtils.printStackTrace(e));
	      errorResponse =
	          new ErrorResponse(ErrorConstants.UNKNOWN_ERROR_CODE, ErrorMessages.UNKNOWN_ERROR_MESSAGE);
	      cf.complete(ResponseEntity.ok(errorResponse));
	    }		
	}
	

}
