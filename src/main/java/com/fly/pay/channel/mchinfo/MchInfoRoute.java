package com.fly.pay.channel.mchinfo;

import java.util.HashMap;
import java.util.Map;

public class MchInfoRoute {
	
	static Map<String,MchInfo> mchPayTypeMap=new HashMap<String,MchInfo>();
	
	static Map<String,MchInfo> mchIdMap=new HashMap<String,MchInfo>();
	
	public static MchInfo route(String payType){
		return mchPayTypeMap.get(payType);
	}

	public static  MchInfo route(String payType,ISelectMchInfo i){
		String id=i.selectMchInfo();
		return null;
	}
	
	static{
		MchInfo m=new MchInfo("1","wx845c916089295728","1323443801","WXAPP","723ba36db0e4eb28aa6baa654a485be9");
		mchIdMap.put(m.getId(),m);
		mchPayTypeMap.put(m.getPayType(), m); 
	}
}
