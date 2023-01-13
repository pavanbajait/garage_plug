package com.garageplug.constants;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponseDO {
  public final Integer code;

  public BaseResponseDO() {
    this.code = HttpStatus.OK.value();
  }

  public BaseResponseDO(Integer code) {
    this.code = code;
  }
}
