package com.fly.pay.common.secure;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;



public abstract class CertificateCoder extends Coder {


    /**
     * Java密钥库(Java Key Store，JKS)KEY_STORE
     */
    public static final String KEY_STORE = "JKS";

    public static final String X509 = "X.509";

    /**
     * 由KeyStore获得私钥
     * 
     * @param keyStorePath
     * @param alias
     * @param password
     * @return
     * @throws java.io.IOException
     * @throws java.security.KeyStoreException
     * @throws java.security.cert.CertificateException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.UnrecoverableKeyException
     * @throws Exception
     */
//    public static PrivateKey getPrivateKey(XiaomiKeyStore authData) throws NoSuchAlgorithmException,
//        CertificateException, KeyStoreException, IOException, UnrecoverableKeyException {
//        KeyStore ks = getKeyStore(authData);
//        PrivateKey key = (PrivateKey) ks.getKey(authData.alias, authData.password.toCharArray());
//        return key;
//    }

    /**
     * 由Certificate获得公钥
     * 
     * @param certificatePath
     * @return
     * @throws java.security.cert.CertificateException
     * @throws Exception
     */
    public static PublicKey getPublicKey(byte[] certData) throws CertificateException  {
        Certificate certificate = getCertificate(certData);
        PublicKey key = certificate.getPublicKey();
        return key;
    }
    
    /**
     * 获取私钥文件
     * @param key
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {

		byte[] keyBytes;
		
		keyBytes = Coder.decryptBASE64(key);
		
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		
		return privateKey;

	}
    
    /**
     * 获取公钥
     * @param keyString
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String keyString) throws Exception{
    	KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] encodedKey = Coder.decryptBASE64(keyString);
        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
        return pubKey;
    }

    /**
     * 获得Certificate
     * 
     * @param certificatePath
     * @return
     * @throws java.security.cert.CertificateException
     * @throws java.io.IOException
     * @throws Exception
     */
    private static Certificate getCertificate(byte[] certData) throws CertificateException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance(X509);
        InputStream in = new ByteArrayInputStream(certData);
        Certificate certificate = certificateFactory.generateCertificate(in);
        return certificate;
    }

    /**
     * 获得KeyStore
     * 
     * @param keyStorePath
     * @param password
     * @return
     * @throws java.io.IOException
     * @throws java.security.cert.CertificateException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.KeyStoreException
     * @throws Exception
     */
//    private static KeyStore getKeyStore(XiaomiKeyStore authData) throws NoSuchAlgorithmException, CertificateException, KeyStoreException,
//        IOException {
//        ByteArrayInputStream is = new ByteArrayInputStream(authData.keyStore);
//        KeyStore ks = KeyStore.getInstance(KEY_STORE);
//        ks.load(is, authData.password.toCharArray());
//        return ks;
//    }

    /**
     * 私钥加密
     * 
     * @param data
     * @param keyStorePath
     * @param alias
     * @param password
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, PrivateKey privateKey) throws Exception {

        // 对数据加密
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(data);

    }

    /**
     * 私钥解密
     * 
     * @param data
     * @param keyStorePath
     * @param alias
     * @param password
     * @return
     * @throws java.io.IOException
     * @throws java.security.KeyStoreException
     * @throws java.security.cert.CertificateException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.UnrecoverableKeyException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.InvalidKeyException
     * @throws javax.crypto.BadPaddingException
     * @throws javax.crypto.IllegalBlockSizeException
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, PrivateKey privateKey)
        throws UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException,
        NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // 对数据加密
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);

    }

    /**
     * 公钥加密
     * 
     * @param data
     * @param certificatePath
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, PublicKey publicKey) throws Exception {

        // 对数据加密
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);

    }

    /**
     * 公钥解密
     * 
     * @param data
     * @param certificatePath
     * @return
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.InvalidKeyException
     * @throws javax.crypto.BadPaddingException
     * @throws javax.crypto.IllegalBlockSizeException
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, PublicKey publicKey) throws NoSuchAlgorithmException, NoSuchPaddingException,
        InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // 对数据加密
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);

    }

    /**
     * 验证Certificate
     * 
     * @param certificatePath
     * @return
     */
//    public static boolean verifyCertificate(XiaomiKeyStore authData) {
//        boolean status = false;
//        try {
//            // 取得证书
//            Certificate certificate = getCertificate(authData.keyStore);
//            // 验证证书是否过期或无效
//            status = verifyCertificate(new Date(), certificate);
//        } catch (Exception e) {
//            LOGGER.info("cert is invalid", e);
//        }
//        return status;
//    }


    /**
     * 验证证书是否过期或无效
     * 
     * @param date
     * @param certificate
     * @return
     */
    public static boolean verifyCertificate(Date date, Certificate certificate) {
        boolean status = true;
        X509Certificate x509Certificate = (X509Certificate) certificate;
        try {
            x509Certificate.checkValidity(date);
        } catch (CertificateExpiredException e) {
        } catch (CertificateNotYetValidException e) {
        }
        return status;
    }

    /**
     * 签名
     * 
     * @param keyStorePath
     * @param alias
     * @param password
     * @return
     * @throws SignatureException
     * @throws java.io.IOException
     * @throws java.security.KeyStoreException
     * @throws java.security.cert.CertificateException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.UnrecoverableKeyException
     * @throws java.security.InvalidKeyException
     * @throws Exception
     */
//    public static String sign(byte[] sign, XiaomiKeyStore authData) throws SignatureException, NoSuchAlgorithmException,
//        CertificateException,
//        KeyStoreException, IOException, UnrecoverableKeyException, InvalidKeyException {
//        // 获得证书
//        X509Certificate x509Certificate = (X509Certificate) getCertificate(authData.keyStore);
//        // 获取私钥
//        KeyStore ks = getKeyStore(authData);
//        // 取得私钥
//        PrivateKey privateKey = (PrivateKey) ks.getKey(authData.alias, authData.password.toCharArray());
//
//        // 构建签名
//        Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
//        signature.initSign(privateKey);
//        signature.update(sign);
//        return encryptBASE64(signature.sign());
//    }

    /**
     * 验证签名
     * 
     * @param data
     * @param sign
     * @param certificatePath
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String sign, X509Certificate x509Certificate) throws Exception {
        // 获得公钥
        PublicKey publicKey = x509Certificate.getPublicKey();
        // 构建签名
        Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
        signature.initVerify(publicKey);
        signature.update(data);

        return signature.verify(decryptBASE64(sign));
    }
}
