package com.fly.pay.channel.service;

import java.util.Map;

public abstract class AbstractChannel implements IChannel{
	
	public abstract Map<String, String> buildReqMap(Map<String, String> map, String payType);

	@Override
	public Map<String, String> pay(Map<String, String> map, String payType) {
		Map<String, String> reqMap=buildReqMap(map, payType);
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
	
	

}
