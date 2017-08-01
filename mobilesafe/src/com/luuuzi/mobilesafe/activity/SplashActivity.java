package com.luuuzi.mobilesafe.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.luuuzi.mobilesafe.R;
import com.luuuzi.mobilesafe.R.layout;
import com.luuuzi.mobilesafe.R.menu;
import com.luuuzi.mobilesafe.util.ConstantUtil;
import com.luuuzi.mobilesafe.util.ToastUtil;
import com.luuuzi.mobilesafe.util.spUtil;
import com.luuuzi.mobilesafe.util.streamUtli;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PermissionInfo;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SplashActivity extends Activity {

	/**
	 * 更新版本的状态码
	 */
	protected static final int UPDATE_VERSION = 100;

	/**
	 * 进入应用程序主界面的状态码
	 */
	protected static final int ENTER_HOME = 101;

	/**
	 * URL错误状态码
	 */
	protected static final int URL_ERROR = 102;

	/**
	 * io错误状态码
	 */
	protected static final int IO_ERROR = 103;

	/**
	 * json解析错误状态码
	 */
	protected static final int JSON_ERROR = 104;
	private Context mContext;
	private String tag = "SplashActivity";
	private TextView tv_version_name;
	private int mVersionCode;
	private String mVersionDes_st;
	private String mVersionUrl_st;

	// 获取服务器的版本内容，在dialog中显示
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case UPDATE_VERSION:
				// 弹出对话框更新版本
				Builder builder = new AlertDialog.Builder(mContext);
				// 设置警告对话框左上角图标
				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("版本更新提示");
				builder.setMessage(mVersionDes_st);
				//点击返回按钮时执行此方法
				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
					
					@Override
					public void onCancel(DialogInterface dialog) {
						enterHome();
						//关闭对话框
						dialog.dismiss();
						
					}
				});
				builder.setNegativeButton("稍后更新",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// 结束对话框,跳转至主界面
								enterHome();

							}
						});

				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// 确定更新版本,下载apk
								download();
								//1.确定下载文件存放的位置
								

							}

						});
				
				builder.show();

				break;
			case ENTER_HOME:
				// 进入应用的主界面
				enterHome();
				break;
			case URL_ERROR:
				// url错误
				ToastUtil.show(mContext, "url错误");
				enterHome();
				break;
			case IO_ERROR:
				// io错误
				ToastUtil.show(mContext, "io错误");
				enterHome();
				break;
			case JSON_ERROR:
				ToastUtil.show(mContext, "json解析错误");
				enterHome();
				break;
			}
		};
	};

	private RelativeLayout mRelativeLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		mContext = this;
		// 初始化ui
		initUi();
		// 获取数据的方法
		initdata();
		//初始化动画，加载Splash界面
		initAnimation();

	}

	/**
	 * 让Splash界面实现动画效果
	 */
	private void initAnimation() {
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(3000);
		mRelativeLayout.startAnimation(alphaAnimation);
		
		
	}

	/**
	 * 从服务器下载apk
	 */
	private void download() {
		//1.判断sdcard是否可用
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			//sd卡状态正常:File.separator表示斜杠(/),mobilesafe.apk是下载文件的名称
			String path=Environment.getDataDirectory().getPath()+File.separator+"mobilesafe.apk";
			//达到sdcard目录的对象
			File sdcardFile = Environment.getDataDirectory();
			long sd_usable=sdcardFile.getUsableSpace();
			String fileSize = Formatter.formatFileSize(mContext, sd_usable);
			Log.i(path, "sdcard剩余空间为:"+fileSize);
			
			//2.执行下载操作,发送请求获取apk，并且放置到指定路径
			HttpUtils httpUtils = new HttpUtils();
				//2.1调用download方法下载
			httpUtils.download(mVersionUrl_st, path, new RequestCallBack<File>() {
				//成功时回调
				@Override
				public void onSuccess(ResponseInfo<File> arg0) {
					//下载成功
					Log.i(tag, "下载成功");
					File file = arg0.result;
					/**
					 * 开始安装
					 */
					//安装应用的界面都是一样的，因为是系统的界面，去查看系统安装界面的源码，apk
					//通过隐式意图开启系统安装应用
					installerApk(file);
					
					
					
				}
				//失败时回调
				@Override
				public void onFailure(HttpException arg0, String arg1) {
					
					Log.i(tag, "下载失败");
					Log.i(tag, "异常信息===="+arg1);
				}
				//刚刚开始下载时执行的方法
				@Override
				public void onStart() {
					Log.i(tag, "开始下载");
					super.onStart();
				}
				//下载过程中的方法,参数1(total):下载文件的总大小， 参数2(current)：当前正在下载的位置,参数2(isUploading):是否正在下载
				@Override
				public void onLoading(long total, long current,
						boolean isUploading) {
					Log.i(tag, "正在下载,下载文件大小："+total+",下载位置；"+current);
					super.onLoading(total, current, isUploading);
				}
			});
		}else{
			//sdcard可用
			ToastUtil.show(mContext, "sdcard不可用");
		}
		
	}

	
	/**
	 * 安装apk
	 * 通过隐式意图开启系统的安装apk的应用程序安装(安装应用的界面都是一样的，因为是系统的界面，去查看系统安装界面的源码，apk)
	 * 设置隐式意图对象的属性和系统的安装apk应用相匹配
	 * @param file 要安装的apk文件
	 */
	protected void installerApk(File file) {
		
		Intent intent = new Intent();
		//设置action
		intent.setAction("android.intent.action.VIEW");
		//添加category() 种类
		intent.addCategory("android.intent.category.DEFAULT");
		//设置数据源
		intent.setData(Uri.fromFile(file));
		//设置类型
		intent.setType("application/vnd.android.package-archive");
		//开启一个Activity并同时添加请求码
		startActivityForResult(intent, 0);
	}
	/**
	 * 安装apk的系统应用程序的Dialog也是要在一个依赖一个Activity上的，
	 * 当点击取消安装时，就会把所依赖的Activity关闭，然后返回到SplashActivity 在执行此方法跳转到mainActivity
	 * 
	开启一个Activity后返回结果调用的方法
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		enterHome();
		super.onActivityResult(requestCode, resultCode, data);
	}
	/**
	 * 进入应用的主界面
	 */
	protected void enterHome() {
		Intent intent = new Intent();
		intent.setClass(mContext, MainActivity.class);
		startActivity(intent);
		// 在开启一个新的界面后将导航界面关闭(导航界面只可见一次)
		finish();

	}

	/**
	 * 获取数据
	 */
	private void initdata() {
		/** 1.应用版本名称 */
		tv_version_name.setText("版本名称：" + getVersionName());
		/** 2.检测当前版本是否是最新版本(用本地版本号去和服务器端的版本号做比较)，如果不是则提示用户更新(member) */
		// 2.1获得版本号
		mVersionCode = getVersionCode();
		// 2.2获取服务器应用的版本号(客户端发请求，服务端做响应(json,xml))
		// http://www.oxx.com/update74.com.json?key=value 返回200则请求成功，流的方式将数据读取出来
		//获取上次用户设置的提示更新操作
		if(spUtil.getBooean(mContext, ConstantUtil.CONTANT_UPDATE, false)){
			//自动更新
			checkVersion();
		}else{
			//不检测更新，直接跳转到主界面
			//传递参数发送后延时执行(延时发送消息)
			//mHandler.sendMessageDelayed(msg, delayMillis);
			//发送状态码，延时执行
			//参数1(what):状态码，int类型
			//参数2(delayMillis):延时时间，ms
			mHandler.sendEmptyMessageDelayed(ENTER_HOME, 4000);
		}
	}

	/**
	 * 发送网络请求，从服务器端获取版本号
	 */
	private void checkVersion() {
		/** 方式一： */
		new Thread() {
			private Message message = Message.obtain();

			public void run() {
				// 获取开启请求网络时间
				long startTime = System.currentTimeMillis();
				// 发送请求获取数据,参数为请求json地址
				// http://192.168.5.214:8080/update.json,可以用，测试阶段不是最优
				// 仅限于模拟器访问电脑的tomcat:10.0.2.2

				try {
					// 1.封装URL地址
					URL url;
					url = new URL("http://192.168.5.58:8090/update.json");
					// 2.开启一个链接
					HttpURLConnection httpurlconnection = (HttpURLConnection) url
							.openConnection();
					// 3.设置请求参数（请求头）
					// 请求超时时间（没连上服务器）
					httpurlconnection.setConnectTimeout(5000);
					// 读取超时(从服务器中没拿到数据)
					httpurlconnection.setReadTimeout(3000);
					// 请求方式：默认为get
					httpurlconnection.setRequestMethod("GET");
					// 4.获取响应码
					int code = httpurlconnection.getResponseCode();
					if (code == 200) {
						// 5以流的方式将数据获取下来
						InputStream inputStream = httpurlconnection
								.getInputStream();
						// 6.将流转换Wie字符串(工具类)
						String json = streamUtli.streamToString(inputStream);
						Log.i(tag, json);
						/**
						 * json解析
						 */
						JSONObject jsonObject = new JSONObject(json);
						String versionName_st = jsonObject
								.getString("versionName");
						mVersionDes_st = jsonObject.getString("versionDes");
						String versionCode_st = jsonObject
								.getString("versionCode");
						mVersionUrl_st = jsonObject.getString("versionUrl");

						Log.i(tag, versionName_st + "," + mVersionDes_st + ","
								+ versionCode_st);
						/**
						 * 比对版本号
						 */
						if (mVersionCode < Integer.parseInt(versionCode_st)) {

							// 通知用户更新版本的状态码
							message.what = UPDATE_VERSION;
						} else {
							// 直接跳转到下一个页面
							message.what = ENTER_HOME;
						}
					}
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					message.what = URL_ERROR;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					message.what = IO_ERROR;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					message.what = JSON_ERROR;
				} finally {
					// 访问网络结束时间
					long endTime = System.currentTimeMillis();
					if (endTime - startTime < 4000) {
						// 请求操作的时长小于4s，强制让其睡眠4s中
						try {
							Thread.sleep(4000 - (endTime - startTime));
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// 指定睡眠时间,请求网络超过4s则不做处理
					}
					mHandler.sendMessage(message);
				}

			};
		}.start();
		/**
		 * 方式二
		 */
		/*
		 * new Thread(new Runnable() {
		 * 
		 * @Override public void run() { // TODO Auto-generated method stub
		 * 
		 * } });
		 */
	}

	/**
	 * 获取当前本地应用(当前应用)版本号
	 * 
	 * @return 非0 则拿到，0则没有拿到
	 */
	private int getVersionCode() {
		PackageManager packageManager = getPackageManager();

		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(
					mContext.getPackageName(), 0);
			int versionCode = packageInfo.versionCode;
			return versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}

	/**
	 * 获得版本的名称
	 * 
	 * @return 应用的版本名称 返回null表示异常
	 */
	private String getVersionName() {
		// 1.包管理者对象packgeManager
		PackageManager packageManager = getPackageManager();
		try {
			// 2.从包的管理者对象中获得指定包名的具体信息
			// 参数1包名(packageName)：包名，参数2标记(flags):Activity 权限等 0代表获取基本信息
			PackageInfo packageInfo = packageManager.getPackageInfo(
					mContext.getPackageName(), 0);
			// 3.获得版本名称
			String versionName = packageInfo.versionName;
			return versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 初始化ui
	 */
	private void initUi() {
		tv_version_name = (TextView) findViewById(R.id.tv_version_name);
		mRelativeLayout = (RelativeLayout) findViewById(R.id.rl_root);

	}
}
