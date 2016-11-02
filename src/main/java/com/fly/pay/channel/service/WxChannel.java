package com.fly.pay.channel.service;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;

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
		
		SortedMap<String,String> reqMap=new TreeMap<String,String>();
		reqMap.put("appid", "");
		reqMap.put("attach", "");
		reqMap.put("body", "");
		reqMap.put("mch_id", "");
		reqMap.put("detail", "");
		reqMap.put("nonce_str", RandomStringUtils.generateString(RANDOM_LENGTH));
		reqMap.put("notify_url", "");
		reqMap.put("openid", "");
		reqMap.put("out_trade_no", "");
		reqMap.put("spbill_create_ip", InetAddress.getLocalHost().getHostAddress());
		reqMap.put("total_fee", "");
		reqMap.put("trade_type", "");
		reqMap.put("sign", sign(reqMap, ""));
		
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
	public Map<String, String> queryQay(Map<String, String> map, String payType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> refund(Map<String, String> map, String payType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> queryRefund(Map<String, String> map, String payType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> downBill(Map<String, String> map, String payType) {
		// TODO Auto-generated method stub
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
