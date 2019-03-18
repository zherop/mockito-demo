package com.zp.util;

import java.math.BigDecimal;

/**
 * @author zp
 * @date 2019年3月18日
 */
public class NumberUtil {
	/**
	 * 加法运算
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static BigDecimal sum(Number a, Number b) {
		return toBigDecimal(a).add(toBigDecimal(b));
	}

	/**
	 * 减法运算
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static BigDecimal sub(Number a, Number b) {
		return toBigDecimal(a).subtract(toBigDecimal(b));
	}

	/**
	 * 乘法运算
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static BigDecimal mul(Number a, Number b) {
		return toBigDecimal(a).multiply(toBigDecimal(b));
	}

	/**
	 * 除法运算
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static BigDecimal div(Number a, Number b) {
		return toBigDecimal(a).divide(toBigDecimal(b));
	}

	/**
	 * @param a
	 */
	private static BigDecimal toBigDecimal(Number a) {
		return new BigDecimal(a.toString());
	}
}
