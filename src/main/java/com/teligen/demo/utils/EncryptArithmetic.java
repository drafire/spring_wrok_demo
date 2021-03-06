package com.teligen.demo.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;


/**
 * 加密算法
 * @author Administrator
 *
 */
public class EncryptArithmetic {
	private static final String LOG_TAG = "JMTX.EncryptArithmetic"; //日志标识
	
	private static final String md5Key="MD5";
	private static final String defaultEncoding="GB2312";
	private static final String Triple_key="guangzhou_huizhi_jiamitx";
	private static final String Triple_Algorithm = "DESede";
	
	
    /**
     * 异或加密算法：明文加密
     * @param sourceString
     * @param key
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String XOREncrypt(String sourceString, String key) throws UnsupportedEncodingException
    {
        byte[] keyByteArray = key.getBytes(defaultEncoding);

        byte[] strByteArray = sourceString.getBytes(defaultEncoding);

        for (int i = 0; i < strByteArray.length; i++)
        {
            for (int j = 0; j < keyByteArray.length; j++)
            {
                strByteArray[i] = (byte)(strByteArray[i] ^ keyByteArray[j]);
            }
        }

        String result = new String(strByteArray);

        return result;
    }
    
    /**
     * 异或加密算法：密文解密
     * @param ciphertext
     * @param key
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String XORDencrypt(String ciphertext, String key) throws UnsupportedEncodingException
    {
    	String result = XOREncrypt(ciphertext, key);
        byte[] b = result.getBytes(defaultEncoding);

        result = new String(b);
        
        return result;
    }
    
   /**
    *  异或加密算法：明文加密
    * @param source
    * @param wantToEncryptLength
    * @param key
    * @return
    */
    public static byte[] XOREncrypt(byte[] source,int wantToEncryptLength,byte[] key){
    	for (int i = 0; i < wantToEncryptLength; i++)
        {
            for (int j = 0; j < key.length; j++)
            {
            	source[i] = (byte)(source[i] ^ key[j]);
            }
        }
    	return source;
    }
    
   /**
    * 异或加密算法：密文解密
    * @param source
    * @param wantToEncryptLength
    * @param key
    * @return
    */
    public static byte[] XORDencrypt(byte[] source,int wantToEncryptLength,byte[] key){
    	return XOREncrypt(source,wantToEncryptLength,key);
    }
    
    
	/**
	 * MD5加密
	 * @param input
	 * @return
	 */
	public static byte[] md5Encrypt(byte[] input){
		try {
			MessageDigest md5=MessageDigest.getInstance(md5Key);
			return md5.digest(input);
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public static String md5EncryptSerialCode(byte[] input){
		try{
			MessageDigest md5 =MessageDigest.getInstance(md5Key);
			byte[] temp =md5.digest(input);
			String s =toHexString(temp);
			s= s.replace("-", "");
			s = s.substring(8, 24);
			return s;
		}catch(Exception e){
			return null;
		}
		
	}
	
	/**
	 * MD5加密
	 * @param input
	 * @return
	 */
	public static String md5EncryptAll(byte[] input){
		byte[] encryData=md5Encrypt(input);
//		String rs=toHexString(encryData);
//		return rs;
		String s = new String(encryData);
		return s;

	}
	
	 public static String toHexString(byte[] b) {
		 String ret = "";
		 for (int i = 0; i < b.length; i++) {
				String tmp=	Integer.toString(b[i]&0xff,16);
				if (tmp.length()==1) tmp="0"+tmp;
				if (tmp.length()==0) tmp="00";
				ret = ret + tmp;
			}   
	        return ret;   
	    }
	 
	 
	/***
	 * 3DES加密
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public static byte[] tripleDesEncrypt(byte[] input) throws Exception{
		byte[] encryptData = null;
		byte[] key = Triple_key.getBytes("ASCII");
		SecretKey desKey = new SecretKeySpec(key, Triple_Algorithm);
		Cipher c1 = Cipher.getInstance(Triple_Algorithm);
		c1.init(Cipher.ENCRYPT_MODE, desKey);
		encryptData = c1.doFinal(input);
		return encryptData;			
	}
	
	/**
	 * 3DES解密
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public static byte[] tripleDesDencrypt(byte[] input) throws Exception{
		byte[] key=Triple_key.getBytes("ASCII");
		SecretKey desKey = new SecretKeySpec(key, Triple_Algorithm);
		Cipher c1 = Cipher.getInstance(Triple_Algorithm);
		c1.init(Cipher.DECRYPT_MODE, desKey);
		byte[] dencryptData = c1.doFinal(input);
		return dencryptData;
	}
	
	

	/***
	 * 3DES加密
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public static byte[] tripleDesEncrypt(byte[] key,byte[] input) throws Exception{
		byte[] encryptData = null;
//		byte[] key = Triple_key.getBytes("ASCII");
		SecretKey desKey = new SecretKeySpec(key, Triple_Algorithm);
		Cipher c1 = Cipher.getInstance(Triple_Algorithm);
		c1.init(Cipher.ENCRYPT_MODE, desKey);
		encryptData = c1.doFinal(input);
		return encryptData;			
	}
	
	/**
	 * 3DES解密
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public static byte[] tripleDesDencrypt(byte[] key,byte[] input) throws Exception{
//		byte[] key=Triple_key.getBytes("ASCII");
		SecretKey desKey = new SecretKeySpec(key, Triple_Algorithm);
		Cipher c1 = Cipher.getInstance(Triple_Algorithm);
		c1.init(Cipher.DECRYPT_MODE, desKey);
		byte[] dencryptData = c1.doFinal(input);
		return dencryptData;
	}
	
	
	public static final byte[] DES_KEY_PUBLIC = {1, 3, 5, 7, 2, 4, 6, 8};
	/** 
	用DES方法加密输入的字节 
	bytKey需为8字节长，是加密的密码 
	bytKey参数无效
	 * @throws Exception 
	*/ 
	public static String encryptByDES(byte[] input) { 
		byte[] data = encryptByDES(input, DES_KEY_PUBLIC);
		String ret = "";
		for (int i = 0; i < data.length; i++) {
			String tmp=	Integer.toString(data[i]&0xff,16);
			if (tmp.length()==1) tmp="0"+tmp;
			if (tmp.length()==0) tmp="00";
			ret = ret + tmp;
		}
		//logger.info("encrypted:" + ret);
		return ret;
	} 
	
	/** 
	用DES方法加密输入的字节 
	bytKey需为8字节长，是加密的密码 
	bytKey参数无效
	 * @throws Exception 
	 */
	public static byte[] encryptByDES(byte[] bytP, byte[] bytKey) {
		try {
			DESKeySpec desKS = new DESKeySpec(DES_KEY_PUBLIC);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
			SecretKey sk = skf.generateSecret(desKS);
			Cipher cip = Cipher.getInstance("DES");
			cip.init(Cipher.ENCRYPT_MODE, sk);
			return cip.doFinal(bytP);
		} catch (Exception e) {
			return null;
		}
	} 
}
