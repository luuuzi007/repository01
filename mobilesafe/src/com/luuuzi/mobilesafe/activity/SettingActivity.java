package com.luuuzi.mobilesafe.activity;

import com.luuuzi.mobilesafe.R;
import com.luuuzi.mobilesafe.util.ConstantUtil;
import com.luuuzi.mobilesafe.util.spUtil;
import com.luuuzi.mobilesafe.view.SettingItemVeiw;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity{
	private Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		mContext=this;
		initUpdate();
	}

	/**
	 * 设置是否自动更新
	 */
	private void initUpdate() {
		//获取update(自动更新设置)的整个布局
		final SettingItemVeiw siv_update = (SettingItemVeiw) findViewById(R.id.siv_update);
		/**
		 * 获取上一次的选中状态(用户自己保存的)
		 */
		boolean booean = spUtil.getBooean(mContext, ConstantUtil.CONTANT_UPDATE, false);
		//将更新按钮的状态设置成上次用户保存的状态
		siv_update.setChecked(booean);
		//当点击到update的整个控件时执行，将选中的设置成不选中的，不选中的设置成选中的
		siv_update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//获取当前布局中checkbox的选中状态
				boolean isCheck = siv_update.isCheck();
				//点击后选中状态就变成相反所以加个 非!
				siv_update.setChecked(!isCheck);
				//重新保存更新选中状态
				spUtil.pullBooean(mContext, ConstantUtil.CONTANT_UPDATE, !isCheck);
				
			}
		});
		
	}
}
