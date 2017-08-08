package com.luuuzi.mobilesafe.activity;

import com.luuuzi.mobilesafe.R;
import com.luuuzi.mobilesafe.util.ConstantUtil;
import com.luuuzi.mobilesafe.util.spUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 设置页面4
 * @author admin
 *
 */
public class Setup4Activity extends Activity{
	private Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext=this;
		setContentView(R.layout.activity_setup4);
	}
	/**
	 * 上一页按钮点击事件
	 * @param view
	 */
	public void previousPage4(View view){
		Intent intent = new Intent(mContext, Setup3Activity.class);
		startActivity(intent);
		finish();
	}
	
	/**
	 * 下一页点击事件nextPage4
	 * @param view
	 */
	public void nextPage4(View view){
		Intent intent = new Intent(mContext,SetupOverActivity.class);
		startActivity(intent);
		startActivity(intent);
		finish();
		//设置完成将手机防盗的值改变为true
		spUtil.pullBooean(mContext, ConstantUtil.SETUPOVER, true);
	}
}
