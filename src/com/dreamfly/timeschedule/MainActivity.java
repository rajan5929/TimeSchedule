package com.dreamfly.timeschedule;

import java.io.InputStream;
import java.util.ArrayList;

import com.dreamfly.timeschedule.maininterface.MainInterface;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements OnClickListener, OnPageChangeListener{
	private static final String TAG = "ViewPagerTest";
	private static final int[] mPics = {R.drawable.introduce_1,
										R.drawable.introduce_2,
										R.drawable.introduce_3,
										R.drawable.introduce_4};
	private ImageView[] mPoints;
	private ViewPager mViewPager;
	private ViewPagerAdapter mVpAdapter;
	private ArrayList<View> mViews;
	private int mCount = 0;
	
	private int mCurrIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
	}
	
	public void initView(){
		mViews = new ArrayList<View>();
		mViewPager = (ViewPager)findViewById(R.id.viewpager);
		mVpAdapter = new ViewPagerAdapter(mViews);
	}
	
	public void initData(){
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		for(int i = 0; i < mPics.length; i++){
			ImageView imgView = new ImageView(this);
			imgView.setLayoutParams(params);
//			imgView.setScaleType(ScaleType.FIT_XY);
			imgView.setImageResource(mPics[i]);
			mViews.add(imgView);
//			Bitmap bitmap = readBitMap(this, mPics[i]);
//			imgView.setImageBitmap(bitmap);
//			mViews.add(imgView);
			
		}
		// set data adapter
		mViewPager.setAdapter(mVpAdapter);
		mViewPager.setOnPageChangeListener(this);
		
		initPoint();
	}
	
	private void initPoint(){
		LinearLayout linearLayout = (LinearLayout)findViewById(R.id.ll_dot);
		mPoints = new ImageView[mPics.length];
		for(int i = 0; i < mPics.length; i++){
			mPoints[i] = (ImageView)linearLayout.getChildAt(i);
			//默认都设为灰色
			mPoints[i].setEnabled(true);
			mPoints[i].setOnClickListener(this);
			//设置位置Tag，方便与当前位置对应
			mPoints[i].setTag(i);		
		}
		mCurrIndex = 0;
		// 选中状态
		mPoints[mCurrIndex].setEnabled(false);
	}
	
	 /**
     * 以最省内存的方式读取本地资源的图片, 避免加载出现OOM, 但是需要注意不能自动匹配分辨率大小，需要在H/L/MDPI等里面配置对应图片
     * @param context
     * @param resId
     * @return
     */  
	public static Bitmap readBitMap(Context context, int resId){  
		BitmapFactory.Options opt = new BitmapFactory.Options();  
		opt.inPreferredConfig = Bitmap.Config.RGB_565;   
		opt.inPurgeable = true;  
		opt.inInputShareable = true;  
		//	获取资源图片  
		InputStream is = context.getResources().openRawResource(resId);  
		return BitmapFactory.decodeStream(is,null,opt);  
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 当滑动状态变化时候调用
	 * 1 2 0地变化
	 * */
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		Log.d(TAG, "===onPageScrollStateChanged====arg0 = " + arg0 + "; mCount = " + mCount);
		// 向右滑onPageScrolled持续超过3次后，再执行动作
		if(arg0 == 0 && mCount > 3){
			Intent intent = new Intent();
//			intent.setClass(MainActivity.this, MainListInterface.class);
			intent.setClass(MainActivity.this, MainInterface.class);
			startActivity(intent);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			finish();
		} 
		mCount = 0;
		
	}

	/**
	 * 当当前页面被滑动时候调用
	 * */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		if(arg0 == mPics.length - 1){
			mCount++;
		}
		Log.d(TAG, "====onPageScrolled===arg0 = " + arg0 + "; arg1 = " + arg1 
				+ "; arg2 = " + arg2 + "; mPics.length = " + mPics.length + "; mCount = " + mCount);
	}

	/**
	 * 当新的页面被选中时候调用
	 * */
	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		Log.d(TAG, "====onPageSelected==== arg0 = " + arg0);
		// 设置当前点被选择
		setCurDot(arg0);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		int position = (Integer)arg0.getTag();
		Log.d(TAG, "====onClick ==== position = " + position);
		setCurView(position);
		setCurDot(position);
	}
	
	private void setCurView(int position){
		if(position < 0 || position >= mPics.length){
			return ;
		}
		Log.d(TAG, "====setCurView====");
		mViewPager.setCurrentItem(position);
	}
	
	private void setCurDot(int position){
		if(position < 0 || position >= mPics.length || position == mCurrIndex){
			return ;
		}
		Log.d(TAG, "======setCurDot======");
		mPoints[position].setEnabled(false);
		mPoints[mCurrIndex].setEnabled(true);
		mCurrIndex = position;
	}

}
