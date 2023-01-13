package com.garageplug.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.garageplug.entities.Orders;

@Repository
public interface OrderDAO extends JpaRepository<Orders, Integer>{

	public List<Orders> findByCustomerId(Integer id);
	
	
}
