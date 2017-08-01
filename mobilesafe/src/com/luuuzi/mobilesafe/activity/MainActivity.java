package com.luuuzi.mobilesafe.activity;

import com.luuuzi.mobilesafe.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MainActivity extends Activity{
	private Context mContext;
	/**
	 * 存主页面数据
	 */
	private String[] mHome_str;
	/**
	 * 存主页面图片
	 */
	private int[] mHomeDrawables;
	/**
	 * GridView对象
	 */
	private GridView gv_home;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext=this;
		initUI();
		initData();
		configAdapter();
	}
	

	/**
	 * 配置适配器，并添加监听方法
	 */
	private void configAdapter() {
		//gv_home已经初始化
		HomeAdapter homeAdapter = new HomeAdapter(mContext, mHome_str, mHomeDrawables);
		gv_home.setAdapter(homeAdapter);
		//设置点击事件的监听
		gv_home.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch(position){
				case 8:
					//点击设置中心
					Intent intent = new Intent();
					intent.setClass(mContext, SettingActivity.class);
					startActivity(intent);
					break;
				}
				
			}
		});
	}


	/**
	 * 获取数据
	 */
	private void initData() {
		mHome_str = new String[]{"手机防盗","通信卫士","软件管理","进程管理","流量统计","手机杀毒","缓存清理","高级工具","设置中心"};
		mHomeDrawables = new int[]{R.drawable.home_safe,R.drawable.home_callmsgsafe,R.drawable.home_apps,
				R.drawable.home_taskmanager,R.drawable.home_netmanager,R.drawable.home_trojan,
				R.drawable.home_sysoptimize,R.drawable.home_tools,R.drawable.home_settings};
	}

	/**
	 * 初始化ui
	 */
	private void initUI() {
		gv_home = (GridView) findViewById(R.id.gv_home);
		
	}
}
