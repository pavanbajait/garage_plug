package com.garageplug.constants;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OkResponseDO<T> extends BaseResponseDO {
  public T result;

  public OkResponseDO() {
    super(HttpStatus.OK.value());
  }

  public OkResponseDO(T result) {
    super(HttpStatus.OK.value());
    this.result = result;
  }
}
