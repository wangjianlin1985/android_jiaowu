package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.CourseInfo;
import com.mobileclient.service.CourseInfoService;
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

public class CourseInfoEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明课程编号TextView
	private TextView TV_courseNumber;
	// 声明课程名称输入框
	private EditText ET_courseName;
	// 声明上课老师下拉框
	private Spinner spinner_courseTeacher;
	private ArrayAdapter<String> courseTeacher_adapter;
	private static  String[] courseTeacher_ShowText  = null;
	private List<Teacher> teacherList = null;
	/*上课老师管理业务逻辑层*/
	private TeacherService teacherService = new TeacherService();
	// 声明上课时间输入框
	private EditText ET_courseTime;
	// 声明上课地点输入框
	private EditText ET_coursePlace;
	// 声明课程学分输入框
	private EditText ET_courseScore;
	// 声明附加信息输入框
	private EditText ET_courseMemo;
	protected String carmera_path;
	/*要保存的课程信息信息*/
	CourseInfo courseInfo = new CourseInfo();
	/*课程信息管理业务逻辑层*/
	private CourseInfoService courseInfoService = new CourseInfoService();

	private String courseNumber;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.courseinfo_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑课程信息信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_courseNumber = (TextView) findViewById(R.id.TV_courseNumber);
		ET_courseName = (EditText) findViewById(R.id.ET_courseName);
		spinner_courseTeacher = (Spinner) findViewById(R.id.Spinner_courseTeacher);
		// 获取所有的上课老师
		try {
			teacherList = teacherService.QueryTeacher(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int teacherCount = teacherList.size();
		courseTeacher_ShowText = new String[teacherCount];
		for(int i=0;i<teacherCount;i++) { 
			courseTeacher_ShowText[i] = teacherList.get(i).getTeacherName();
		}
		// 将可选内容与ArrayAdapter连接起来
		courseTeacher_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, courseTeacher_ShowText);
		// 设置图书类别下拉列表的风格
		courseTeacher_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_courseTeacher.setAdapter(courseTeacher_adapter);
		// 添加事件Spinner事件监听
		spinner_courseTeacher.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				courseInfo.setCourseTeacher(teacherList.get(arg2).getTeacherNumber()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_courseTeacher.setVisibility(View.VISIBLE);
		ET_courseTime = (EditText) findViewById(R.id.ET_courseTime);
		ET_coursePlace = (EditText) findViewById(R.id.ET_coursePlace);
		ET_courseScore = (EditText) findViewById(R.id.ET_courseScore);
		ET_courseMemo = (EditText) findViewById(R.id.ET_courseMemo);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		courseNumber = extras.getString("courseNumber");
		/*单击修改课程信息按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取课程名称*/ 
					if(ET_courseName.getText().toString().equals("")) {
						Toast.makeText(CourseInfoEditActivity.this, "课程名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_courseName.setFocusable(true);
						ET_courseName.requestFocus();
						return;	
					}
					courseInfo.setCourseName(ET_courseName.getText().toString());
					/*验证获取上课时间*/ 
					if(ET_courseTime.getText().toString().equals("")) {
						Toast.makeText(CourseInfoEditActivity.this, "上课时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_courseTime.setFocusable(true);
						ET_courseTime.requestFocus();
						return;	
					}
					courseInfo.setCourseTime(ET_courseTime.getText().toString());
					/*验证获取上课地点*/ 
					if(ET_coursePlace.getText().toString().equals("")) {
						Toast.makeText(CourseInfoEditActivity.this, "上课地点输入不能为空!", Toast.LENGTH_LONG).show();
						ET_coursePlace.setFocusable(true);
						ET_coursePlace.requestFocus();
						return;	
					}
					courseInfo.setCoursePlace(ET_coursePlace.getText().toString());
					/*验证获取课程学分*/ 
					if(ET_courseScore.getText().toString().equals("")) {
						Toast.makeText(CourseInfoEditActivity.this, "课程学分输入不能为空!", Toast.LENGTH_LONG).show();
						ET_courseScore.setFocusable(true);
						ET_courseScore.requestFocus();
						return;	
					}
					courseInfo.setCourseScore(Float.parseFloat(ET_courseScore.getText().toString()));
					/*验证获取附加信息*/ 
					if(ET_courseMemo.getText().toString().equals("")) {
						Toast.makeText(CourseInfoEditActivity.this, "附加信息输入不能为空!", Toast.LENGTH_LONG).show();
						ET_courseMemo.setFocusable(true);
						ET_courseMemo.requestFocus();
						return;	
					}
					courseInfo.setCourseMemo(ET_courseMemo.getText().toString());
					/*调用业务逻辑层上传课程信息信息*/
					CourseInfoEditActivity.this.setTitle("正在更新课程信息信息，稍等...");
					String result = courseInfoService.UpdateCourseInfo(courseInfo);
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
	    courseInfo = courseInfoService.GetCourseInfo(courseNumber);
		this.TV_courseNumber.setText(courseNumber);
		this.ET_courseName.setText(courseInfo.getCourseName());
		for (int i = 0; i < teacherList.size(); i++) {
			if (courseInfo.getCourseTeacher().equals(teacherList.get(i).getTeacherNumber())) {
				this.spinner_courseTeacher.setSelection(i);
				break;
			}
		}
		this.ET_courseTime.setText(courseInfo.getCourseTime());
		this.ET_coursePlace.setText(courseInfo.getCoursePlace());
		this.ET_courseScore.setText(courseInfo.getCourseScore() + "");
		this.ET_courseMemo.setText(courseInfo.getCourseMemo());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
