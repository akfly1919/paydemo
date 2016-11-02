package com.fly.pay.channel.mchinfo;

public class MchInfoRoute {
	
	public MchInfo route(String payType){
		return null;
	}

	public MchInfo route(String payType,ISelectMchInfo i){
		String id=i.selectMchInfo();
		return null;
	}
}
