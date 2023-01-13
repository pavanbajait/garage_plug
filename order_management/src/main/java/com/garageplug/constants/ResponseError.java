package com.garageplug.constants;

public enum ResponseError {
	INVALID_EMAIL_ID("Email Id not found", 1402),INVALID_PASSWORD("Password not matches",1405), CUSTOMER_NOT_LOGGED_IN("Customer not logged in, Please logged in first",1410), CUSTOMER_AREADY_LOGGED_IN("Customer Already logged in with this credentials", 1415), CUSTOMER_NOT_FOUND("Customer not found, Please add customers", 1420), CUSTOMER_NOT_PRESENT("Customer not present with entered customer ID", 1425), ORDERS_NOT_FOUND("Orders are not found, Please placed order", 1430);                                                                                                                               

  private final String message;
  private final Integer code;

  ResponseError(String message, Integer code) {

    this.message = message;
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public Integer getCode() {
    return code;
  }

}
