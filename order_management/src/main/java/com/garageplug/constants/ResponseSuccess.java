package com.garageplug.constants;

public enum ResponseSuccess {
  SUCCESS_CUSTOMER_CREATION("Create Customer Successful"), SUCCESS_CUSTOMER_UPDATION("Update Customer Successful"), SUCCESS_ORDER_CREATION("Create Order Successful"), 
  SUCCESS_CUSTOMER_LOGIN("Customer LogIn Successfully"),SUCCESS_CUSTOMER_LOGOUT("Customer LogOut Successfully"), SUCCESS_GET_ALL_ORDER("GET All Order Successful"), SUCCESS_GET_BY_ORDER_ID("Get Order By Id Successful"),;
                                     
  private final String message;

  ResponseSuccess(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
