package com.heeexy.example.util;

/**
 * @author: hxy
 * @date: 2017/10/24 10:16
 */
public class StringTools {

	public static boolean isNullOrEmpty(String str) {
		return null == str || "".equals(str) || "null".equals(str);
	}

	public static boolean isNullOrEmpty(Object obj) {
		return null == obj || "".equals(obj);
	}
	
	public static String array2String(Object[] list) {
		StringBuilder builder = new StringBuilder("[");
		for (Object object : list) {
			builder.append(list.toString()).append(", ");
		}
		builder.replace(builder.length()-2, builder.length(), "]");
		return builder.toString();
	}
}
