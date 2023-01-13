package com.garageplug.converter;

import org.springframework.stereotype.Service;

import com.garageplug.dos.OrderDO;
import com.garageplug.entities.Orders;

@Service
public class Converter {
	public Orders convertOrderDTOtoOrder(OrderDO orderDO) {
		Orders order = new Orders();
		order.setOrderDescription(orderDO.getOrderDescription());
		order.setTotalCost(orderDO.getTotalCost());
		return order;
	}
}
