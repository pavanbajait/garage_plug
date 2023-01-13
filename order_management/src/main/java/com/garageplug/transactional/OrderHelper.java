package com.garageplug.transactional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garageplug.entities.Customer;
import com.garageplug.entities.Orders;
import com.garageplug.repository.CustomerDAO;
import com.garageplug.repository.OrderDAO;

@Service
public class OrderHelper {
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private OrderDAO orderDAO;
	
	@Transactional
	public Orders createOrderTransaction(Customer customer, Orders order) {
		customerDAO.save(customer);
		orderDAO.save(order);
		return order;
	}

}
