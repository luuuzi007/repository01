package com.luuuzi.mobilesafe.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class streamUtli {

	/**流转换成字符串
	 * @param is 流对象
	 * @return	返回字符串
	 */
	public static String streamToString(InputStream is) {
		//1.在读取的过程中，将读取的内容存储在缓存中，然后一次性转换成字符串
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		//2.流的读操作，读到没有为止(循环)
		byte[] buffer=new byte[1024];
		//3.记录读取内容的临时变量
		int temp=-1;
		try {
			if((temp=is.read(buffer))!=-1){
				byteArrayOutputStream.write(buffer);
				return byteArrayOutputStream.toString();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				is.close();
				byteArrayOutputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
		
	}

}
