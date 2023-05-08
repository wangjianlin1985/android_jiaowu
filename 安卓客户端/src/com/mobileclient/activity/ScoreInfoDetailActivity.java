package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.ScoreInfo;
import com.mobileclient.service.ScoreInfoService;
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
public class ScoreInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_scoreId;
	// 声明学生对象控件
	private TextView TV_studentNumber;
	// 声明课程对象控件
	private TextView TV_courseNumber;
	// 声明成绩得分控件
	private TextView TV_scoreValue;
	// 声明学生评价控件
	private TextView TV_studentEvaluate;
	/* 要保存的成绩信息信息 */
	ScoreInfo scoreInfo = new ScoreInfo(); 
	/* 成绩信息管理业务逻辑层 */
	private ScoreInfoService scoreInfoService = new ScoreInfoService();
	private StudentService studentService = new StudentService();
	private CourseInfoService courseInfoService = new CourseInfoService();
	private int scoreId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.scoreinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看成绩信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_scoreId = (TextView) findViewById(R.id.TV_scoreId);
		TV_studentNumber = (TextView) findViewById(R.id.TV_studentNumber);
		TV_courseNumber = (TextView) findViewById(R.id.TV_courseNumber);
		TV_scoreValue = (TextView) findViewById(R.id.TV_scoreValue);
		TV_studentEvaluate = (TextView) findViewById(R.id.TV_studentEvaluate);
		Bundle extras = this.getIntent().getExtras();
		scoreId = extras.getInt("scoreId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ScoreInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    scoreInfo = scoreInfoService.GetScoreInfo(scoreId); 
		this.TV_scoreId.setText(scoreInfo.getScoreId() + "");
		Student studentNumber = studentService.GetStudent(scoreInfo.getStudentNumber());
		this.TV_studentNumber.setText(studentNumber.getStudentName());
		CourseInfo courseNumber = courseInfoService.GetCourseInfo(scoreInfo.getCourseNumber());
		this.TV_courseNumber.setText(courseNumber.getCourseName());
		this.TV_scoreValue.setText(scoreInfo.getScoreValue() + "");
		this.TV_studentEvaluate.setText(scoreInfo.getStudentEvaluate());
	} 
}
