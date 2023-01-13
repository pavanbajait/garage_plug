package com.garageplug.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.garageplug.entities.Customer;

@Repository
public interface CustomerDAO extends JpaRepository<Customer, Integer> {

	public Customer findBycustomerName(String customerName);
	
	public Customer findByEmail(String email);
 
}