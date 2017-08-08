package com.luuuzi.mobilesafe.activity;

import com.luuuzi.mobilesafe.R;
import com.luuuzi.mobilesafe.util.ConstantUtil;
import com.luuuzi.mobilesafe.util.spUtil;
import com.luuuzi.mobilesafe.view.SettingItemVeiw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 *手机防盗设置界面2Activity
 * @author admin
 *
 */
public class Setup2Activity extends Activity{
	private Context mContext;
	private SettingItemVeiw siv_bound;
	private String tag="SEtup2Activity";
	private boolean isCheck;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext=this;
		setContentView(R.layout.activity_setup2);
		initUI();
	}
	
	/**
	 * sim卡的绑定逻辑实现
	 */
	private void initUI() {
		//1.回显，获取上一次sim的序列号
		String sim_bound = spUtil.getString(mContext, ConstantUtil.SIM_SIMSERIALNUMBER, "");
		siv_bound = (SettingItemVeiw) findViewById(R.id.siv_bound);
		//2.根据序列号判断
		if(TextUtils.isEmpty(sim_bound)){
			Log.i(tag, "序列号为空");
			//如果序列号为空，则将checkbox设置为不选中
			siv_bound.setChecked(false);
		}else{
			siv_bound.setChecked(true);
		}
		//3.设置点击事件，并改变CheckBox的状态
		siv_bound.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//2.1拿到CheckBox的选中状态
				isCheck= siv_bound.isCheck();
				//当点击后设置CheckBox的状态要改变
				siv_bound.setChecked(!isCheck);
				if(!isCheck){
					//4存储sim卡序列号
					//4.1先拿到sim卡序列号，在存储,拿到telephone的管理者对象
					TelephonyManager telephoneManager=(TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
					//4.2通过管理者对象拿到序列号
					String simSerialNumber = telephoneManager.getSimSerialNumber();
					//4.3存储序列号
					spUtil.putString(mContext, ConstantUtil.SIM_SIMSERIALNUMBER, simSerialNumber);				
				}else{
					//删除sim卡序列号
					spUtil.removeNode(mContext, ConstantUtil.SIM_SIMSERIALNUMBER);
					
				}
			}
		});
		
	}
	/**上一页按钮点击事件
	 * @param view
	 */
	public void previousPage(View view){
		Intent intent = new Intent(mContext, Setup1Activity.class);
		startActivity(intent);
		finish();
	}
	/**
	 * 下一页按钮点击事件
	 * @param view
	 */
	public void nextPage(View view){
		Intent intent = new Intent(mContext, Setup3Activity.class);
		startActivity(intent);
		finish();
	}
}
