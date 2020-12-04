package com.teligen.demo.utils;

import cn.hutool.core.codec.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class EncryptUtil {

	private static final char XOR_ENCODE_KEY = '$';

	private EncryptUtil(){
		
	}
	
	public static byte[] encryptByAES4Android(byte[] plainData,String keyStr){
		byte[] arr = null;
		try {
			byte[] keyArr = keyStr.getBytes();
			SecretKey scrKey = new SecretKeySpec(keyArr, "AES");
			Cipher encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			encryptCipher.init(Cipher.ENCRYPT_MODE, scrKey,
					new IvParameterSpec(keyArr));
			arr = encryptCipher.doFinal(plainData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arr;
	}
	
	public static byte[] decryptByAES4Android(byte[] cipherData,String keyStr){
		byte[] arr = null;
		try {
			byte[] keyArr = keyStr.getBytes();
			SecretKey scrKey = new SecretKeySpec(keyArr, "AES");
			Cipher encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			encryptCipher.init(Cipher.DECRYPT_MODE, scrKey,
					new IvParameterSpec(keyArr));
			arr = encryptCipher.doFinal(cipherData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arr;
	}


	public static byte[] encryptByRc4(byte[] data, byte[] keyArr) {
		byte[] cipherByteArr = null;
		try {
			Cipher cipher = Cipher.getInstance("ARCFOUR");
			SecretKeySpec key = new SecretKeySpec(keyArr, "ARCFOUR");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			cipherByteArr = cipher.update(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cipherByteArr;
	}

	public static byte[] decryptByRc4(byte[] data, byte[] keyArr) {
		byte[] plainByteArr = null;
		try {
			Cipher cipher = Cipher.getInstance("ARCFOUR");
			SecretKeySpec key = new SecretKeySpec(keyArr, "ARCFOUR");
			cipher.init(Cipher.DECRYPT_MODE, key);
			plainByteArr = cipher.update(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return plainByteArr;
	}

	/**
	 * rc4+xor加密
	 * @param data
	 * @param keyArr
	 * @return
	 */
	public static byte[] encryptByRc4Plus(byte[] data, byte[] keyArr) {
		byte[] b = encryptByRc4(data, keyArr);
		for(int i=0,size=b.length; i<size; i++){
			b[i] = (byte) (b[i]^XOR_ENCODE_KEY);
		}
		return b;
	}

	/**
	 * rc4+xor解密
	 * @param data
	 * @param keyArr
	 * @return
	 */
	public static byte[] decryptByRc4Plus(byte[] data, byte[] keyArr) {
		for(int i=0,size=data.length; i<size; i++){
			data[i] = (byte) (data[i]^XOR_ENCODE_KEY);
		}
		byte[] b = decryptByRc4(data, keyArr);
		return b;
	}

	public static String rc4(String str) {
		String resultStr = str;
		try {
			byte[] encryptBytes = str.getBytes("UTF-8");
			byte[] symKey = new byte[64];
			for (int i = 0; i < 64; i++) {
				symKey[i] = (byte) i;
			}
			byte[] decryptBytes = EncryptUtil.encryptByRc4Plus(encryptBytes, symKey);
			resultStr = Base64.encode(decryptBytes);
		} catch (Exception e) {
			resultStr = "";
			e.printStackTrace();
		}
		return resultStr;
	}
	public static String deCodeResult(String str) {
		String result = null;
		try {
			byte[] resByte = Base64.decode(str);

			byte[] symKey = new byte[64];
			for (int i = 0; i < 64; i++) {
				symKey[i] = (byte) i;
			}
			byte[] decryptBytes = decryptByRc4Plus(resByte, symKey);
			result = new String(decryptBytes, "UTF-8");
		} catch (Exception e) {
			return null;
		}

		return result;
	}


	public static void main(String[] args) {
		String key = "OTYyMmQ3MTgwY2JlNGQxMjlmMzRhMmU0ODdlNWQ5NmE5NjIyZDcxODBjYmU0ZDEyOWYzNGEyZTQ4N2U1ZDk2YQ==";
		String data = "nQCY9KbUuE/Ea93d5Y0g0suD8Srxk1YAhVNQtPfvGy/QaxNOpqtEF4rxRRSUa/n1WS8KsYz8UHarEYoapSAvEuq2mHpBVQQleCOIVDWYmcVUXUcm6or/Kj/K4uO45eiSIS+C4EbKLE7w8A==";
		System.out.println(rc4(key, data));
	}


	private static final int BODY_TYPE_FIELD_LEN = 1;
	private static final int BODY_LENTH_FIELD_LEN = 2;
	public static String rc4(String key, String data){
		String resultStr;
		try{
			byte[] encryptBytes = Base64.decode(data);
			byte[] symKey = Base64.decode(key);
			byte[] decryptBytes = EncryptUtil.decryptByRc4(encryptBytes, symKey);

			byte[] value = new byte[decryptBytes.length - BODY_TYPE_FIELD_LEN - BODY_LENTH_FIELD_LEN ];
			System.arraycopy(decryptBytes, BODY_TYPE_FIELD_LEN + BODY_LENTH_FIELD_LEN, value, 0, value.length);
			resultStr = new String(value, "UTF-8");
		}catch(Exception e){
			resultStr = "";
			e.printStackTrace();
		}
		return resultStr;
	}
}
