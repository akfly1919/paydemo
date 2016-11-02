package com.fly.pay.common.secure;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUtils {
	public static final String KEY_SHA256 = "SHA-256";

	/**
	 * 除去数组中的空值和签名参数
	 * 
	 * @param map
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	public static Map<String, String> paraFilter(Map<String, ?> map) {

		Map<String, String> result = new HashMap<String, String>();

		if (map == null || map.size() <= 0) {
			return result;
		}

		for (String key : map.keySet()) {
			Object obj = map.get(key);
			if (null == obj){
				continue;
			}
			String value = obj + "";
			if (value.equals("") || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type")) {
				continue;
			}
			result.put(key, value);
		}

		return result;
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			// 拼接时，不包括最后一个&字符
			if (i == keys.size() - 1) {
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}

	/**
	 * 字符串进行签名
	 * @param content
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String signContentSha256(String content) throws Exception {

		MessageDigest md = null;
		String strDes = null;

		byte[] bt = content.getBytes();
		try {
			String encName = KEY_SHA256;
			md = MessageDigest.getInstance(encName);
			md.update(bt);
			strDes = Base64Utils.encode(md.digest());
		} catch (NoSuchAlgorithmException e) {
			throw e;
		}

		return strDes;
	}

	/**
	 * 验证签名
	 * @param content
	 * @param sign
	 * 
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyContent(String content, String sign)
			throws Exception {

		String strDes = signContentSha256(content);

		if (strDes.equals(sign)) {
			return true;
		}

		return false;
	}

}
