package com.luuuzi.mobilesafe.activity;

import com.luuuzi.mobilesafe.R;
import com.luuuzi.mobilesafe.util.ConstantUtil;
import com.luuuzi.mobilesafe.util.ToastUtil;
import com.luuuzi.mobilesafe.util.md5Util;
import com.luuuzi.mobilesafe.util.spUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

public class MainActivity extends Activity {
	private String tag = "MainActivity";
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
	// 用户自己保存的用户名和密码
	private String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		initUI();
		initData();
		configAdapter();
	}

	/**
	 * 配置适配器，并添加监听方法
	 */
	private void configAdapter() {
		// gv_home已经初始化
		HomeAdapter homeAdapter = new HomeAdapter(mContext, mHome_str,
				mHomeDrawables);
		gv_home.setAdapter(homeAdapter);
		// 设置点击事件的监听
		gv_home.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					// 手机防盗功能的实现
					// 弹出对话框提示输入密码
					showDialog();
					break;
				case 8:
					// 点击设置中心
					Intent intent = new Intent();
					intent.setClass(mContext, SettingActivity.class);
					startActivity(intent);
					break;
				}

			}
		});
	}

	/**
	 * 点击手机防盗按钮后弹出输入密码 //2种情况, 1.第一次进入应用，没有设置密码 2.用户已经设置过密码，直接提示用户输入密码，判断是否正确
	 */
	protected void showDialog() {
		password = spUtil.getString(mContext,
				ConstantUtil.MOBILESAFE_PASSWORD, null);

		//Log.i(tag, "用户设置的密码=====：" + password);
		// 判断用户是否是第一次进入应用
		if (TextUtils.isEmpty(password)) {
			// 没有设置密码，即第一次进入
			firstPasswordDialog();
		} else {
			// 之前设置过密码，输入密码做比较
			showComfirmDialog();
		}

	}

	/**
	 * 输入密码和之前的做比较
	 */
	private void showComfirmDialog() {
		Builder builder = new AlertDialog.Builder(mContext);
		final View view = View.inflate(mContext,
				R.layout.dialog_confirm_password, null);
		final AlertDialog dialog = builder.create();
		dialog.setView(view);
		// 获取确定按钮和取消按钮
		Button btConfirm = (Button) view.findViewById(R.id.bt_cofirm_password);
		Button btCancel = (Button) view.findViewById(R.id.bt_cancel_password);
		// 设置点击事件
		btConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 比较用户输入的密码和之前存储的密码
				EditText ed_inputPassword = (EditText) view
						.findViewById(R.id.ed_input_password);
				// 拿到用户输入的密码
				String inputPassword = ed_inputPassword.getText().toString()
						.trim();
				// 加盐在比较:mobilesafe
				String md5Password = md5Util.encoder(inputPassword
						+ "mobilesafe");
				// 和之前用户保存的密码做比较
				if (md5Password.equals(password)) {
					// 进入下一个界面,如果用户之前设置了就直接进入设置完成界面
					// 没有设置则进入setup1界面
					if (spUtil.getBooean(mContext, ConstantUtil.SETUPOVER,
							false)) {
						Intent intent = new Intent(mContext,
								SetupOverActivity.class);
						startActivity(intent);
					} else {

						Intent intent = new Intent(mContext,
								Setup1Activity.class);
						startActivity(intent);
					}
					// 进入界面后解散Dialog
					dialog.dismiss();
				} else {
					ToastUtil.show(mContext, "密码错误");
				}

			}
		});
		btCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 解散dialog
				dialog.dismiss();

			}
		});
		dialog.show();
	}

	/**
	 * 第一次进入设置密码
	 */
	private void firstPasswordDialog() {
		Builder builder = new AlertDialog.Builder(mContext);
		// 1.将布局文件转换为view对象
		View dialogView = View.inflate(mContext, R.layout.dialog_set_password,
				null);
		final AlertDialog dialog = builder.create();
		// 2.设置给builder
		// dialog.setView(dialogView);
		// 为了兼容低版本，给对话框设置布局的时候，让其没有内边距(android系统默认提供出来的)
		dialog.setView(dialogView, 0, 0, 0, 0);

		// 拿到确定和取消按钮button控件
		Button bt_submit = (Button) dialogView.findViewById(R.id.bt_submit);
		Button bt_cancel = (Button) dialogView.findViewById(R.id.bt_cancel);
		// 拿到输入密码的edittext
		final EditText et_iinputPassword = (EditText) dialogView
				.findViewById(R.id.et_inputPassword);
		final EditText et_submitPasswrod = (EditText) dialogView
				.findViewById(R.id.et_submitPassword);
		// 设置点击事件
		bt_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 拿到密码
				String inputPasswrodStr = et_iinputPassword.getText()
						.toString().trim();
				String submitPasswordStr = et_submitPasswrod.getText()
						.toString().trim();
				// 判断输入是否为空
				if (!TextUtils.isEmpty(inputPasswrodStr)
						&& !TextUtils.isEmpty(submitPasswordStr)) {
					// 做比较
					if (inputPasswrodStr.equals(submitPasswordStr)) {
						// 跳转至新的Activity
						Intent intent = new Intent(mContext,
								Setup1Activity.class);
						startActivity(intent);
						Log.i(tag, "用户输入的密码：===" + submitPasswordStr);
						// 保存密码:先对密码进行加密在保存
						// 加密
						String md5Password = md5Util.encoder(submitPasswordStr
								+ "mobilesafe");
						Log.i(tag, "加盐后的密码===========" + md5Password);
						// 保存密码
						spUtil.putString(mContext, ConstantUtil.MOBILESAFE_PASSWORD,md5Password);
						Log.i(tag, "保存密码");

						// 跳转过去后，要隐藏Dialog
						dialog.dismiss();
					} else {
						// 输入密码不匹配
						ToastUtil.show(mContext, "密码输入不一致");

					}
				} else {
					// 有一个输入框为空提示用户
					ToastUtil.show(mContext, "请输入密码");
				}

			}
		});
		// 取消按钮点击事件
		bt_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();

			}
		});
		// 显示Dialog
		dialog.show();

	}

	/**
	 * 获取数据
	 */
	private void initData() {
		mHome_str = new String[] { "手机防盗", "通信卫士", "软件管理", "进程管理", "流量统计",
				"手机杀毒", "缓存清理", "高级工具", "设置中心" };
		mHomeDrawables = new int[] { R.drawable.home_safe,
				R.drawable.home_callmsgsafe, R.drawable.home_apps,
				R.drawable.home_taskmanager, R.drawable.home_netmanager,
				R.drawable.home_trojan, R.drawable.home_sysoptimize,
				R.drawable.home_tools, R.drawable.home_settings };
	}

	/**
	 * 初始化ui
	 */
	private void initUI() {
		gv_home = (GridView) findViewById(R.id.gv_home);

	}
}
