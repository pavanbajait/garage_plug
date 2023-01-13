package com.garageplug.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;

import com.garageplug.dos.OrderDO;


public interface OrderService {

	public void createOrder(OrderDO orderDO, CompletableFuture<ResponseEntity<?>> cf) ;

	public void viewAllOrders(String key, CompletableFuture<ResponseEntity<?>> cf);

	public void viewOrder(int orderId, String key, CompletableFuture<ResponseEntity<?>> cf) ;
	

}
