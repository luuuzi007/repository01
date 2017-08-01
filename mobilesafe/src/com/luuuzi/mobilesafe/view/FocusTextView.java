package com.luuuzi.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;
import android.widget.TextView;

public class FocusTextView extends TextView {
	// 由系统调用(上下文+属性集合+布局文件中定义的样式，主题)
	public FocusTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	// 使用xml添加控件,由系统调用(上下文+控件属性集合)
	public FocusTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	// 使用java代码创建控件eg
	// new focusTextView(mContext);
	public FocusTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	// 重写获取焦点的方法，由系统调用,调用的时候默认获取焦点
	@Override
	public boolean isFocused() {
		// TODO Auto-generated method stub
		return true;
	}
}
