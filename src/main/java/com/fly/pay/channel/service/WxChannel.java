package com.fly.pay.channel.service;

import java.net.InetAddress;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.fly.pay.channel.mchinfo.MchInfo;
import com.fly.pay.channel.mchinfo.MchInfoRoute;
import com.fly.pay.common.http.HttpUtils;
import com.fly.pay.common.http.Response;
import com.fly.pay.common.secure.MD5Util;
import com.fly.pay.common.secure.SignUtils;
import com.fly.pay.common.util.BeanUtil;
import com.fly.pay.common.util.RandomStringUtils;
public class WxChannel implements IChannel{
	
	static Logger logger =Logger.getLogger(WxChannel.class);
	
	public final String PAY_URL="https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	public final String QUERYPAY_URL="https://api.mch.weixin.qq.com/pay/orderquery";
	
	public final String REFUND_URL="https://api.mch.weixin.qq.com/secapi/pay/refund";
	
	public final String QUERYREFUND_URL="https://api.mch.weixin.qq.com/pay/refundquery";
	
	public final String DOWNBILL_URL="https://api.mch.weixin.qq.com/pay/downloadbill";

	static int RANDOM_LENGTH=16;
	
	@Override
	public Map<String, String> pay(Map<String, String> map, String payType) throws Throwable {
		MchInfo mi=MchInfoRoute.route(payType);
		
		SortedMap<String,String> reqMap=new TreeMap<String,String>();
		reqMap.put("appid", mi.getAppid());
		reqMap.put("attach", "");
		reqMap.put("body", "");
		reqMap.put("mch_id", mi.getMchId());
		reqMap.put("detail", "");
		reqMap.put("nonce_str", RandomStringUtils.generateString(RANDOM_LENGTH));
		reqMap.put("notify_url", "");
		reqMap.put("openid", "");
		reqMap.put("out_trade_no", "");
		reqMap.put("spbill_create_ip", InetAddress.getLocalHost().getHostAddress());
		reqMap.put("total_fee", "");
		reqMap.put("trade_type", "");
		reqMap.put("sign", sign(reqMap, mi.getKey()));
		
		String xmlParams = BeanUtil.map2xml(reqMap);
		
		String xmlParamsEncode = new String(xmlParams.getBytes("utf-8"), "ISO-8859-1");
		
		Response resp = HttpUtils.httpPost(PAY_URL, xmlParamsEncode, 10000);
		
		if(resp.getStatusCode()==200){
			String responseDecode = new String(resp.getResponseString().getBytes("ISO-8859-1"),"utf-8");
			return BeanUtil.xmltoMap(responseDecode);
		}
		
		return null;
	}

	@Override
	public Map<String, String> queryQay(Map<String, String> map, String payType) throws Throwable {
		SortedMap<String,String> reqMap=new TreeMap<String,String>();
		reqMap.put("appid", "");
		reqMap.put("mch_id", "");
		reqMap.put("nonce_str", RandomStringUtils.generateString(RANDOM_LENGTH));
		reqMap.put("out_trade_no", "");
		reqMap.put("transaction_id", "");
		reqMap.put("sign", sign(reqMap, ""));
		
		String xmlParams = BeanUtil.map2xml(reqMap);
		
		String xmlParamsEncode = new String(xmlParams.getBytes("utf-8"), "ISO-8859-1");
		
		Response resp = HttpUtils.httpPost(QUERYPAY_URL, xmlParamsEncode, 10000);
		
		if(resp.getStatusCode()==200){
			String responseDecode = new String(resp.getResponseString().getBytes("ISO-8859-1"),"utf-8");
			return BeanUtil.xmltoMap(responseDecode);
		}
		
		return null;
	}

	@Override
	public Map<String, String> refund(Map<String, String> map, String payType) throws Throwable {
		SortedMap<String,String> reqMap=new TreeMap<String,String>();
		reqMap.put("appid", "");
		reqMap.put("mch_id", "");
		reqMap.put("nonce_str", RandomStringUtils.generateString(RANDOM_LENGTH));
		reqMap.put("op_user_id", "");
		reqMap.put("out_trade_no", "");
		reqMap.put("transaction_id", "");
		reqMap.put("refund_fee", "");
		reqMap.put("total_fee", "");
		reqMap.put("sign", sign(reqMap, ""));
		
		String xmlParams = BeanUtil.map2xml(reqMap);
		
		String xmlParamsEncode = new String(xmlParams.getBytes("utf-8"), "ISO-8859-1");
		
		Response resp = HttpUtils.httpPost(REFUND_URL, xmlParamsEncode, 10000);
		
		if(resp.getStatusCode()==200){
			String responseDecode = new String(resp.getResponseString().getBytes("ISO-8859-1"),"utf-8");
			return BeanUtil.xmltoMap(responseDecode);
		}
		
		return null;
	}

	@Override
	public Map<String, String> queryRefund(Map<String, String> map, String payType) throws Throwable {
		SortedMap<String,String> reqMap=new TreeMap<String,String>();
		reqMap.put("appid", "");
		reqMap.put("mch_id", "");
		reqMap.put("nonce_str", RandomStringUtils.generateString(RANDOM_LENGTH));
		reqMap.put("out_trade_no", "");
		reqMap.put("transaction_id", "");
		reqMap.put("out_refund_no", "");
		reqMap.put("refund_id", "");
		reqMap.put("sign", sign(reqMap, ""));
		
		String xmlParams = BeanUtil.map2xml(reqMap);
		
		String xmlParamsEncode = new String(xmlParams.getBytes("utf-8"), "ISO-8859-1");
		
		Response resp = HttpUtils.httpPost(QUERYREFUND_URL, xmlParamsEncode, 10000);
		
		if(resp.getStatusCode()==200){
			String responseDecode = new String(resp.getResponseString().getBytes("ISO-8859-1"),"utf-8");
			return BeanUtil.xmltoMap(responseDecode);
		}
		
		return null;
	}

	@Override
	public Map<String, String> downBill(Map<String, String> map, String payType) throws Throwable {
		SortedMap<String,String> reqMap=new TreeMap<String,String>();
		reqMap.put("appid", "");
		reqMap.put("mch_id", "");
		reqMap.put("nonce_str", RandomStringUtils.generateString(RANDOM_LENGTH));
		reqMap.put("bill_date", "");
		reqMap.put("bill_type", "");
		reqMap.put("sign", sign(reqMap, ""));
		
		String xmlParams = BeanUtil.map2xml(reqMap);
		
		String xmlParamsEncode = new String(xmlParams.getBytes("utf-8"), "ISO-8859-1");
		
		Response resp = HttpUtils.httpPost(DOWNBILL_URL, xmlParamsEncode, 10000);
		
		if(resp.getStatusCode()==200){
			String responseDecode = new String(resp.getResponseString().getBytes("ISO-8859-1"),"utf-8");
			return BeanUtil.xmltoMap(responseDecode);
		}
		
		return null;
	}

	public String sign(SortedMap<String,String> sortedMap,String key){
		 String toSignString = SignUtils.createLinkString(sortedMap);
		 if(key!=null&&key.length()!=0){
			 toSignString += "&key=" + key;
		 }
         String calSignature = MD5Util.getMd5String(toSignString).toUpperCase();
		return calSignature;
	}
	
}
