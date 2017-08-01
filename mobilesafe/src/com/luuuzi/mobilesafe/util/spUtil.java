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
}
