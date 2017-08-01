package com.luuuzi.mobilesafe.activity;

import com.luuuzi.mobilesafe.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeAdapter extends BaseAdapter{
	private Context mContext;
	private String[] mHome_str;
	private int[]	mHomeDrawable;
	
	public HomeAdapter(Context context,String[] home_str,int[] homeDrawable){
		this.mHome_str=home_str;
		this.mHomeDrawable=homeDrawable;
		this.mContext=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mHome_str.length;
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return mHome_str[position] ;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
		convertView = View.inflate(mContext, R.layout.gridview_item, null);
		holder=new ViewHolder();
		holder.iv_home=(ImageView) convertView.findViewById(R.id.iv_home);
		holder.tv_home=(TextView) convertView.findViewById(R.id.tv_home);
		
		holder.iv_home.setImageResource(mHomeDrawable[position]);
		holder.tv_home.setText(mHome_str[position]);
		
		convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
		return convertView;
	}
	class ViewHolder{
		private ImageView iv_home;
		private TextView tv_home;
	}
}
