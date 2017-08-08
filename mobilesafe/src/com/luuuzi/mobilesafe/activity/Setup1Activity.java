package com.luuuzi.mobilesafe.activity;

import com.luuuzi.mobilesafe.R;
import com.luuuzi.mobilesafe.util.ConstantUtil;
import com.luuuzi.mobilesafe.util.spUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Setup1Activity extends Activity{
	private Context mContext;
	/**
	 * 下一页button
	 */
	private Button bt_nextPage1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext=this;
		//通过sharedPreferences存储一个Boolean的值来判断手机防盗是否设置完成
		boolean setup_over = spUtil.getBooean(mContext, ConstantUtil.SETUPOVER, false);
		if(setup_over){
			//已经设置直接跳转至设置完成界面
			setContentView(R.layout.activity_setupover_over);
		}else{
			//未设置跳转至设置界面1
			setContentView(R.layout.activity_setup1);
		}
		bt_nextPage1 = (Button) findViewById(R.id.bt_nextpage1);
		bt_nextPage1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//跳转至设置界面2
				Intent intent = new Intent(mContext, Setup2Activity.class);
				startActivity(intent);
				//关闭当前Activity
				finish();
			
			}
		});
	}
	
}
