package com.fly.pay.channel.mchinfo;

import java.util.HashMap;
import java.util.Map;

public class PayTypeMap {
	
	static Map<String,String> map=new HashMap<String,String>();
	
	static{
		map.put("WXJSAPI","JSAPI");
		map.put("wxNATIVE","NATIVE");
		map.put("WXAPP","APP");
		map.put("WXWAP","WAP");
	}
	
	public String cover(String payType){
		return map.get(payType);
	}

}
