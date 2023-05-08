package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.SpecialFieldInfo;
import com.mobileclient.service.SpecialFieldInfoService;
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
public class SpecialFieldInfoAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明专业编号输入框
	private EditText ET_specialFieldNumber;
	// 声明专业名称输入框
	private EditText ET_specialFieldName;
	// 声明所在学院下拉框
	private Spinner spinner_specialCollegeNumber;
	private ArrayAdapter<String> specialCollegeNumber_adapter;
	private static  String[] specialCollegeNumber_ShowText  = null;
	private List<CollegeInfo> collegeInfoList = null;
	/*所在学院管理业务逻辑层*/
	private CollegeInfoService collegeInfoService = new CollegeInfoService();
	// 出版成立日期控件
	private DatePicker dp_specialBirthDate;
	// 声明联系人输入框
	private EditText ET_specialMan;
	// 声明联系电话输入框
	private EditText ET_specialTelephone;
	// 声明附加信息输入框
	private EditText ET_specialMemo;
	protected String carmera_path;
	/*要保存的专业信息信息*/
	SpecialFieldInfo specialFieldInfo = new SpecialFieldInfo();
	/*专业信息管理业务逻辑层*/
	private SpecialFieldInfoService specialFieldInfoService = new SpecialFieldInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.specialfieldinfo_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加专业信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_specialFieldNumber = (EditText) findViewById(R.id.ET_specialFieldNumber);
		ET_specialFieldName = (EditText) findViewById(R.id.ET_specialFieldName);
		spinner_specialCollegeNumber = (Spinner) findViewById(R.id.Spinner_specialCollegeNumber);
		// 获取所有的所在学院
		try {
			collegeInfoList = collegeInfoService.QueryCollegeInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int collegeInfoCount = collegeInfoList.size();
		specialCollegeNumber_ShowText = new String[collegeInfoCount];
		for(int i=0;i<collegeInfoCount;i++) { 
			specialCollegeNumber_ShowText[i] = collegeInfoList.get(i).getCollegeName();
		}
		// 将可选内容与ArrayAdapter连接起来
		specialCollegeNumber_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, specialCollegeNumber_ShowText);
		// 设置下拉列表的风格
		specialCollegeNumber_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_specialCollegeNumber.setAdapter(specialCollegeNumber_adapter);
		// 添加事件Spinner事件监听
		spinner_specialCollegeNumber.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				specialFieldInfo.setSpecialCollegeNumber(collegeInfoList.get(arg2).getCollegeNumber()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_specialCollegeNumber.setVisibility(View.VISIBLE);
		dp_specialBirthDate = (DatePicker)this.findViewById(R.id.dp_specialBirthDate);
		ET_specialMan = (EditText) findViewById(R.id.ET_specialMan);
		ET_specialTelephone = (EditText) findViewById(R.id.ET_specialTelephone);
		ET_specialMemo = (EditText) findViewById(R.id.ET_specialMemo);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加专业信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取专业编号*/
					if(ET_specialFieldNumber.getText().toString().equals("")) {
						Toast.makeText(SpecialFieldInfoAddActivity.this, "专业编号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_specialFieldNumber.setFocusable(true);
						ET_specialFieldNumber.requestFocus();
						return;
					}
					specialFieldInfo.setSpecialFieldNumber(ET_specialFieldNumber.getText().toString());
					/*验证获取专业名称*/ 
					if(ET_specialFieldName.getText().toString().equals("")) {
						Toast.makeText(SpecialFieldInfoAddActivity.this, "专业名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_specialFieldName.setFocusable(true);
						ET_specialFieldName.requestFocus();
						return;	
					}
					specialFieldInfo.setSpecialFieldName(ET_specialFieldName.getText().toString());
					/*获取成立日期*/
					Date specialBirthDate = new Date(dp_specialBirthDate.getYear()-1900,dp_specialBirthDate.getMonth(),dp_specialBirthDate.getDayOfMonth());
					specialFieldInfo.setSpecialBirthDate(new Timestamp(specialBirthDate.getTime()));
					/*验证获取联系人*/ 
					if(ET_specialMan.getText().toString().equals("")) {
						Toast.makeText(SpecialFieldInfoAddActivity.this, "联系人输入不能为空!", Toast.LENGTH_LONG).show();
						ET_specialMan.setFocusable(true);
						ET_specialMan.requestFocus();
						return;	
					}
					specialFieldInfo.setSpecialMan(ET_specialMan.getText().toString());
					/*验证获取联系电话*/ 
					if(ET_specialTelephone.getText().toString().equals("")) {
						Toast.makeText(SpecialFieldInfoAddActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_specialTelephone.setFocusable(true);
						ET_specialTelephone.requestFocus();
						return;	
					}
					specialFieldInfo.setSpecialTelephone(ET_specialTelephone.getText().toString());
					/*验证获取附加信息*/ 
					if(ET_specialMemo.getText().toString().equals("")) {
						Toast.makeText(SpecialFieldInfoAddActivity.this, "附加信息输入不能为空!", Toast.LENGTH_LONG).show();
						ET_specialMemo.setFocusable(true);
						ET_specialMemo.requestFocus();
						return;	
					}
					specialFieldInfo.setSpecialMemo(ET_specialMemo.getText().toString());
					/*调用业务逻辑层上传专业信息信息*/
					SpecialFieldInfoAddActivity.this.setTitle("正在上传专业信息信息，稍等...");
					String result = specialFieldInfoService.AddSpecialFieldInfo(specialFieldInfo);
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
