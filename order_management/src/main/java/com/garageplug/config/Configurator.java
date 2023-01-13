package com.garageplug.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;


public interface Configurator {
  public String getString(String property);

  public Integer getInt(String property);

  public Double getDouble(String property);

  public Float getFloat(String property);

  public Long getLong(String property);

  public String getString(String property, String defaultValue);

  public Integer getInt(String property, Integer defaultValue);

  public Double getDouble(String property, Double defaultValue);

  public Float getFloat(String property, Float defaultValue);

  public Long getLong(String property, Long defaultValue);
}
