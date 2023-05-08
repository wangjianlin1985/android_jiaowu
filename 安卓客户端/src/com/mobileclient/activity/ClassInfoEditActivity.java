package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class ClassInfoEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明班级编号TextView
	private TextView TV_classNumber;
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

	private String classNumber;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.classinfo_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑班级信息信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_classNumber = (TextView) findViewById(R.id.TV_classNumber);
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
		// 设置图书类别下拉列表的风格
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
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		classNumber = extras.getString("classNumber");
		/*单击修改班级信息按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取班级名称*/ 
					if(ET_className.getText().toString().equals("")) {
						Toast.makeText(ClassInfoEditActivity.this, "班级名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_className.setFocusable(true);
						ET_className.requestFocus();
						return;	
					}
					classInfo.setClassName(ET_className.getText().toString());
					/*获取出版日期*/
					Date classBirthDate = new Date(dp_classBirthDate.getYear()-1900,dp_classBirthDate.getMonth(),dp_classBirthDate.getDayOfMonth());
					classInfo.setClassBirthDate(new Timestamp(classBirthDate.getTime()));
					/*验证获取班主任*/ 
					if(ET_classTeacherCharge.getText().toString().equals("")) {
						Toast.makeText(ClassInfoEditActivity.this, "班主任输入不能为空!", Toast.LENGTH_LONG).show();
						ET_classTeacherCharge.setFocusable(true);
						ET_classTeacherCharge.requestFocus();
						return;	
					}
					classInfo.setClassTeacherCharge(ET_classTeacherCharge.getText().toString());
					/*验证获取联系电话*/ 
					if(ET_classTelephone.getText().toString().equals("")) {
						Toast.makeText(ClassInfoEditActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_classTelephone.setFocusable(true);
						ET_classTelephone.requestFocus();
						return;	
					}
					classInfo.setClassTelephone(ET_classTelephone.getText().toString());
					/*验证获取附加信息*/ 
					if(ET_classMemo.getText().toString().equals("")) {
						Toast.makeText(ClassInfoEditActivity.this, "附加信息输入不能为空!", Toast.LENGTH_LONG).show();
						ET_classMemo.setFocusable(true);
						ET_classMemo.requestFocus();
						return;	
					}
					classInfo.setClassMemo(ET_classMemo.getText().toString());
					/*调用业务逻辑层上传班级信息信息*/
					ClassInfoEditActivity.this.setTitle("正在更新班级信息信息，稍等...");
					String result = classInfoService.UpdateClassInfo(classInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    classInfo = classInfoService.GetClassInfo(classNumber);
		this.TV_classNumber.setText(classNumber);
		this.ET_className.setText(classInfo.getClassName());
		for (int i = 0; i < specialFieldInfoList.size(); i++) {
			if (classInfo.getClassSpecialFieldNumber().equals(specialFieldInfoList.get(i).getSpecialFieldNumber())) {
				this.spinner_classSpecialFieldNumber.setSelection(i);
				break;
			}
		}
		Date classBirthDate = new Date(classInfo.getClassBirthDate().getTime());
		this.dp_classBirthDate.init(classBirthDate.getYear() + 1900,classBirthDate.getMonth(), classBirthDate.getDate(), null);
		this.ET_classTeacherCharge.setText(classInfo.getClassTeacherCharge());
		this.ET_classTelephone.setText(classInfo.getClassTelephone());
		this.ET_classMemo.setText(classInfo.getClassMemo());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
