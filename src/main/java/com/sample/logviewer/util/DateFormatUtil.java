package com.sample.logviewer.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {

  private static DateFormat df =  new SimpleDateFormat("MM-dd-yyyy");

  public static Date unmarshal(String date) throws Exception {
    return df.parse(date);
  }

  public static String marshal(Date date) throws Exception {
    return df.format(date);
	}
}
