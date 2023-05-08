package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.StudentSelectCourseInfo;
import com.mobileclient.service.StudentSelectCourseInfoService;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
import com.mobileclient.domain.CourseInfo;
import com.mobileclient.service.CourseInfoService;
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
public class StudentSelectCourseInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_selectId;
	// 声明学生对象控件
	private TextView TV_studentNumber;
	// 声明课程对象控件
	private TextView TV_courseNumber;
	/* 要保存的选课信息信息 */
	StudentSelectCourseInfo studentSelectCourseInfo = new StudentSelectCourseInfo(); 
	/* 选课信息管理业务逻辑层 */
	private StudentSelectCourseInfoService studentSelectCourseInfoService = new StudentSelectCourseInfoService();
	private StudentService studentService = new StudentService();
	private CourseInfoService courseInfoService = new CourseInfoService();
	private int selectId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.studentselectcourseinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看选课信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_selectId = (TextView) findViewById(R.id.TV_selectId);
		TV_studentNumber = (TextView) findViewById(R.id.TV_studentNumber);
		TV_courseNumber = (TextView) findViewById(R.id.TV_courseNumber);
		Bundle extras = this.getIntent().getExtras();
		selectId = extras.getInt("selectId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				StudentSelectCourseInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    studentSelectCourseInfo = studentSelectCourseInfoService.GetStudentSelectCourseInfo(selectId); 
		this.TV_selectId.setText(studentSelectCourseInfo.getSelectId() + "");
		Student studentNumber = studentService.GetStudent(studentSelectCourseInfo.getStudentNumber());
		this.TV_studentNumber.setText(studentNumber.getStudentName());
		CourseInfo courseNumber = courseInfoService.GetCourseInfo(studentSelectCourseInfo.getCourseNumber());
		this.TV_courseNumber.setText(courseNumber.getCourseName());
	} 
}
