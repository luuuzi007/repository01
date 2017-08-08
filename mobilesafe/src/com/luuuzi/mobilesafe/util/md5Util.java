package com.luuuzi.mobilesafe.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Log;

/**
 * 密码加密过程演示
 * @author admin
 *
 */
public class md5Util {

	private static String tag="md5Util";
	/**密码加密过程(给指定字符按照md5算法去加密)
	 * @param password 需要加密的密码
	 */
	public static String encoder(String password) {
		StringBuffer stringBuffer = new StringBuffer();

		try {
			//1.指定算法类型(单例模式),异常是当没有该算法时
			//参数写算法名，必须大写
			MessageDigest instance = MessageDigest.getInstance("MD5");
			//2.将需要加密的字符串转换成byte[] byte数组，然后进行随机的哈希过程
			//password.getBytes()，将字符串转换为byte类型的数组byte的数组的长度变为16位
			byte[] digest_byte = instance.digest(password.getBytes());
			//3.将16位的数组转换成32位的字符串,固定写法
			//4.拼接字符串
			for(byte b:digest_byte){
				//固定写法(byte是1字节 表示8位 oxff也是8位)，i是4字节(32位)
				int i=b&0xff;
				//int类型的i转换成16进制的字符
				String hexString = Integer.toHexString(i);
				//给不够2位的字符补0
				if(hexString.length()<2){
					hexString=hexString+"0";
				}
				stringBuffer.append(hexString);
			}
			Log.i("md5Util", stringBuffer.toString());
			return stringBuffer.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
