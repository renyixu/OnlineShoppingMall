package com.qishiyi.utils;

import java.text.DecimalFormat;

public class DoubleChangeUtils {
	
	public static double doubleChange(double number){
		DecimalFormat format=new DecimalFormat("#0.00");
		String str=format.format(number);
		return Double.parseDouble(str);
	}

}
