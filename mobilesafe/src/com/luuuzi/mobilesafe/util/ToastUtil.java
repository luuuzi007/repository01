package com.luuuzi.mobilesafe.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
 /**
  * 提示错误信息
 * @param context 上下文
 * @param message 错误内容
 */
public static void show(Context context,String message){
	 Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
 }
}
