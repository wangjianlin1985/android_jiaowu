package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Teacher;
import com.mobileclient.service.TeacherService;
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
public class TeacherDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明教师编号控件
	private TextView TV_teacherNumber;
	// 声明教师姓名控件
	private TextView TV_teacherName;
	// 声明登录密码控件
	private TextView TV_teacherPassword;
	// 声明性别控件
	private TextView TV_teacherSex;
	// 声明出生日期控件
	private TextView TV_teacherBirthday;
	// 声明入职日期控件
	private TextView TV_teacherArriveDate;
	// 声明身份证号控件
	private TextView TV_teacherCardNumber;
	// 声明联系电话控件
	private TextView TV_teacherPhone;
	// 声明教师照片图片框
	private ImageView iv_teacherPhoto;
	// 声明家庭地址控件
	private TextView TV_teacherAddress;
	// 声明附加信息控件
	private TextView TV_teacherMemo;
	/* 要保存的教师信息信息 */
	Teacher teacher = new Teacher(); 
	/* 教师信息管理业务逻辑层 */
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
		setContentView(R.layout.teacher_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看教师信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_teacherNumber = (TextView) findViewById(R.id.TV_teacherNumber);
		TV_teacherName = (TextView) findViewById(R.id.TV_teacherName);
		TV_teacherPassword = (TextView) findViewById(R.id.TV_teacherPassword);
		TV_teacherSex = (TextView) findViewById(R.id.TV_teacherSex);
		TV_teacherBirthday = (TextView) findViewById(R.id.TV_teacherBirthday);
		TV_teacherArriveDate = (TextView) findViewById(R.id.TV_teacherArriveDate);
		TV_teacherCardNumber = (TextView) findViewById(R.id.TV_teacherCardNumber);
		TV_teacherPhone = (TextView) findViewById(R.id.TV_teacherPhone);
		iv_teacherPhoto = (ImageView) findViewById(R.id.iv_teacherPhoto); 
		TV_teacherAddress = (TextView) findViewById(R.id.TV_teacherAddress);
		TV_teacherMemo = (TextView) findViewById(R.id.TV_teacherMemo);
		Bundle extras = this.getIntent().getExtras();
		teacherNumber = extras.getString("teacherNumber");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				TeacherDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    teacher = teacherService.GetTeacher(teacherNumber); 
		this.TV_teacherNumber.setText(teacher.getTeacherNumber());
		this.TV_teacherName.setText(teacher.getTeacherName());
		this.TV_teacherPassword.setText(teacher.getTeacherPassword());
		this.TV_teacherSex.setText(teacher.getTeacherSex());
		Date teacherBirthday = new Date(teacher.getTeacherBirthday().getTime());
		String teacherBirthdayStr = (teacherBirthday.getYear() + 1900) + "-" + (teacherBirthday.getMonth()+1) + "-" + teacherBirthday.getDate();
		this.TV_teacherBirthday.setText(teacherBirthdayStr);
		Date teacherArriveDate = new Date(teacher.getTeacherArriveDate().getTime());
		String teacherArriveDateStr = (teacherArriveDate.getYear() + 1900) + "-" + (teacherArriveDate.getMonth()+1) + "-" + teacherArriveDate.getDate();
		this.TV_teacherArriveDate.setText(teacherArriveDateStr);
		this.TV_teacherCardNumber.setText(teacher.getTeacherCardNumber());
		this.TV_teacherPhone.setText(teacher.getTeacherPhone());
		byte[] teacherPhoto_data = null;
		try {
			// 获取图片数据
			teacherPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + teacher.getTeacherPhoto());
			Bitmap teacherPhoto = BitmapFactory.decodeByteArray(teacherPhoto_data, 0,teacherPhoto_data.length);
			this.iv_teacherPhoto.setImageBitmap(teacherPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_teacherAddress.setText(teacher.getTeacherAddress());
		this.TV_teacherMemo.setText(teacher.getTeacherMemo());
	} 
}
