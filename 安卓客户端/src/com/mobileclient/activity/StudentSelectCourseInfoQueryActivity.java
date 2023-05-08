package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.StudentSelectCourseInfo;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
import com.mobileclient.domain.CourseInfo;
import com.mobileclient.service.CourseInfoService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class StudentSelectCourseInfoQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明学生对象下拉框
	private Spinner spinner_studentNumber;
	private ArrayAdapter<String> studentNumber_adapter;
	private static  String[] studentNumber_ShowText  = null;
	private List<Student> studentList = null; 
	/*学生信息管理业务逻辑层*/
	private StudentService studentService = new StudentService();
	// 声明课程对象下拉框
	private Spinner spinner_courseNumber;
	private ArrayAdapter<String> courseNumber_adapter;
	private static  String[] courseNumber_ShowText  = null;
	private List<CourseInfo> courseInfoList = null; 
	/*课程信息管理业务逻辑层*/
	private CourseInfoService courseInfoService = new CourseInfoService();
	/*查询过滤条件保存到这个对象中*/
	private StudentSelectCourseInfo queryConditionStudentSelectCourseInfo = new StudentSelectCourseInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.studentselectcourseinfo_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置选课信息查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_studentNumber = (Spinner) findViewById(R.id.Spinner_studentNumber);
		// 获取所有的学生信息
		try {
			studentList = studentService.QueryStudent(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int studentCount = studentList.size();
		studentNumber_ShowText = new String[studentCount+1];
		studentNumber_ShowText[0] = "不限制";
		for(int i=1;i<=studentCount;i++) { 
			studentNumber_ShowText[i] = studentList.get(i-1).getStudentName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		studentNumber_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, studentNumber_ShowText);
		// 设置学生对象下拉列表的风格
		studentNumber_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_studentNumber.setAdapter(studentNumber_adapter);
		// 添加事件Spinner事件监听
		spinner_studentNumber.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionStudentSelectCourseInfo.setStudentNumber(studentList.get(arg2-1).getStudentNumber()); 
				else
					queryConditionStudentSelectCourseInfo.setStudentNumber("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_studentNumber.setVisibility(View.VISIBLE);
		spinner_courseNumber = (Spinner) findViewById(R.id.Spinner_courseNumber);
		// 获取所有的课程信息
		try {
			courseInfoList = courseInfoService.QueryCourseInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int courseInfoCount = courseInfoList.size();
		courseNumber_ShowText = new String[courseInfoCount+1];
		courseNumber_ShowText[0] = "不限制";
		for(int i=1;i<=courseInfoCount;i++) { 
			courseNumber_ShowText[i] = courseInfoList.get(i-1).getCourseName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		courseNumber_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, courseNumber_ShowText);
		// 设置课程对象下拉列表的风格
		courseNumber_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_courseNumber.setAdapter(courseNumber_adapter);
		// 添加事件Spinner事件监听
		spinner_courseNumber.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionStudentSelectCourseInfo.setCourseNumber(courseInfoList.get(arg2-1).getCourseNumber()); 
				else
					queryConditionStudentSelectCourseInfo.setCourseNumber("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_courseNumber.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionStudentSelectCourseInfo", queryConditionStudentSelectCourseInfo);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
