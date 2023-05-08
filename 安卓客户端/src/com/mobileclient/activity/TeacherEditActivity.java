package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Teacher;
import com.mobileclient.service.TeacherService;
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

public class TeacherEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明教师编号TextView
	private TextView TV_teacherNumber;
	// 声明教师姓名输入框
	private EditText ET_teacherName;
	// 声明登录密码输入框
	private EditText ET_teacherPassword;
	// 声明性别输入框
	private EditText ET_teacherSex;
	// 出版出生日期控件
	private DatePicker dp_teacherBirthday;
	// 出版入职日期控件
	private DatePicker dp_teacherArriveDate;
	// 声明身份证号输入框
	private EditText ET_teacherCardNumber;
	// 声明联系电话输入框
	private EditText ET_teacherPhone;
	// 声明教师照片图片框控件
	private ImageView iv_teacherPhoto;
	private Button btn_teacherPhoto;
	protected int REQ_CODE_SELECT_IMAGE_teacherPhoto = 1;
	private int REQ_CODE_CAMERA_teacherPhoto = 2;
	// 声明家庭地址输入框
	private EditText ET_teacherAddress;
	// 声明附加信息输入框
	private EditText ET_teacherMemo;
	protected String carmera_path;
	/*要保存的教师信息信息*/
	Teacher teacher = new Teacher();
	/*教师信息管理业务逻辑层*/
	private TeacherService teacherService = new TeacherService();

	private String teacherNumber;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.teacher_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑教师信息信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_teacherNumber = (TextView) findViewById(R.id.TV_teacherNumber);
		ET_teacherName = (EditText) findViewById(R.id.ET_teacherName);
		ET_teacherPassword = (EditText) findViewById(R.id.ET_teacherPassword);
		ET_teacherSex = (EditText) findViewById(R.id.ET_teacherSex);
		dp_teacherBirthday = (DatePicker)this.findViewById(R.id.dp_teacherBirthday);
		dp_teacherArriveDate = (DatePicker)this.findViewById(R.id.dp_teacherArriveDate);
		ET_teacherCardNumber = (EditText) findViewById(R.id.ET_teacherCardNumber);
		ET_teacherPhone = (EditText) findViewById(R.id.ET_teacherPhone);
		iv_teacherPhoto = (ImageView) findViewById(R.id.iv_teacherPhoto);
		/*单击图片显示控件时进行图片的选择*/
		iv_teacherPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(TeacherEditActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_teacherPhoto);
			}
		});
		btn_teacherPhoto = (Button) findViewById(R.id.btn_teacherPhoto);
		btn_teacherPhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_teacherPhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_teacherPhoto);  
			}
		});
		ET_teacherAddress = (EditText) findViewById(R.id.ET_teacherAddress);
		ET_teacherMemo = (EditText) findViewById(R.id.ET_teacherMemo);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		teacherNumber = extras.getString("teacherNumber");
		/*单击修改教师信息按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取教师姓名*/ 
					if(ET_teacherName.getText().toString().equals("")) {
						Toast.makeText(TeacherEditActivity.this, "教师姓名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_teacherName.setFocusable(true);
						ET_teacherName.requestFocus();
						return;	
					}
					teacher.setTeacherName(ET_teacherName.getText().toString());
					/*验证获取登录密码*/ 
					if(ET_teacherPassword.getText().toString().equals("")) {
						Toast.makeText(TeacherEditActivity.this, "登录密码输入不能为空!", Toast.LENGTH_LONG).show();
						ET_teacherPassword.setFocusable(true);
						ET_teacherPassword.requestFocus();
						return;	
					}
					teacher.setTeacherPassword(ET_teacherPassword.getText().toString());
					/*验证获取性别*/ 
					if(ET_teacherSex.getText().toString().equals("")) {
						Toast.makeText(TeacherEditActivity.this, "性别输入不能为空!", Toast.LENGTH_LONG).show();
						ET_teacherSex.setFocusable(true);
						ET_teacherSex.requestFocus();
						return;	
					}
					teacher.setTeacherSex(ET_teacherSex.getText().toString());
					/*获取出版日期*/
					Date teacherBirthday = new Date(dp_teacherBirthday.getYear()-1900,dp_teacherBirthday.getMonth(),dp_teacherBirthday.getDayOfMonth());
					teacher.setTeacherBirthday(new Timestamp(teacherBirthday.getTime()));
					/*获取出版日期*/
					Date teacherArriveDate = new Date(dp_teacherArriveDate.getYear()-1900,dp_teacherArriveDate.getMonth(),dp_teacherArriveDate.getDayOfMonth());
					teacher.setTeacherArriveDate(new Timestamp(teacherArriveDate.getTime()));
					/*验证获取身份证号*/ 
					if(ET_teacherCardNumber.getText().toString().equals("")) {
						Toast.makeText(TeacherEditActivity.this, "身份证号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_teacherCardNumber.setFocusable(true);
						ET_teacherCardNumber.requestFocus();
						return;	
					}
					teacher.setTeacherCardNumber(ET_teacherCardNumber.getText().toString());
					/*验证获取联系电话*/ 
					if(ET_teacherPhone.getText().toString().equals("")) {
						Toast.makeText(TeacherEditActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_teacherPhone.setFocusable(true);
						ET_teacherPhone.requestFocus();
						return;	
					}
					teacher.setTeacherPhone(ET_teacherPhone.getText().toString());
					if (!teacher.getTeacherPhoto().startsWith("upload/")) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						TeacherEditActivity.this.setTitle("正在上传图片，稍等...");
						String teacherPhoto = HttpUtil.uploadFile(teacher.getTeacherPhoto());
						TeacherEditActivity.this.setTitle("图片上传完毕！");
						teacher.setTeacherPhoto(teacherPhoto);
					} 
					/*验证获取家庭地址*/ 
					if(ET_teacherAddress.getText().toString().equals("")) {
						Toast.makeText(TeacherEditActivity.this, "家庭地址输入不能为空!", Toast.LENGTH_LONG).show();
						ET_teacherAddress.setFocusable(true);
						ET_teacherAddress.requestFocus();
						return;	
					}
					teacher.setTeacherAddress(ET_teacherAddress.getText().toString());
					/*验证获取附加信息*/ 
					if(ET_teacherMemo.getText().toString().equals("")) {
						Toast.makeText(TeacherEditActivity.this, "附加信息输入不能为空!", Toast.LENGTH_LONG).show();
						ET_teacherMemo.setFocusable(true);
						ET_teacherMemo.requestFocus();
						return;	
					}
					teacher.setTeacherMemo(ET_teacherMemo.getText().toString());
					/*调用业务逻辑层上传教师信息信息*/
					TeacherEditActivity.this.setTitle("正在更新教师信息信息，稍等...");
					String result = teacherService.UpdateTeacher(teacher);
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
	    teacher = teacherService.GetTeacher(teacherNumber);
		this.TV_teacherNumber.setText(teacherNumber);
		this.ET_teacherName.setText(teacher.getTeacherName());
		this.ET_teacherPassword.setText(teacher.getTeacherPassword());
		this.ET_teacherSex.setText(teacher.getTeacherSex());
		Date teacherBirthday = new Date(teacher.getTeacherBirthday().getTime());
		this.dp_teacherBirthday.init(teacherBirthday.getYear() + 1900,teacherBirthday.getMonth(), teacherBirthday.getDate(), null);
		Date teacherArriveDate = new Date(teacher.getTeacherArriveDate().getTime());
		this.dp_teacherArriveDate.init(teacherArriveDate.getYear() + 1900,teacherArriveDate.getMonth(), teacherArriveDate.getDate(), null);
		this.ET_teacherCardNumber.setText(teacher.getTeacherCardNumber());
		this.ET_teacherPhone.setText(teacher.getTeacherPhone());
		byte[] teacherPhoto_data = null;
		try {
			// 获取图片数据
			teacherPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + teacher.getTeacherPhoto());
			Bitmap teacherPhoto = BitmapFactory.decodeByteArray(teacherPhoto_data, 0, teacherPhoto_data.length);
			this.iv_teacherPhoto.setImageBitmap(teacherPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.ET_teacherAddress.setText(teacher.getTeacherAddress());
		this.ET_teacherMemo.setText(teacher.getTeacherMemo());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_teacherPhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_teacherPhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_teacherPhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_teacherPhoto.setImageBitmap(booImageBm);
				this.iv_teacherPhoto.setScaleType(ScaleType.FIT_CENTER);
				this.teacher.setTeacherPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_teacherPhoto && resultCode == Activity.RESULT_OK) {
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
				this.iv_teacherPhoto.setImageBitmap(bm); 
				this.iv_teacherPhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			teacher.setTeacherPhoto(filename); 
		}
	}
}
