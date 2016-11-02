package com.fly.pay.common.secure;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.util.encoders.UrlBase64;
public class Coder {
    public static final String KEY_SHA = "SHA";
    public static final String KEY_MD5 = "MD5";

    /**
     * MAC算法可选以下多种算法
     * 
     * <pre>
     * HmacMD5  
     * HmacSHA1  
     * HmacSHA256  
     * HmacSHA384  
     * HmacSHA512
     * </pre>
     */
    public static final String KEY_MAC = "HmacMD5";
    
    public static final String KEY_MAC_SHA1 = "HmacSHA1";

    /**
     * BASE64解密
     * 
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) {
        try {
			return Base64.decodeBase64(key.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return null;
    }

    /**
     * BASE64加密
     * 
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) {
        try {
            return new String(Base64.encodeBase64(key), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    
    public static String encryptUrlBase64(byte[] data) {
    	try {
			return new String( UrlBase64.encode(data),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    public static byte[] decryptUrlBASE64(byte[] data) {
    	return UrlBase64.decode(data);
    }
    /**
     * MD5加密
     * 
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws Exception
     */
    public static byte[] encryptMD5(byte[] data) throws NoSuchAlgorithmException {

        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data);

        return md5.digest();

    }

    /**
     * SHA加密
     * 
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws Exception
     */
    public static byte[] encryptSHA(byte[] data) throws NoSuchAlgorithmException {

        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data);

        return sha.digest();

    }

    /**
     * 初始化HMAC密钥
     * 
     * @return
     * @throws Exception
     */
    public static String initMacKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);

        SecretKey secretKey = keyGenerator.generateKey();
        return encryptBASE64(secretKey.getEncoded());
    }

    /**
     * HMAC加密
     * 
     * @param data
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws Exception
     */
    public static byte[] encryptHMAC(byte[] data, String key) throws NoSuchAlgorithmException, InvalidKeyException {

        SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);

        return mac.doFinal(data);

    }
    
    /**
     * hamc sha1加密
     * @param data
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static byte[] encryptHmacSha1(byte[] data,byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
    	Mac mac = Mac.getInstance(KEY_MAC_SHA1);
		SecretKeySpec secret = new SecretKeySpec(key,KEY_MAC_SHA1);
		mac.init(secret);
		return mac.doFinal(data);
    }
}
