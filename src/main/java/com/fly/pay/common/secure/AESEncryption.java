package com.fly.pay.common.secure;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.fly.pay.common.exception.AESDecodeException;
import com.fly.pay.common.exception.AESEncodeException;


public class AESEncryption {
	// 加密
	public static byte[] encrypt(String sSrc, String sKey) throws AESEncodeException {
		try {
			if (sKey == null) {
	            throw new AESEncodeException("Key为空null");
	        }
	        // 判断Key是否为16位
	        if (sKey.length() != 16) {
	            throw new AESEncodeException("Key长度不是16位");
	        }
	        byte[] raw = sKey.getBytes();
	        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
	        IvParameterSpec iv = new IvParameterSpec(Coder.encryptMD5(sKey.getBytes()));
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec,iv);
			return cipher.doFinal(sSrc.getBytes());
		} catch (Exception e) {
			throw new AESEncodeException("AES加密错误",e);
		}
	}

	// 解密
	public static byte[] decrypt(byte[] src, String sKey) throws AESDecodeException {
		try {
			if (sKey == null) {
	            throw new AESEncodeException("Key为空null");
	        }
	        // 判断Key是否为16位
	        if (sKey.length() != 16) {
	            throw new AESEncodeException("Key长度不是16位");
	        }
            byte[] raw = sKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(Coder.encryptMD5(sKey.getBytes()));
            cipher.init(Cipher.DECRYPT_MODE, skeySpec,iv);
            return cipher.doFinal(src);
		} catch (Exception e) {
			throw new AESDecodeException("AES加密错误",e);
		}
	}

	public static byte[] hex2byte(String strhex) {
		if (strhex == null) {
			return null;
		}
		int l = strhex.length();
		if (l % 2 == 1) {
			return null;
		}
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
					16);
		}
		return b;
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	
	/**
	 * AES加密
	 * @param content 加密字符串
	 * @param hexAkey 十六进制表示的key
	 * @return UrlBase64加密的aes加密字符串
	 * @throws AESEncodeException
	 * @throws UnsupportedEncodingException
	 */
	public static String hexEncrypt(String content, String hexAkey) throws AESEncodeException, UnsupportedEncodingException {
		byte[] akey = hex2byte(hexAkey);
		byte[] strByte = encrypt(content, new String(akey,"UTF-8"));
		return Coder.encryptUrlBase64(strByte);
	}
	
	/**
	 * AES解密
	 * @param base64Content UrlBase64的aes加密密文字符串
	 * @param hexAkey 十六进制表示的key
	 * @return 解密后的字符串
	 * @throws AESDecodeException
	 * @throws UnsupportedEncodingException
	 */
	public static String hexDecrypt(String base64Content, String hexAkey) throws AESDecodeException, UnsupportedEncodingException {
		byte[] akey = hex2byte(hexAkey);
		byte[] strByte = decrypt(Coder.decryptUrlBASE64(base64Content.getBytes("UTF-8")),new String(akey,"UTF-8"));
		return new String(strByte);
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, AESDecodeException {
		String s = "SvnxUs8S2ftrvRL4UMRC0Y3X3ffmAWmIY4xwAGdb9QlNl-nLOc7Lqza3D7K3yLsyZWetpAiLOVrrAcmouPjuGNhqOOkpejlknA4ffkFeK8rANc3wsWec1pxyjgqndegDjLcwvm9zmFSdNZuZpq1MyP0CMg2OpyhuWDEZqOJXA3S2he5O0g30grhtgjddcfYNfz7Pg2wq6s7BXDE2Q3Ag7kN3FBKFFXLF3prud2ITsmea2GHuhq06A7ndwK4R8OKlenWAZ205PL-KhoHkklAvHdUqx3oIWdUgBGKK0camyRiakwW_i580PQ2coClKB8bMEBZV6SUOOo54SCj5fQAQKu1rzLFHbbSMcOKwgW-LDVcPCozvOqDzyPkQTZM8whMq";
		String k = "38464B6C45486561724D415964687A61";
		System.out.println(hexDecrypt(s,k));
		
		System.out.println(URLDecoder.decode("%7B%22user%22%3A%7B%22nickName%22%3A%22yd%22%2C%22uid%22%3A1%7D%2C%22errcode%22%3A200%7D"));
	}
}
