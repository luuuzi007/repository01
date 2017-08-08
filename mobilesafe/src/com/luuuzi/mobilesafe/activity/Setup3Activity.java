package com.luuuzi.mobilesafe.activity;

import com.luuuzi.mobilesafe.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Setup3Activity extends Activity{
	private Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext=this;
		setContentView(R.layout.activity_setup3);
	}
	/**
	 * 上一页点击按钮previousPage
	 * @param view
	 */
	public void previousPage3(View view){
		Intent intent = new Intent(mContext,Setup2Activity.class);
		startActivity(intent);
		finish();
	}
	/**
	 * 下一页点击事件
	 * @param view
	 */
	public void nextPage(View view){
		Intent intent = new Intent(mContext,Setup4Activity.class);
		startActivity(intent);
		finish();
	}
}
