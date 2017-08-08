package com.luuuzi.mobilesafe.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class spUtil {
	private static SharedPreferences sharedPreferences;

	/**
	 * 将获取到的更新判断flag存储到私有目录中
	 * 
	 * @param context	上下文
	 * @param key	存储节点名称
	 * @param value	存储值
	 */
	public static void pullBooean(Context context, String key, boolean value) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences("config",
					Context.MODE_PRIVATE);
		}
		Editor edit = sharedPreferences.edit();
		edit.putBoolean(key, value);
		edit.commit();
	}

	/**
	 * @param context	上下文
	 * @param key	存储节点名称
	 * @param defaultBoolean	默认值即此节点没有读取到结果使用的值
	 * @return
	 */
	public static boolean getBooean(Context context, String key,
			boolean defaultBoolean) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences("config",
					Context.MODE_PRIVATE);
		}
		boolean boolean1 = sharedPreferences.getBoolean(key, defaultBoolean);
		return boolean1;
	}
	
	/**
	 * 获取密码：通过key去拿到用户名密码，没有拿到则默认为null
	 * @param context 	上下文
	 * @param key	密码的键
	 * @param defaultPasswrod	用户没有设置密码时的默认密码
	 * @return
	 */
	public static String getString(Context context,String key,String defaultPasswrod){
		if(sharedPreferences==null){
			context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		String str = sharedPreferences.getString(key, defaultPasswrod);
		return str;
	}
	/**保存密码:当用户第一次设置密码时将其保存到私有目录下
	 * @param context	上下文
	 * @param passwrod	用户输入密码
	 */
	public static void putString(Context context,String key,String value){
		if(sharedPreferences==null){
			context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		//sharedPreferences.edit().putString(ConstantUtil.MOBILESAFE_PASSWORD, passwrod);
		Editor edit = sharedPreferences.edit();
		edit.putString(key, value);
		edit.commit();
		
	}
	/**
	 * 删除节点
	 * @param context	上下文
	 * @param key	键
	 */
	public static void removeNode(Context context,String key){
		if(sharedPreferences==null){
			context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		Editor edit = sharedPreferences.edit();
		edit.remove(key);
		edit.commit();
	}
}
