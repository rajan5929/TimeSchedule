package com.dreamfly.timeschedule.maininterface;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dreamfly.debuginfo.LogPrint;
import com.dreamfly.timeschedule.R;
import com.dreamfly.timeschedule.model.TimeStruct;
import com.dreamfly.timeschedule.utils.CommonUtils;
import com.dreamfly.timeschedule.utils.greendao.TSDatabaseMgrMul;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class MainBaseAdapter extends BaseAdapter{
	private Context mContext;
	private List<TimeStruct> mData;
	
	private LayoutInflater mInflater;
	
	public MainBaseAdapter(Context context){
		mContext = context;
		mData = new ArrayList<TimeStruct>();
		mInflater = (LayoutInflater)mContext.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
	}

	public void addItem(final TimeStruct item){
		mData.add(item);
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		LogPrint.Debug("====getView; position = " + position + "; convertView = " + convertView);
		ViewHolder holder = null;
		// Optimize the speed
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.item, null);
			holder = new ViewHolder();
			holder.checkBox = (CheckBox) convertView.findViewById(R.id.checked);
			holder.TxtHeading = (TextView) convertView.findViewById(R.id.heading);
			holder.TxtStateinfo = (TextView) convertView.findViewById(R.id.stateinfo);
			holder.TxtTime = (TextView) convertView.findViewById(R.id.time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		holder.TxtHeading.setText(mData.get(position).getS_titile());
		holder.TxtStateinfo.setText(CommonUtils.getInstance(mContext).getTaskStatus(mData.get(position).getI_status()));
		holder.TxtTime.setText(mData.get(position).getS_start_time());
		holder.checkBox.setChecked(false);
		holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					LogPrint.Debug("===== delete the item task = position = " + position);
					if(position < 0 || position >= mData.size()){
						LogPrint.Debug("===== out of position = " + position);
					} else {
						mData.remove(position);
						notifyDataSetChanged();
					}
				}
			}
		});
		return convertView;
	}
	
	public static class ViewHolder{
		public CheckBox checkBox;
		public TextView TxtHeading;
		public TextView TxtStateinfo;
		public TextView TxtTime;
	}
	
}