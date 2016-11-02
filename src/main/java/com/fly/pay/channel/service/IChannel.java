package com.fly.pay.channel.service;

import java.util.Map;

import org.apache.log4j.Logger;

public interface IChannel {
	/**
	 * 支付
	 * @param map
	 * @return
	 */
	public Map<String,String> pay(Map<String,String> map,String payType) throws Throwable;
	/**
	 * 补单
	 * @param map
	 * @return
	 */
	public Map<String,String> queryQay(Map<String,String> map,String payType) throws Throwable;
	/**
	 * 退款
	 * @param map
	 * @return
	 */
	public Map<String,String> refund(Map<String,String> map,String payType) throws Throwable;
	/**
	 * 查询退款
	 * @param map
	 * @param payType
	 * @return
	 */
	public Map<String,String> queryRefund(Map<String,String> map,String payType) throws Throwable;
	/**
	 * 下载对账文件
	 * @param map
	 * @return
	 */
	public Map<String,String> downBill(Map<String,String> map,String payType) throws Throwable;
}
