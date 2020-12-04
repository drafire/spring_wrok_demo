package com.teligen.demo.utils;


import cn.hutool.core.codec.Base64;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class HexstringByteConvert {
	private static final String HEX_NUMS_STR="0123456789ABCDEF";
	/**   
     * 十六进制字符串转换为字节数组
     * @param hex   
     * @return   
     */
	public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);   
        byte[] result = new byte[len];   
        char[] hexChars = hex.toCharArray();   
        for (int i = 0; i < len; i++) {   
            int pos = i * 2;   
            result[i] = (byte) (HEX_NUMS_STR.indexOf(hexChars[pos]) << 4    
                            | HEX_NUMS_STR.indexOf(hexChars[pos + 1]));   
        }   
        return result;   
    }

	/**  
     * 转换为十六进制字符串
     * @param b  
     * @return
     */  
	public static String byteToHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();   
        for (int i = 0; i < b.length; i++) {   
            String hex = Integer.toHexString(b[i] & 0xFF);   
            if (hex.length() == 1) {   
                hex = '0' + hex;   
            }   
            hexString.append(hex.toUpperCase());   
        }
        return hexString.toString();   
    }
	
	/**
	 * Base64编码
	 * @param str
	 * @return
	 */
	public static String Base64Encode(String str){
		return Base64.encode(str.getBytes());
	}
	
	/**
	 * Base64解码
	 * @param str
	 * @return
	 */
	public static String Base64Decode(String str){
		try {
			return new String(Base64.decode(str),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}
	
	public static String random(int len){
		Random random = new Random();
		String s1 = "";
		String s2 = "";
		for(int i = 0; i < len; i++){
			s1 = s1 + "0";
			s2 = s2 + "9";
		}
		String s = s1 + String.valueOf(random.nextInt(Integer.valueOf(s2)));
		s = s.substring(s.length()-len);
		return s;
	}
	
	public static String getEncoding(String str) {
	      String encode = "GB2312";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s = encode;      
	              return s;      
	           }      
	       } catch (Exception exception) {      
	       }      
	       encode = "ISO-8859-1";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s1 = encode;      
	              return s1;      
	           }      
	       } catch (Exception exception1) {      
	       }      
	       encode = "UTF-8";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s2 = encode;      
	              return s2;      
	           }      
	       } catch (Exception exception2) {      
	       }      
	       encode = "GBK";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s3 = encode;      
	              return s3;      
	           }      
	       } catch (Exception exception3) {      
	       }      
	      return "";      
	   }      
}
