package com.common.ecmanager.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.common.util.RandomUtil;


public class OrderNoGenerateUtil {
	public static String getOrderNo(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date date = new Date();
		String newDate =  sdf.format(date);
		String orderNo = newDate + RandomUtil.getRandom(5);
		return orderNo;
	}
}
