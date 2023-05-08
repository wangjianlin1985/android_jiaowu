package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.ScoreInfo;
import com.mobileclient.service.ScoreInfoService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class ScoreInfoEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明记录编号TextView
	private TextView TV_scoreId;
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
	// 声明成绩得分输入框
	private EditText ET_scoreValue;
	// 声明学生评价输入框
	private EditText ET_studentEvaluate;
	protected String carmera_path;
	/*要保存的成绩信息信息*/
	ScoreInfo scoreInfo = new ScoreInfo();
	/*成绩信息管理业务逻辑层*/
	private ScoreInfoService scoreInfoService = new ScoreInfoService();

	private int scoreId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.scoreinfo_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑成绩信息信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_scoreId = (TextView) findViewById(R.id.TV_scoreId);
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
		// 设置图书类别下拉列表的风格
		studentNumber_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_studentNumber.setAdapter(studentNumber_adapter);
		// 添加事件Spinner事件监听
		spinner_studentNumber.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				scoreInfo.setStudentNumber(studentList.get(arg2).getStudentNumber()); 
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
		// 设置图书类别下拉列表的风格
		courseNumber_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_courseNumber.setAdapter(courseNumber_adapter);
		// 添加事件Spinner事件监听
		spinner_courseNumber.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				scoreInfo.setCourseNumber(courseInfoList.get(arg2).getCourseNumber()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_courseNumber.setVisibility(View.VISIBLE);
		ET_scoreValue = (EditText) findViewById(R.id.ET_scoreValue);
		ET_studentEvaluate = (EditText) findViewById(R.id.ET_studentEvaluate);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		scoreId = extras.getInt("scoreId");
		/*单击修改成绩信息按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取成绩得分*/ 
					if(ET_scoreValue.getText().toString().equals("")) {
						Toast.makeText(ScoreInfoEditActivity.this, "成绩得分输入不能为空!", Toast.LENGTH_LONG).show();
						ET_scoreValue.setFocusable(true);
						ET_scoreValue.requestFocus();
						return;	
					}
					scoreInfo.setScoreValue(Float.parseFloat(ET_scoreValue.getText().toString()));
					/*验证获取学生评价*/ 
					if(ET_studentEvaluate.getText().toString().equals("")) {
						Toast.makeText(ScoreInfoEditActivity.this, "学生评价输入不能为空!", Toast.LENGTH_LONG).show();
						ET_studentEvaluate.setFocusable(true);
						ET_studentEvaluate.requestFocus();
						return;	
					}
					scoreInfo.setStudentEvaluate(ET_studentEvaluate.getText().toString());
					/*调用业务逻辑层上传成绩信息信息*/
					ScoreInfoEditActivity.this.setTitle("正在更新成绩信息信息，稍等...");
					String result = scoreInfoService.UpdateScoreInfo(scoreInfo);
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
	    scoreInfo = scoreInfoService.GetScoreInfo(scoreId);
		this.TV_scoreId.setText(scoreId+"");
		for (int i = 0; i < studentList.size(); i++) {
			if (scoreInfo.getStudentNumber().equals(studentList.get(i).getStudentNumber())) {
				this.spinner_studentNumber.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < courseInfoList.size(); i++) {
			if (scoreInfo.getCourseNumber().equals(courseInfoList.get(i).getCourseNumber())) {
				this.spinner_courseNumber.setSelection(i);
				break;
			}
		}
		this.ET_scoreValue.setText(scoreInfo.getScoreValue() + "");
		this.ET_studentEvaluate.setText(scoreInfo.getStudentEvaluate());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
