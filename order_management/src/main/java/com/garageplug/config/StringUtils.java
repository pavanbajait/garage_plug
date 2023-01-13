package com.garageplug.config;

import java.text.MessageFormat;
import java.util.IllegalFormatException;

import org.springframework.stereotype.Service;


public class StringUtils {
  private StringUtils() {}

  public static boolean isEmpty(String string) {
    if (string != null && string.isEmpty()) {
      return true;
    }
    return false;
  }

  public static boolean isNotEmpty(String string) {
    if (string != null && !string.isEmpty()) {
      return true;
    }
    return false;
  }

  public static boolean isNullOrEmpty(String string) {
    if (string == null || string.isEmpty()) {
      return true;
    }
    return false;
  }

  public static boolean isNotNullAndEmpty(String string) {
    if (string != null && !string.isEmpty()) {
      return true;
    }
    return false;
  }

  public static int convertToInt(String string) {
    return Integer.parseInt(string);
  }

  public static float convertToFloat(String string) {
    return Float.parseFloat(string);
  }

  public static long convertToLong(String string) {
    return Long.parseLong(string);
  }

  public static double convertToDouble(String string) {
    return Double.parseDouble(string);
  }

  /**
   * Format String with template and value given.
   * 
   * @param template String template which the value would be furnished with.
   * @param value The value to be furnished with.
   * @return Formatted string with the given value.
   * @throws IllegalFormatException on an illegal template format given.
   */
  public static String formatString(String template, Integer value) throws IllegalFormatException {
    return MessageFormat.format(template, value);
  }
  
  public static String printStackTrace(Exception e) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Exception: " + e.getMessage() + "\n\t");
    for(StackTraceElement stmt: e.getStackTrace()) {
      stringBuilder.append(stmt.toString()).append("\n\t");
    }
    return stringBuilder.toString();
  }
  
  public static boolean convertToBoolean(String string) {
    return Boolean.parseBoolean(string);
  }
}
