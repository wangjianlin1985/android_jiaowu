package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.ClassInfo;
import com.mobileclient.service.ClassInfoService;
import com.mobileclient.domain.SpecialFieldInfo;
import com.mobileclient.service.SpecialFieldInfoService;
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
public class ClassInfoAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明班级编号输入框
	private EditText ET_classNumber;
	// 声明班级名称输入框
	private EditText ET_className;
	// 声明所属专业下拉框
	private Spinner spinner_classSpecialFieldNumber;
	private ArrayAdapter<String> classSpecialFieldNumber_adapter;
	private static  String[] classSpecialFieldNumber_ShowText  = null;
	private List<SpecialFieldInfo> specialFieldInfoList = null;
	/*所属专业管理业务逻辑层*/
	private SpecialFieldInfoService specialFieldInfoService = new SpecialFieldInfoService();
	// 出版成立日期控件
	private DatePicker dp_classBirthDate;
	// 声明班主任输入框
	private EditText ET_classTeacherCharge;
	// 声明联系电话输入框
	private EditText ET_classTelephone;
	// 声明附加信息输入框
	private EditText ET_classMemo;
	protected String carmera_path;
	/*要保存的班级信息信息*/
	ClassInfo classInfo = new ClassInfo();
	/*班级信息管理业务逻辑层*/
	private ClassInfoService classInfoService = new ClassInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.classinfo_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加班级信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_classNumber = (EditText) findViewById(R.id.ET_classNumber);
		ET_className = (EditText) findViewById(R.id.ET_className);
		spinner_classSpecialFieldNumber = (Spinner) findViewById(R.id.Spinner_classSpecialFieldNumber);
		// 获取所有的所属专业
		try {
			specialFieldInfoList = specialFieldInfoService.QuerySpecialFieldInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int specialFieldInfoCount = specialFieldInfoList.size();
		classSpecialFieldNumber_ShowText = new String[specialFieldInfoCount];
		for(int i=0;i<specialFieldInfoCount;i++) { 
			classSpecialFieldNumber_ShowText[i] = specialFieldInfoList.get(i).getSpecialFieldName();
		}
		// 将可选内容与ArrayAdapter连接起来
		classSpecialFieldNumber_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, classSpecialFieldNumber_ShowText);
		// 设置下拉列表的风格
		classSpecialFieldNumber_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_classSpecialFieldNumber.setAdapter(classSpecialFieldNumber_adapter);
		// 添加事件Spinner事件监听
		spinner_classSpecialFieldNumber.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				classInfo.setClassSpecialFieldNumber(specialFieldInfoList.get(arg2).getSpecialFieldNumber()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_classSpecialFieldNumber.setVisibility(View.VISIBLE);
		dp_classBirthDate = (DatePicker)this.findViewById(R.id.dp_classBirthDate);
		ET_classTeacherCharge = (EditText) findViewById(R.id.ET_classTeacherCharge);
		ET_classTelephone = (EditText) findViewById(R.id.ET_classTelephone);
		ET_classMemo = (EditText) findViewById(R.id.ET_classMemo);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加班级信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取班级编号*/
					if(ET_classNumber.getText().toString().equals("")) {
						Toast.makeText(ClassInfoAddActivity.this, "班级编号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_classNumber.setFocusable(true);
						ET_classNumber.requestFocus();
						return;
					}
					classInfo.setClassNumber(ET_classNumber.getText().toString());
					/*验证获取班级名称*/ 
					if(ET_className.getText().toString().equals("")) {
						Toast.makeText(ClassInfoAddActivity.this, "班级名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_className.setFocusable(true);
						ET_className.requestFocus();
						return;	
					}
					classInfo.setClassName(ET_className.getText().toString());
					/*获取成立日期*/
					Date classBirthDate = new Date(dp_classBirthDate.getYear()-1900,dp_classBirthDate.getMonth(),dp_classBirthDate.getDayOfMonth());
					classInfo.setClassBirthDate(new Timestamp(classBirthDate.getTime()));
					/*验证获取班主任*/ 
					if(ET_classTeacherCharge.getText().toString().equals("")) {
						Toast.makeText(ClassInfoAddActivity.this, "班主任输入不能为空!", Toast.LENGTH_LONG).show();
						ET_classTeacherCharge.setFocusable(true);
						ET_classTeacherCharge.requestFocus();
						return;	
					}
					classInfo.setClassTeacherCharge(ET_classTeacherCharge.getText().toString());
					/*验证获取联系电话*/ 
					if(ET_classTelephone.getText().toString().equals("")) {
						Toast.makeText(ClassInfoAddActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_classTelephone.setFocusable(true);
						ET_classTelephone.requestFocus();
						return;	
					}
					classInfo.setClassTelephone(ET_classTelephone.getText().toString());
					/*验证获取附加信息*/ 
					if(ET_classMemo.getText().toString().equals("")) {
						Toast.makeText(ClassInfoAddActivity.this, "附加信息输入不能为空!", Toast.LENGTH_LONG).show();
						ET_classMemo.setFocusable(true);
						ET_classMemo.requestFocus();
						return;	
					}
					classInfo.setClassMemo(ET_classMemo.getText().toString());
					/*调用业务逻辑层上传班级信息信息*/
					ClassInfoAddActivity.this.setTitle("正在上传班级信息信息，稍等...");
					String result = classInfoService.AddClassInfo(classInfo);
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
