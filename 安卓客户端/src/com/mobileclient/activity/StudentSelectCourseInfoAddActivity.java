package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.StudentSelectCourseInfo;
import com.mobileclient.service.StudentSelectCourseInfoService;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
import com.mobileclient.domain.CourseInfo;
import com.mobileclient.service.CourseInfoService;
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
public class StudentSelectCourseInfoAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明学生对象下拉框
	private Spinner spinner_studentNumber;
	private ArrayAdapter<String> studentNumber_adapter;
	private static  String[] studentNumber_ShowText  = null;
	private List<Student> studentList = null;
	/*学生对象管理业务逻辑层*/
	private StudentService studentService = new StudentService();
	// 声明课程对象下拉框
	private Spinner spinner_courseNumber;
	private ArrayAdapter<String> courseNumber_adapter;
	private static  String[] courseNumber_ShowText  = null;
	private List<CourseInfo> courseInfoList = null;
	/*课程对象管理业务逻辑层*/
	private CourseInfoService courseInfoService = new CourseInfoService();
	protected String carmera_path;
	/*要保存的选课信息信息*/
	StudentSelectCourseInfo studentSelectCourseInfo = new StudentSelectCourseInfo();
	/*选课信息管理业务逻辑层*/
	private StudentSelectCourseInfoService studentSelectCourseInfoService = new StudentSelectCourseInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.studentselectcourseinfo_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加选课信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		spinner_studentNumber = (Spinner) findViewById(R.id.Spinner_studentNumber);
		// 获取所有的学生对象
		try {
			studentList = studentService.QueryStudent(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int studentCount = studentList.size();
		studentNumber_ShowText = new String[studentCount];
		for(int i=0;i<studentCount;i++) { 
			studentNumber_ShowText[i] = studentList.get(i).getStudentName();
		}
		// 将可选内容与ArrayAdapter连接起来
		studentNumber_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, studentNumber_ShowText);
		// 设置下拉列表的风格
		studentNumber_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_studentNumber.setAdapter(studentNumber_adapter);
		// 添加事件Spinner事件监听
		spinner_studentNumber.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				studentSelectCourseInfo.setStudentNumber(studentList.get(arg2).getStudentNumber()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_studentNumber.setVisibility(View.VISIBLE);
		spinner_courseNumber = (Spinner) findViewById(R.id.Spinner_courseNumber);
		// 获取所有的课程对象
		try {
			courseInfoList = courseInfoService.QueryCourseInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int courseInfoCount = courseInfoList.size();
		courseNumber_ShowText = new String[courseInfoCount];
		for(int i=0;i<courseInfoCount;i++) { 
			courseNumber_ShowText[i] = courseInfoList.get(i).getCourseName();
		}
		// 将可选内容与ArrayAdapter连接起来
		courseNumber_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, courseNumber_ShowText);
		// 设置下拉列表的风格
		courseNumber_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_courseNumber.setAdapter(courseNumber_adapter);
		// 添加事件Spinner事件监听
		spinner_courseNumber.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				studentSelectCourseInfo.setCourseNumber(courseInfoList.get(arg2).getCourseNumber()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_courseNumber.setVisibility(View.VISIBLE);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加选课信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*调用业务逻辑层上传选课信息信息*/
					StudentSelectCourseInfoAddActivity.this.setTitle("正在上传选课信息信息，稍等...");
					String result = studentSelectCourseInfoService.AddStudentSelectCourseInfo(studentSelectCourseInfo);
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
