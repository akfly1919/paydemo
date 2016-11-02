package com.fly.pay.channel.mchinfo;

public class MchInfo {
	
	private String id;
	
	private String appid;
	
	private String mchId;
	
	private String payType;
	
	private String key;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public MchInfo(String id, String appid, String mchId, String payType, String key) {
		super();
		this.id = id;
		this.appid = appid;
		this.mchId = mchId;
		this.payType = payType;
		this.key = key;
	}

	public MchInfo() {
	}
	
}
