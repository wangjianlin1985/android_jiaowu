package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.CollegeInfo;
import com.mobileclient.service.CollegeInfoService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class CollegeInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明学院编号控件
	private TextView TV_collegeNumber;
	// 声明学院名称控件
	private TextView TV_collegeName;
	// 声明成立日期控件
	private TextView TV_collegeBirthDate;
	// 声明院长姓名控件
	private TextView TV_collegeMan;
	// 声明联系电话控件
	private TextView TV_collegeTelephone;
	// 声明附加信息控件
	private TextView TV_collegeMemo;
	/* 要保存的学院信息信息 */
	CollegeInfo collegeInfo = new CollegeInfo(); 
	/* 学院信息管理业务逻辑层 */
	private CollegeInfoService collegeInfoService = new CollegeInfoService();
	private String collegeNumber;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.collegeinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看学院信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_collegeNumber = (TextView) findViewById(R.id.TV_collegeNumber);
		TV_collegeName = (TextView) findViewById(R.id.TV_collegeName);
		TV_collegeBirthDate = (TextView) findViewById(R.id.TV_collegeBirthDate);
		TV_collegeMan = (TextView) findViewById(R.id.TV_collegeMan);
		TV_collegeTelephone = (TextView) findViewById(R.id.TV_collegeTelephone);
		TV_collegeMemo = (TextView) findViewById(R.id.TV_collegeMemo);
		Bundle extras = this.getIntent().getExtras();
		collegeNumber = extras.getString("collegeNumber");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CollegeInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    collegeInfo = collegeInfoService.GetCollegeInfo(collegeNumber); 
		this.TV_collegeNumber.setText(collegeInfo.getCollegeNumber());
		this.TV_collegeName.setText(collegeInfo.getCollegeName());
		Date collegeBirthDate = new Date(collegeInfo.getCollegeBirthDate().getTime());
		String collegeBirthDateStr = (collegeBirthDate.getYear() + 1900) + "-" + (collegeBirthDate.getMonth()+1) + "-" + collegeBirthDate.getDate();
		this.TV_collegeBirthDate.setText(collegeBirthDateStr);
		this.TV_collegeMan.setText(collegeInfo.getCollegeMan());
		this.TV_collegeTelephone.setText(collegeInfo.getCollegeTelephone());
		this.TV_collegeMemo.setText(collegeInfo.getCollegeMemo());
	} 
}
