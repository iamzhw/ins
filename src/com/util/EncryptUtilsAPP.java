package com.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
  




  
  
/** 
 * 编码工具类 
 * 实现aes加密、解密 
 */  
public class EncryptUtilsAPP {  
	public static final String VIPARA = "0102030405060708";  
	public static final String bm = "GBK";  
    
    public static String encrypt(String dataPassword, String cleartext)  
            throws Exception {  
        IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());  
        SecretKeySpec key = new SecretKeySpec(dataPassword.getBytes(), "AES");  
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);  
        byte[] encryptedData = cipher.doFinal(cleartext.getBytes(bm));  
      
        return Base64.encode(encryptedData);  
    }  
      
    public static String decrypt(String dataPassword, String encrypted)  
            throws Exception {  
        byte[] byteMi = Base64.decode(encrypted);  
        IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());  
        SecretKeySpec key = new SecretKeySpec(dataPassword.getBytes(), "AES");  
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);  
        byte[] decryptedData = cipher.doFinal(byteMi);  
      
        return new String(decryptedData,bm);  
    }  
  
}  
