package com.garageplug.dos;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerLoginDO {
	
	private String email;

	@Size(min = 3, max = 10, message = "Password must be min size of 3 characters and max of 10 characters")
	private String password;
}