package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
import com.mobileclient.domain.ClassInfo;
import com.mobileclient.service.ClassInfoService;
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
public class StudentAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明学号输入框
	private EditText ET_studentNumber;
	// 声明姓名输入框
	private EditText ET_studentName;
	// 声明登录密码输入框
	private EditText ET_studentPassword;
	// 声明性别输入框
	private EditText ET_studentSex;
	// 声明所在班级下拉框
	private Spinner spinner_studentClassNumber;
	private ArrayAdapter<String> studentClassNumber_adapter;
	private static  String[] studentClassNumber_ShowText  = null;
	private List<ClassInfo> classInfoList = null;
	/*所在班级管理业务逻辑层*/
	private ClassInfoService classInfoService = new ClassInfoService();
	// 出版出生日期控件
	private DatePicker dp_studentBirthday;
	// 声明政治面貌输入框
	private EditText ET_studentState;
	// 声明学生照片图片框控件
	private ImageView iv_studentPhoto;
	private Button btn_studentPhoto;
	protected int REQ_CODE_SELECT_IMAGE_studentPhoto = 1;
	private int REQ_CODE_CAMERA_studentPhoto = 2;
	// 声明联系电话输入框
	private EditText ET_studentTelephone;
	// 声明学生邮箱输入框
	private EditText ET_studentEmail;
	// 声明联系qq输入框
	private EditText ET_studentQQ;
	// 声明家庭地址输入框
	private EditText ET_studentAddress;
	// 声明附加信息输入框
	private EditText ET_studentMemo;
	protected String carmera_path;
	/*要保存的学生信息信息*/
	Student student = new Student();
	/*学生信息管理业务逻辑层*/
	private StudentService studentService = new StudentService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.student_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加学生信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_studentNumber = (EditText) findViewById(R.id.ET_studentNumber);
		ET_studentName = (EditText) findViewById(R.id.ET_studentName);
		ET_studentPassword = (EditText) findViewById(R.id.ET_studentPassword);
		ET_studentSex = (EditText) findViewById(R.id.ET_studentSex);
		spinner_studentClassNumber = (Spinner) findViewById(R.id.Spinner_studentClassNumber);
		// 获取所有的所在班级
		try {
			classInfoList = classInfoService.QueryClassInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int classInfoCount = classInfoList.size();
		studentClassNumber_ShowText = new String[classInfoCount];
		for(int i=0;i<classInfoCount;i++) { 
			studentClassNumber_ShowText[i] = classInfoList.get(i).getClassName();
		}
		// 将可选内容与ArrayAdapter连接起来
		studentClassNumber_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, studentClassNumber_ShowText);
		// 设置下拉列表的风格
		studentClassNumber_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_studentClassNumber.setAdapter(studentClassNumber_adapter);
		// 添加事件Spinner事件监听
		spinner_studentClassNumber.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				student.setStudentClassNumber(classInfoList.get(arg2).getClassNumber()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_studentClassNumber.setVisibility(View.VISIBLE);
		dp_studentBirthday = (DatePicker)this.findViewById(R.id.dp_studentBirthday);
		ET_studentState = (EditText) findViewById(R.id.ET_studentState);
		iv_studentPhoto = (ImageView) findViewById(R.id.iv_studentPhoto);
		/*单击图片显示控件时进行图片的选择*/
		iv_studentPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(StudentAddActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_studentPhoto);
			}
		});
		btn_studentPhoto = (Button) findViewById(R.id.btn_studentPhoto);
		btn_studentPhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_studentPhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_studentPhoto);  
			}
		});
		ET_studentTelephone = (EditText) findViewById(R.id.ET_studentTelephone);
		ET_studentEmail = (EditText) findViewById(R.id.ET_studentEmail);
		ET_studentQQ = (EditText) findViewById(R.id.ET_studentQQ);
		ET_studentAddress = (EditText) findViewById(R.id.ET_studentAddress);
		ET_studentMemo = (EditText) findViewById(R.id.ET_studentMemo);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加学生信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取学号*/
					if(ET_studentNumber.getText().toString().equals("")) {
						Toast.makeText(StudentAddActivity.this, "学号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_studentNumber.setFocusable(true);
						ET_studentNumber.requestFocus();
						return;
					}
					student.setStudentNumber(ET_studentNumber.getText().toString());
					/*验证获取姓名*/ 
					if(ET_studentName.getText().toString().equals("")) {
						Toast.makeText(StudentAddActivity.this, "姓名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_studentName.setFocusable(true);
						ET_studentName.requestFocus();
						return;	
					}
					student.setStudentName(ET_studentName.getText().toString());
					/*验证获取登录密码*/ 
					if(ET_studentPassword.getText().toString().equals("")) {
						Toast.makeText(StudentAddActivity.this, "登录密码输入不能为空!", Toast.LENGTH_LONG).show();
						ET_studentPassword.setFocusable(true);
						ET_studentPassword.requestFocus();
						return;	
					}
					student.setStudentPassword(ET_studentPassword.getText().toString());
					/*验证获取性别*/ 
					if(ET_studentSex.getText().toString().equals("")) {
						Toast.makeText(StudentAddActivity.this, "性别输入不能为空!", Toast.LENGTH_LONG).show();
						ET_studentSex.setFocusable(true);
						ET_studentSex.requestFocus();
						return;	
					}
					student.setStudentSex(ET_studentSex.getText().toString());
					/*获取出生日期*/
					Date studentBirthday = new Date(dp_studentBirthday.getYear()-1900,dp_studentBirthday.getMonth(),dp_studentBirthday.getDayOfMonth());
					student.setStudentBirthday(new Timestamp(studentBirthday.getTime()));
					/*验证获取政治面貌*/ 
					if(ET_studentState.getText().toString().equals("")) {
						Toast.makeText(StudentAddActivity.this, "政治面貌输入不能为空!", Toast.LENGTH_LONG).show();
						ET_studentState.setFocusable(true);
						ET_studentState.requestFocus();
						return;	
					}
					student.setStudentState(ET_studentState.getText().toString());
					if(student.getStudentPhoto() != null) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						StudentAddActivity.this.setTitle("正在上传图片，稍等...");
						String studentPhoto = HttpUtil.uploadFile(student.getStudentPhoto());
						StudentAddActivity.this.setTitle("图片上传完毕！");
						student.setStudentPhoto(studentPhoto);
					} else {
						student.setStudentPhoto("upload/noimage.jpg");
					}
					/*验证获取联系电话*/ 
					if(ET_studentTelephone.getText().toString().equals("")) {
						Toast.makeText(StudentAddActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_studentTelephone.setFocusable(true);
						ET_studentTelephone.requestFocus();
						return;	
					}
					student.setStudentTelephone(ET_studentTelephone.getText().toString());
					/*验证获取学生邮箱*/ 
					if(ET_studentEmail.getText().toString().equals("")) {
						Toast.makeText(StudentAddActivity.this, "学生邮箱输入不能为空!", Toast.LENGTH_LONG).show();
						ET_studentEmail.setFocusable(true);
						ET_studentEmail.requestFocus();
						return;	
					}
					student.setStudentEmail(ET_studentEmail.getText().toString());
					/*验证获取联系qq*/ 
					if(ET_studentQQ.getText().toString().equals("")) {
						Toast.makeText(StudentAddActivity.this, "联系qq输入不能为空!", Toast.LENGTH_LONG).show();
						ET_studentQQ.setFocusable(true);
						ET_studentQQ.requestFocus();
						return;	
					}
					student.setStudentQQ(ET_studentQQ.getText().toString());
					/*验证获取家庭地址*/ 
					if(ET_studentAddress.getText().toString().equals("")) {
						Toast.makeText(StudentAddActivity.this, "家庭地址输入不能为空!", Toast.LENGTH_LONG).show();
						ET_studentAddress.setFocusable(true);
						ET_studentAddress.requestFocus();
						return;	
					}
					student.setStudentAddress(ET_studentAddress.getText().toString());
					/*验证获取附加信息*/ 
					if(ET_studentMemo.getText().toString().equals("")) {
						Toast.makeText(StudentAddActivity.this, "附加信息输入不能为空!", Toast.LENGTH_LONG).show();
						ET_studentMemo.setFocusable(true);
						ET_studentMemo.requestFocus();
						return;	
					}
					student.setStudentMemo(ET_studentMemo.getText().toString());
					/*调用业务逻辑层上传学生信息信息*/
					StudentAddActivity.this.setTitle("正在上传学生信息信息，稍等...");
					String result = studentService.AddStudent(student);
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
		if (requestCode == REQ_CODE_CAMERA_studentPhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_studentPhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_studentPhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_studentPhoto.setImageBitmap(booImageBm);
				this.iv_studentPhoto.setScaleType(ScaleType.FIT_CENTER);
				this.student.setStudentPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_studentPhoto && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_studentPhoto.setImageBitmap(bm); 
				this.iv_studentPhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			student.setStudentPhoto(filename); 
		}
	}
}
