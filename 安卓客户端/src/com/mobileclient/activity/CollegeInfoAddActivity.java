package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.CollegeInfo;
import com.mobileclient.service.CollegeInfoService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class CollegeInfoAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明学院编号输入框
	private EditText ET_collegeNumber;
	// 声明学院名称输入框
	private EditText ET_collegeName;
	// 出版成立日期控件
	private DatePicker dp_collegeBirthDate;
	// 声明院长姓名输入框
	private EditText ET_collegeMan;
	// 声明联系电话输入框
	private EditText ET_collegeTelephone;
	// 声明附加信息输入框
	private EditText ET_collegeMemo;
	protected String carmera_path;
	/*要保存的学院信息信息*/
	CollegeInfo collegeInfo = new CollegeInfo();
	/*学院信息管理业务逻辑层*/
	private CollegeInfoService collegeInfoService = new CollegeInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.collegeinfo_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加学院信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_collegeNumber = (EditText) findViewById(R.id.ET_collegeNumber);
		ET_collegeName = (EditText) findViewById(R.id.ET_collegeName);
		dp_collegeBirthDate = (DatePicker)this.findViewById(R.id.dp_collegeBirthDate);
		ET_collegeMan = (EditText) findViewById(R.id.ET_collegeMan);
		ET_collegeTelephone = (EditText) findViewById(R.id.ET_collegeTelephone);
		ET_collegeMemo = (EditText) findViewById(R.id.ET_collegeMemo);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加学院信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取学院编号*/
					if(ET_collegeNumber.getText().toString().equals("")) {
						Toast.makeText(CollegeInfoAddActivity.this, "学院编号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_collegeNumber.setFocusable(true);
						ET_collegeNumber.requestFocus();
						return;
					}
					collegeInfo.setCollegeNumber(ET_collegeNumber.getText().toString());
					/*验证获取学院名称*/ 
					if(ET_collegeName.getText().toString().equals("")) {
						Toast.makeText(CollegeInfoAddActivity.this, "学院名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_collegeName.setFocusable(true);
						ET_collegeName.requestFocus();
						return;	
					}
					collegeInfo.setCollegeName(ET_collegeName.getText().toString());
					/*获取成立日期*/
					Date collegeBirthDate = new Date(dp_collegeBirthDate.getYear()-1900,dp_collegeBirthDate.getMonth(),dp_collegeBirthDate.getDayOfMonth());
					collegeInfo.setCollegeBirthDate(new Timestamp(collegeBirthDate.getTime()));
					/*验证获取院长姓名*/ 
					if(ET_collegeMan.getText().toString().equals("")) {
						Toast.makeText(CollegeInfoAddActivity.this, "院长姓名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_collegeMan.setFocusable(true);
						ET_collegeMan.requestFocus();
						return;	
					}
					collegeInfo.setCollegeMan(ET_collegeMan.getText().toString());
					/*验证获取联系电话*/ 
					if(ET_collegeTelephone.getText().toString().equals("")) {
						Toast.makeText(CollegeInfoAddActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_collegeTelephone.setFocusable(true);
						ET_collegeTelephone.requestFocus();
						return;	
					}
					collegeInfo.setCollegeTelephone(ET_collegeTelephone.getText().toString());
					/*验证获取附加信息*/ 
					if(ET_collegeMemo.getText().toString().equals("")) {
						Toast.makeText(CollegeInfoAddActivity.this, "附加信息输入不能为空!", Toast.LENGTH_LONG).show();
						ET_collegeMemo.setFocusable(true);
						ET_collegeMemo.requestFocus();
						return;	
					}
					collegeInfo.setCollegeMemo(ET_collegeMemo.getText().toString());
					/*调用业务逻辑层上传学院信息信息*/
					CollegeInfoAddActivity.this.setTitle("正在上传学院信息信息，稍等...");
					String result = collegeInfoService.AddCollegeInfo(collegeInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
