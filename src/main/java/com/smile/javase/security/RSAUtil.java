package com.smile.javase.security;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;

import javax.crypto.Cipher;

import com.smile.javase.util.StringUtil;

public class RSAUtil {

	private final static String RSAkeyfill = "RSA";//补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
	private final String publicKey = "RSAPublicKey";
	private final String privateKey = "RSAPrivateKey";
	
	/**
	 * 获取RSA公钥私钥
	 *
	 * @author wjie
	 * @data 2016年6月8日
	 * @return
	 * @throws NoSuchAlgorithmException HashMap<String,Object>
	 */
	public HashMap<String,Object> initKey() throws NoSuchAlgorithmException{
		HashMap<String, Object> map = new HashMap<String, Object>();  
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSAkeyfill);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        PublicKey pbk = keyPair.getPublic();
        PrivateKey pvk = keyPair.getPrivate();
        map.put(publicKey, pbk);
        map.put(privateKey, pvk);
        return map;
	}
	
	/**
	 * 根据模和指数生成RSA公钥
	 * 
	 * @author wjie
	 * @data 2016年6月8日
	 * @param modulus 模
	 * @param publicexponent 指数
	 * @return RSAPublicKey
	 */
	public static RSAPublicKey getPublicKey(String modulus, String publicexponent) {  
        try {  
            BigInteger b1 = new BigInteger(modulus);  
            BigInteger b2 = new BigInteger(publicexponent);  
            KeyFactory keyFactory = KeyFactory.getInstance(RSAkeyfill);  
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);  
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }
	
	/**
	 * 使用模和指数生成RSA私钥 
	 *
	 * @author wjie
	 * @data 2016年6月8日
	 * @param modulus  模
	 * @param privateexponent  指数
	 * @return RSAPrivateKey
	 */
	public static RSAPrivateKey getPrivateKey(String modulus, String privateexponent) {  
        try {  
            BigInteger b1 = new BigInteger(modulus);  
            BigInteger b2 = new BigInteger(privateexponent);  
            KeyFactory keyFactory = KeyFactory.getInstance(RSAkeyfill);  
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);  
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
	
	
    /** 
     * 公钥加密 
     *  
     * @param data 
     * @param publicKey 
     * @return 
     * @throws Exception 
     */  
    public static String encryptByPublicKey(String data, RSAPublicKey publicKey)  
            throws Exception {  
        Cipher cipher = Cipher.getInstance(RSAkeyfill);  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
        // 模长  
        int key_len = publicKey.getModulus().bitLength() / 8;  
        // 加密数据长度 <= 模长-11  
        String[] datas = StringUtil.splitString(data, key_len - 11);  
        String mi = "";  
        //如果明文长度大于模长-11则要分组加密  
        for (String s : datas) {  
            mi += StringUtil.bcd2Str(cipher.doFinal(s.getBytes()));  
        }  
        return mi;  
    }  
  
    /** 
     * 私钥解密 
     *  
     * @param data 
     * @param privateKey 
     * @return 
     * @throws Exception 
     */  
    public static String decryptByPrivateKey(String data, RSAPrivateKey privateKey)  
            throws Exception {  
        Cipher cipher = Cipher.getInstance(RSAkeyfill);  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
        //模长  
        int key_len = privateKey.getModulus().bitLength() / 8;  
        byte[] bytes = data.getBytes();  
        byte[] bcd = StringUtil.ASCII_To_BCD(bytes, bytes.length);  
        System.err.println(bcd.length);  
        //如果密文长度大于模长则要分组解密  
        String ming = "";  
        byte[][] arrays = StringUtil.splitArray(bcd, key_len);  
        for(byte[] arr : arrays){  
            ming += new String(cipher.doFinal(arr));  
        }  
        return ming;  
    }  
    
   
}
