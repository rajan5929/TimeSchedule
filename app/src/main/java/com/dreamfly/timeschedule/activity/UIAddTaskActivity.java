package com.dreamfly.timeschedule.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dreamfly.debuginfo.LogPrint;
import com.dreamfly.timeschedule.R;
import com.dreamfly.timeschedule.bo.ConstantVar;
import com.dreamfly.timeschedule.bo.TimeItemEntity;
import com.dreamfly.timeschedule.utils.CommonUtils;
import com.dreamfly.timeschedule.view.widget.EditTextWithDel;

import de.greenrobot.event.EventBus;

public class UIAddTaskActivity extends Activity{

	private static final String TAG = "UIAddTaskActivity";
	private View mVFirstLevel;
	private View mVSecondLevel;
	private View mVThirdLevel;
	private View mVFourthLevel;
	private CheckBox mCBFirstLevel;
	private CheckBox mCBSecondLevel;
	private CheckBox mCBThirdLevel;
	private CheckBox mCBFourthLevel;
	private ImageButton mBackBtn;
	private Button mSaveBtn;
	private ImageButton mDelBtn;
	private EditTextWithDel mEditTitle;
	private EditTextWithDel mEditNotice;
	private TimeItemEntity mTimeItemEntity;
	private boolean mFinish;
	private String mTitle;
	private String mComment;
	private int mLevel = ConstantVar.STATUS_FIRST_LEVEL;
	private String mStartTime;
	private String mEndTime;
	private boolean mAlarm;


	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		if(savedInstanceState != null) {
//			LogPrint.Warning("saveInstanceState != null... resotre");
			restoreInstance(savedInstanceState);
		} else {
			if(intent != null) {
				mTimeItemEntity = (TimeItemEntity)intent.getSerializableExtra(ConstantVar.ADD_TASK);
			}
		}
		if(mTimeItemEntity != null) {
//			LogPrint.Debug("timeEntity = " + mTimeItemEntity.toString());
			mFinish = mTimeItemEntity.getB_finish();
			mTitle = mTimeItemEntity.getS_titile();
			mComment = mTimeItemEntity.getS_notice();
			mLevel = mTimeItemEntity.getI_status();
			mStartTime = mTimeItemEntity.getS_start_time();
			mEndTime = mTimeItemEntity.getS_end_time();
		}
		setContentView(R.layout.layout_add_task);
		initView();
		initListener();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
//		LogPrint.Warning("=>>start to onSavedInstanceState");
		outState.putSerializable(ConstantVar.TASK_DATA, mTimeItemEntity);
		super.onSaveInstanceState(outState);
	}

	private void restoreInstance(Bundle savedInstanceState) {
		mTimeItemEntity = (TimeItemEntity)savedInstanceState.getSerializable(ConstantVar.TASK_DATA);
	}


	private void initView() {
		mVFirstLevel = findViewById(R.id.ll_first_level);
		mVSecondLevel = findViewById(R.id.ll_second_level);
		mVThirdLevel = findViewById(R.id.ll_third_level);
		mVFourthLevel = findViewById(R.id.ll_fourth_level);
		mCBFirstLevel = (CheckBox) findViewById(R.id.cb_first_level);
		mCBSecondLevel = (CheckBox) findViewById(R.id.cb_second_level);
		mCBThirdLevel = (CheckBox) findViewById(R.id.cb_third_level);
		mCBFourthLevel = (CheckBox) findViewById(R.id.cb_fourth_level);
		mBackBtn = (ImageButton) findViewById(R.id.icon_back);
		mSaveBtn = (Button) findViewById(R.id.icon_save);
		mDelBtn = (ImageButton) findViewById(R.id.icon_delete);
		mEditTitle = (EditTextWithDel) findViewById(R.id.main_edit_task);
		mEditNotice = (EditTextWithDel) findViewById(R.id.edit_notice);

		mEditTitle.setText(mTitle);
		mEditNotice.setText(mComment);
		setStatusView(mLevel);
	}

	private void initListener() {
		mVFirstLevel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mLevel = ConstantVar.STATUS_FIRST_LEVEL;
				setStatusView(ConstantVar.STATUS_FIRST_LEVEL);
			}
		});
		mVSecondLevel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mLevel = ConstantVar.STATUS_SECOND_LEVEL;
				setStatusView(ConstantVar.STATUS_SECOND_LEVEL);
			}
		});
		mVThirdLevel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mLevel = ConstantVar.STATUS_THIRD_LEVEL;
			    setStatusView(ConstantVar.STATUS_THIRD_LEVEL);
			}
		});
		mVFourthLevel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mLevel = ConstantVar.STATUS_FOURTH_LEVEL;
				setStatusView(ConstantVar.STATUS_FOURTH_LEVEL);
			}
		});

		mBackBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		mSaveBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
                LogPrint.Debug("start to save the item to database...");
                String strTitle = mEditTitle.getText().toString();
				if("".equals(strTitle)) {
					Toast.makeText(UIAddTaskActivity.this, R.string.str_plz_title, Toast.LENGTH_SHORT).show();
					return ;
				}
				String strNotice = mEditNotice.getText().toString();
				TimeItemEntity timeItemEntity = new TimeItemEntity();
				timeItemEntity.setS_titile(strTitle);
				timeItemEntity.setI_status(mLevel);
				timeItemEntity.setS_notice(strNotice);
				CommonUtils.getInstance(UIAddTaskActivity.this).saveTimeStruct(timeItemEntity);
				EventBus.getDefault().post(timeItemEntity);
                finish();
			}
		});
//		mDelBtn.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//			}
//		});
	}

	private void setStatusView(final int level) {
		switch (level) {
			case ConstantVar.STATUS_FIRST_LEVEL:
				mCBFirstLevel.setChecked(true);
				mCBSecondLevel.setChecked(false);
				mCBThirdLevel.setChecked(false);
				mCBFourthLevel.setChecked(false);
				break;
			case ConstantVar.STATUS_SECOND_LEVEL:
				mCBFirstLevel.setChecked(false);
				mCBSecondLevel.setChecked(true);
				mCBThirdLevel.setChecked(false);
				mCBFourthLevel.setChecked(false);
				break;
			case ConstantVar.STATUS_THIRD_LEVEL:
				mCBFirstLevel.setChecked(false);
				mCBSecondLevel.setChecked(false);
				mCBThirdLevel.setChecked(true);
				mCBFourthLevel.setChecked(false);
				break;
			case ConstantVar.STATUS_FOURTH_LEVEL:
				mCBFirstLevel.setChecked(false);
				mCBSecondLevel.setChecked(false);
				mCBThirdLevel.setChecked(false);
				mCBFourthLevel.setChecked(true);
				break;
			default:
				mCBFirstLevel.setChecked(true);
				mCBSecondLevel.setChecked(false);
				mCBThirdLevel.setChecked(false);
				mCBFourthLevel.setChecked(false);
				break;
		}
	}
}