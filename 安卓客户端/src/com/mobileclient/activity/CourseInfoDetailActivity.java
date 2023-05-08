package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.CourseInfo;
import com.mobileclient.service.CourseInfoService;
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
public class CourseInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明课程编号控件
	private TextView TV_courseNumber;
	// 声明课程名称控件
	private TextView TV_courseName;
	// 声明上课老师控件
	private TextView TV_courseTeacher;
	// 声明上课时间控件
	private TextView TV_courseTime;
	// 声明上课地点控件
	private TextView TV_coursePlace;
	// 声明课程学分控件
	private TextView TV_courseScore;
	// 声明附加信息控件
	private TextView TV_courseMemo;
	/* 要保存的课程信息信息 */
	CourseInfo courseInfo = new CourseInfo(); 
	/* 课程信息管理业务逻辑层 */
	private CourseInfoService courseInfoService = new CourseInfoService();
	private TeacherService teacherService = new TeacherService();
	private String courseNumber;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.courseinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看课程信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_courseNumber = (TextView) findViewById(R.id.TV_courseNumber);
		TV_courseName = (TextView) findViewById(R.id.TV_courseName);
		TV_courseTeacher = (TextView) findViewById(R.id.TV_courseTeacher);
		TV_courseTime = (TextView) findViewById(R.id.TV_courseTime);
		TV_coursePlace = (TextView) findViewById(R.id.TV_coursePlace);
		TV_courseScore = (TextView) findViewById(R.id.TV_courseScore);
		TV_courseMemo = (TextView) findViewById(R.id.TV_courseMemo);
		Bundle extras = this.getIntent().getExtras();
		courseNumber = extras.getString("courseNumber");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CourseInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    courseInfo = courseInfoService.GetCourseInfo(courseNumber); 
		this.TV_courseNumber.setText(courseInfo.getCourseNumber());
		this.TV_courseName.setText(courseInfo.getCourseName());
		Teacher courseTeacher = teacherService.GetTeacher(courseInfo.getCourseTeacher());
		this.TV_courseTeacher.setText(courseTeacher.getTeacherName());
		this.TV_courseTime.setText(courseInfo.getCourseTime());
		this.TV_coursePlace.setText(courseInfo.getCoursePlace());
		this.TV_courseScore.setText(courseInfo.getCourseScore() + "");
		this.TV_courseMemo.setText(courseInfo.getCourseMemo());
	} 
}
