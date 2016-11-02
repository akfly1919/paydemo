package com.fly.pay.trade.util;

import java.util.Date;

import org.apache.http.client.utils.DateUtils;


public class IdUtil {
	/**
	 * 生成订单id
	 * @param date
	 * @return
	 */
	public static String generateOrderIdByDate(Date date) {
        StringBuilder sb = new StringBuilder();// yyyyMMddHHmmssXXXXXXmigc
        try {
            sb.append(DateUtils.formatDate(date));
            sb.append((int) (Math.random() * 1000000));
            sb.append("demo");
        } catch (Exception e) {
        }
        return sb.toString();
    }

}
