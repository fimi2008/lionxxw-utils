package com.lionxxw.utils;


import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

public final class FormatUtil {
	/** ==============IS Base=================== */
	/** 判断是否为整数(包括负数) */
	public static boolean isNumber(Object arg) {
		return NumberBo(0, toString(arg));
	}

	/** 判断是否为小数(包括整数,包括负数) */
	public static boolean isDecimal(Object arg) {
		return NumberBo(1, toString(arg));
	}

	/** 判断是否为空 ,为空返回true */
	public static boolean isEmpty(Object arg) {
		return toStringTrim(arg).length() == 0 ? true : false;
	}

	/** ==============TO Base=================== */
	/**
	 * Object 转换成 Int 转换失败 返回默认值 0 <br>
	 * 使用:toInt(值,默认值[选填])
	 */
	public static int toInt(Object... args) {
		int def = 0;
		if (args != null) {
			String str = toStringTrim(args[0]);
			// 判断小数情况。舍弃小数位
			int stri = str.indexOf('.');
			str = stri > 0 ? str.substring(0, stri) : str;
			if (args.length > 1)
				def = Integer.parseInt(args[args.length - 1].toString());
			if (isNumber(str))
				return Integer.parseInt(str);
		}
		return def;
	}

	/**
	 * Object 转换成 Long 转换失败 返回默认值 0 <br>
	 * 使用:toLong(值,默认值[选填])
	 */
	public static long toLong(Object... args) {
		Long def = 0L;
		if (args != null) {
			String str = toStringTrim(args[0]);
			if (args.length > 1)
				def = Long.parseLong(args[args.length - 1].toString());
			if (isNumber(str))
				return Long.parseLong(str);
		}
		return def;
	}
	
	

	/**
	 * Object 转换成 Double 转换失败 返回默认值 0 <br>
	 * 使用:toDouble(值,默认值[选填])
	 */
	public static double toDouble(Object... args) {
		double def = 0;
		if (args != null) {
			String str = toStringTrim(args[0]);
			if (args.length > 1)
				def = Double.parseDouble(args[args.length - 1].toString());
			if (isDecimal(str))
				return Double.parseDouble(str);
		}
		return def;
	}

	/**
	 * Object 转换成 BigDecimal 转换失败 返回默认值 0 <br>
	 * 使用:toDecimal(值,默认值[选填]) 特别注意: new BigDecimal(Double) 会有误差，得先转String
	 */
	public static BigDecimal toDecimal(Object... args) {
		String dou = Double.toString(toDouble(args));
		return new BigDecimal(dou);
	}

	/**
	 * String 转换成 BigDecimal <br>
	 * 
	 */
	public static BigDecimal toDecimal(String args) {
		return new BigDecimal(args);
	}
	/**
	 * Object 转换成 String 为null 返回空字符 <br>
	 * 使用:toString(值,默认值[选填])
	 */
	public static String toString(Object... args) {
		String def = "";
		if (args != null) {
			if (args.length > 1)
				def = toString(args[args.length - 1]);
			Object obj = args[0];
			if (obj == null)
				return def;
			return obj.toString();
		} else {
			return def;
		}
	}

	/**
	 * Object 转换成 String[去除所以空格]; 为null 返回空字符 <br>
	 * 使用:toStringTrim(值,默认值[选填])
	 */
	public static String toStringTrim(Object... args) {
		String str = toString(args);
		return str.replaceAll("\\s*", "");
	}

	/** ==============Other Base=================== */
	public static String getXParam(String cInfo) {
		String str = "";
		int length = cInfo.length();
		if (length >= 16) {
			int minL = 4;
			str = cInfo.substring(0, minL) + getX(length - 2 * minL) + cInfo.substring(length - minL);
		} else {
			str = getX(length / 2) + cInfo.substring(length / 2);
		}
		return str;
	}

	public static String getX(int len) {
		String x = "*";
		while (x.length() < len)
			x = x + "*";
		return x;
	}

	public static <T extends Serializable> String beanToString(T obj) {
		try {
			// 序列化
			String json = JSON.toJSONString(obj);
			return SecurityUtil.Base64Encode(json);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static <T extends Serializable> T stringToBean(Class<T> cla, String base64JsonStr) {
		try {
			T t = (T) JSON.parseObject(SecurityUtil.Base64Decode(base64JsonStr), cla);
			return t;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/** 数字左边补0 ,obj:要补0的数， length:补0后的长度 */
	public static String leftPad(Object obj, int length) {
		return String.format("%0" + length + "d", toInt(obj));
	}

	/** 小数 转 百分数 */
	public static String toPercent(Double str) {
		StringBuffer sb = new StringBuffer(Double.toString(str * 100.0000d));
		return sb.append("%").toString();
	}

	/** 百分数 转 小数 */
	public static Double toPercent2(String str) {
		if (str.charAt(str.length() - 1) == '%')
			return Double.parseDouble(str.substring(0, str.length() - 1)) / 100.0000d;
		return 0d;
	}

	/** String 转 Money */
	public static String toMoney(Object str, String MoneyType) {
		DecimalFormat df = new DecimalFormat(MoneyType);
		if (isDecimal(str))
			return df.format(toDecimal(str)).toString();
		return df.format(toDecimal("0.00")).toString();
	}

	/** 获取字符串str 左边len位数 */
	public static String getLeft(Object obj, int len) {
		String str = toString(obj);
		if (len <= 0)
			return "";
		if (str.length() <= len)
			return str;
		else
			return str.substring(0, len);
	}

	/** 获取字符串str 右边len位数 */
	public static String getRight(Object obj, int len) {
		String str = toString(obj);
		if (len <= 0)
			return "";
		if (str.length() <= len)
			return str;
		else
			return str.substring(str.length() - len, str.length());
	}

	/**
	 * 首字母变小写
	 */
	public static String firstCharToLowerCase(String str) {
		Character firstChar = str.charAt(0);
		String tail = str.substring(1);
		str = Character.toLowerCase(firstChar) + tail;
		return str;
	}

	/**
	 * 首字母变大写
	 */
	public static String firstCharToUpperCase(String str) {
		Character firstChar = str.charAt(0);
		String tail = str.substring(1);
		str = Character.toUpperCase(firstChar) + tail;
		return str;
	}

	/**
	 * 将byte[] 转换成字符串
	 */
	public static String byte2Hex(byte[] srcBytes) {
		StringBuilder hexRetSB = new StringBuilder();
		for (byte b : srcBytes) {
			String hexString = Integer.toHexString(0x00ff & b);
			hexRetSB.append(hexString.length() == 1 ? 0 : "").append(hexString);
		}
		return hexRetSB.toString();
	}

	/**
	 * 将16进制字符串转为转换成字符串
	 */
	public static byte[] hex2Bytes(String source) {
		byte[] sourceBytes = new byte[source.length() / 2];
		for (int i = 0; i < sourceBytes.length; i++) {
			sourceBytes[i] = (byte) Integer.parseInt(source.substring(i * 2, i * 2 + 2), 16);
		}
		return sourceBytes;
	}

	/**
	 * List集合去除重复值 只能用于基本数据类型，。 对象类集合，自己写
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List delMoreList(List list) {
		Set set = new HashSet();
		List newList = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element))
				newList.add(element);
		}
		return newList;
	}

	/** ============== END =================== */
	public final static class MoneyType {
		/** * 保留2位有效数字，整数位每3位逗号隔开 （默认） */
		public static final String DECIMAL = "#,##0.00";
		/** * 保留2位有效数字 */
		public static final String DECIMAL_2 = "0.00";
		/** * 保留4位有效数字 */
		public static final String DECIMAL_4 = "0.0000";
	}

	private static boolean NumberBo(int type, Object obj) {
		if (isEmpty(obj))
			return false;
		int points = 0;
		int chr = 0;
		String str = toString(obj);
		for (int i = str.length(); --i >= 0;) {
			chr = str.charAt(i);
			if (chr < 48 || chr > 57) { // 判断数字
				if (i == 0 && chr == 45) // 判断 - 号
					return true;
				if (i >= 0 && chr == 46 && type == 1) { // 判断 . 号
					++points;
					if (points <= 1)
						continue;
				}
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		
	}
}