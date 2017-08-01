package com.luuuzi.mobilesafe.view;

import com.luuuzi.mobilesafe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingItemVeiw extends RelativeLayout {

	private CheckBox cb_setting;
	private TextView tv_updateSetting;
	private String tag="SettingItemView";
	private final String NAMESPCE="http://schemas.android.com/apk/res/com.luuuzi.mobilesafe";
	/**
	 * title属性值
	 */
	private String destitle;
	/**
	 * off属性值
	 */
	private String desoff;
	/**
	 * on属性值
	 */
	private String deson;
	public SettingItemVeiw(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// xml-->view,将设置界面的一个条目直接转换成view对象，直接添加到当前的SettingItemView对应的view中
		// View view2 = View.inflate(context, R.layout.setting_item_view, null);
		// this.addView(view2);
		View view = View.inflate(context, R.layout.setting_item_view, this);
		// 获取当前布局文件中的控件
		TextView tv_settingHome = (TextView) findViewById(R.id.tv_settingHome);
		tv_updateSetting = (TextView) findViewById(R.id.tv_updateSetting);
		cb_setting = (CheckBox) findViewById(R.id.cb_setting);
		//获取自定义和原生属性的操作
		initAttrs(attrs);
		//为控件(显示title的控件)设置属性值(从xml布局中拿到的)
		tv_settingHome.setText(destitle);

	}
	
	/** 
	 * 返回属性集合中自定义属性的值
	 * @param attrs 构造方法中维护好的属性集合
	 */
	private void initAttrs(AttributeSet attrs) {
		/**
		 * attrs.getAttributeCount()获取当前view所在xml布局中使用属性的总数,没使用的不算
		 * 顺序按xml从上到下
		 */
//		for(int i=0;i<attrs.getAttributeCount();i++){
//			//通过索引拿到属性名
//			Log.i(tag, "属性名："+attrs.getAttributeName(i));
//			//通过索引拿到属性值,
//			Log.i(tag, attrs.getAttributeValue(i));
//		}
		//通过命名空间(NAMESPCE)和属性名拿到属性值NAMESPCE="http://schemas.android.com/apk/res/com.luuuzi.mobilesafe";
		destitle = attrs.getAttributeValue(NAMESPCE,"destitle" );
		desoff = attrs.getAttributeValue(NAMESPCE, "desoff");
		deson = attrs.getAttributeValue(NAMESPCE, "deson");
	}
	public SettingItemVeiw(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SettingItemVeiw(Context context) {
		this(context, null);
	}
	/**
	 * @return	获取checkbox的选中状态
	 */
	public boolean isCheck(){
		return cb_setting.isChecked();
	}
	public void setChecked(boolean isCheck){
		cb_setting.setChecked(isCheck);
		if(isCheck){
			tv_updateSetting.setText(deson);
		}else{
			tv_updateSetting.setText(desoff);
		}
	}
}
