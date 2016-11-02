
package com.fly.pay.common.secure;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Arrays;

import org.bouncycastle.util.encoders.UrlBase64;

/**
 * 签名信息生成类
 * @author jhw
 *
 */
public class SignatureCoder {
    private static String UTF8 = "UTF-8";
    private static String SHA1_WITH_RSA = "SHA1WithRSA";
    
    /**
     * 生成hmacsh1摘要信息
     * @param data 加密数据
     * @param key 加密key
     * @return base64的hmacsha1加密数据
     */
    public static String genHmacSha1Signature(String data,String key) {
    	byte[] dataBytes;
		try {
			dataBytes = data.getBytes(UTF8);
			byte[] keyBytes = key.getBytes(UTF8);
			byte[] dataEncry = Coder.encryptHmacSha1(dataBytes, keyBytes);
	    	String dataBase64 = Coder.encryptBASE64(dataEncry);
	    	return dataBase64;
		} catch (UnsupportedEncodingException e) {
			throw new SecurityException("hmacsha1 signature - unspported encoding",e);
		} catch (InvalidKeyException e) {
			throw new SecurityException("hmacsha1 signature - key invalid",e);
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException("hmacsha1 signature - no such algorithm",e);
		}    	
    }
    /**
     * 生成sh1摘要信息
     * @param data
     * @return
     */
    public static String genSHA1Signature(String data) {
        try {
            byte[] dataBytes = data.getBytes(UTF8);
            byte[] dataEncry = Coder.encryptSHA(dataBytes);

            String dataBase64 = Coder.encryptBASE64(dataEncry);
            return dataBase64;
        } catch (UnsupportedEncodingException e) {
            throw new SecurityException("sha1 signature - unspported encoding");
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException("sha1 signature - no such algorithm");
        }
    }
    
    /**
     * 私钥签名
     * @param content
     * @param privateKey
     * @return
     */
	public static String signByPrivateKey(PrivateKey privateKey, String content) {
		Signature signature;
		try {
			signature = Signature.getInstance(SHA1_WITH_RSA);
			signature.initSign(privateKey);
			signature.update(content.getBytes(UTF8));
			byte[] signed = signature.sign();
//			String dataBase64 = Coder.encryptUrlBase64(signed);
			String dataBase64 = Coder.encryptBASE64(signed);
			return dataBase64;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据公钥验证签名
	 * @param publicKey
	 * @param content
	 * @param singContent
	 * @return
	 */
	public static boolean verifySignByPublicKey(PublicKey publicKey, String content, String signContent) {
		try {
//			byte[] singbyte = Coder.decryptUrlBASE64(signContent.getBytes("UTF-8"));
			byte[] singbyte = Coder.decryptBASE64(signContent);
			Signature signature = Signature.getInstance(SHA1_WITH_RSA);
			signature.initVerify(publicKey);
			signature.update(content.getBytes(UTF8));
			return signature.verify(singbyte);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private static void testSign() throws Exception{
		String key = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALMGBEMB+3geEpurc3blqAXUODEcFYzPcK/NvE837SGabG/q16+MgR4RprI54n2Gp9MiECPWjqGDf4xiWl32nPpWHZDoTUmr/v03fjUpqcd3UGcFq3r/WozZrJHHTLp4egr3RHUa4OIJPX9J1QAaPMIQS2Kdkeiq22F/V5No62y/AgMBAAECgYEArWj0k9vFtLKcn8HSFKecvBi3XYqwrTb7FrAY6jtoLZah1z8KMffN0kySxi0f4SugQPnL3IsmmfkAggF7O21427vdCOA0zX1D0/Cvn0G5dTo91s4Yb4fEtGaCJtDHKRyPy+vtlHnc51KUgaLZIDotrtjc5LsQjLeoAy9ontAie5ECQQDstYcsS55C4e4BDAZo+H3E7ngRyGtPZh1kmI2CKnnb9cqW0P7BO0JC0/5oR2C43j1hGFQiJv4lID4MAez49X9jAkEAwZz93YkBlGsy9Lc83pyg/RAO/PzW5AwpqeEcgrI30kSdTlaip65YkZwD8ccI8xfQXeLnzRsN+f0yobgBXLxh9QJBANgdSIPFqoHP+tfT3cpbDaEORGuf+3GrfqyIp7IvC8EqoMOfnuhXLUEMgls8x+8hYRLT/oSL6z+EN4IJBA2tPpcCQQCXVnqNXtlQRRbYeB5pN28TQCknEQ97xBIRCrf5ZQsDHcUEBe4wObRbZOMkatqRAa1KpeFgzmP3YzXfeO//p/S9AkAc50wCTUvMgwfYL6sKMJ/P/cu8ekK12A5Z37yXne3mKq0qYRmtcn/6uCRbAmXxYWqsN9/54Eg8kWKdZ+HSjkRU";
		PrivateKey privateKey = CertificateCoder.getPrivateKey(key);
		String str = "/api/account/verifyServiceToken.do{\"user\":{\"nickName\":\"yd\",\"uid\":1},\"errcode\":200}";
		String s1 = signByPrivateKey(privateKey,str);

//		String s2 = sign(str,key);
		System.out.println(s1);
	}
	private static void testVerifySign() throws Exception{
		String certFileName = "my.cer";
    	InputStream in = SignatureCoder.class.getResourceAsStream(certFileName);
        byte[] buffer = new byte[64 * 1024];
        int size = in.read(buffer, 0, buffer.length);
        PublicKey pubKey = CertificateCoder.getPublicKey(Arrays.copyOf(buffer, size));
        String content = "/api/bill/queryBanlance.domsgId=07ae54ab9873494aa1884502a9e7da46&sender=100&xiaomiId=209999";
        String singContent = "aKPoVaUUGKsYCw3L2E7niMZweARU7NgS4x60ej2q6kzOwTmQcqLS4duBLKH8oY3NuSW0o4J1pXWOjzoFVkKILvdA590ZUuqWdNVATacK10Dd7Gz_k991VVjto47VxVIovIHNoJC69l5TnaTp--_F0KZHcvEpa3EqS83bjqsRryI.";
        System.out.println(verifySignByPublicKey(pubKey,content,singContent));
	}
	
	 public static String miuiSHA1(String plain) {
         try {
             if (null == plain) {
                 return "";
             }
             MessageDigest md = MessageDigest.getInstance("SHA1");
             byte[] tmp = md.digest(plain.getBytes());
             
             String s = new String(UrlBase64.encode(tmp));
//             System.out.println("plain : " + plain + " sha1 : " + s.substring(0,16));
             return s.substring(0,16);

         } catch (Exception e) {
           e.printStackTrace();  
         }
         return "";
     }
	public static void main(String[] args) throws Exception{
//		testSign();
		testVerifySign();
	}
}
