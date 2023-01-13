package com.garageplug.dos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDO {
	
	private String orderDescription;
	
	private Double totalCost;
	
    private Integer customerId;
}
