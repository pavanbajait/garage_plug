package com.garageplug.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.garageplug.dos.CustomerLoginDO;
import com.garageplug.entities.Customer;


public interface CustomerService {

	public void createCustomer(Customer customer, CompletableFuture<ResponseEntity<?>> cf);

	public void updateCustomer(Customer customer, String key, CompletableFuture<ResponseEntity<?>> cf);

	public void customerlogin(CustomerLoginDO details, CompletableFuture<ResponseEntity<?>> cf);

	public void customerlogout(String key, CompletableFuture<ResponseEntity<?>> cf);

	public void viewAllCustomers(CompletableFuture<ResponseEntity<?>> cf);

}
