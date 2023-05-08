package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Teacher;

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
public class TeacherQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明教师编号输入框
	private EditText ET_teacherNumber;
	// 声明教师姓名输入框
	private EditText ET_teacherName;
	// 出生日期控件
	private DatePicker dp_teacherBirthday;
	private CheckBox cb_teacherBirthday;
	// 入职日期控件
	private DatePicker dp_teacherArriveDate;
	private CheckBox cb_teacherArriveDate;
	/*查询过滤条件保存到这个对象中*/
	private Teacher queryConditionTeacher = new Teacher();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.teacher_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置教师信息查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_teacherNumber = (EditText) findViewById(R.id.ET_teacherNumber);
		ET_teacherName = (EditText) findViewById(R.id.ET_teacherName);
		dp_teacherBirthday = (DatePicker) findViewById(R.id.dp_teacherBirthday);
		cb_teacherBirthday = (CheckBox) findViewById(R.id.cb_teacherBirthday);
		dp_teacherArriveDate = (DatePicker) findViewById(R.id.dp_teacherArriveDate);
		cb_teacherArriveDate = (CheckBox) findViewById(R.id.cb_teacherArriveDate);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionTeacher.setTeacherNumber(ET_teacherNumber.getText().toString());
					queryConditionTeacher.setTeacherName(ET_teacherName.getText().toString());
					if(cb_teacherBirthday.isChecked()) {
						/*获取出生日期*/
						Date teacherBirthday = new Date(dp_teacherBirthday.getYear()-1900,dp_teacherBirthday.getMonth(),dp_teacherBirthday.getDayOfMonth());
						queryConditionTeacher.setTeacherBirthday(new Timestamp(teacherBirthday.getTime()));
					} else {
						queryConditionTeacher.setTeacherBirthday(null);
					} 
					if(cb_teacherArriveDate.isChecked()) {
						/*获取入职日期*/
						Date teacherArriveDate = new Date(dp_teacherArriveDate.getYear()-1900,dp_teacherArriveDate.getMonth(),dp_teacherArriveDate.getDayOfMonth());
						queryConditionTeacher.setTeacherArriveDate(new Timestamp(teacherArriveDate.getTime()));
					} else {
						queryConditionTeacher.setTeacherArriveDate(null);
					} 
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionTeacher", queryConditionTeacher);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
