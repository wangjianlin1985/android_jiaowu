package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Student;
import com.mobileclient.domain.ClassInfo;
import com.mobileclient.service.ClassInfoService;

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
public class StudentQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明学号输入框
	private EditText ET_studentNumber;
	// 声明姓名输入框
	private EditText ET_studentName;
	// 声明所在班级下拉框
	private Spinner spinner_studentClassNumber;
	private ArrayAdapter<String> studentClassNumber_adapter;
	private static  String[] studentClassNumber_ShowText  = null;
	private List<ClassInfo> classInfoList = null; 
	/*班级信息管理业务逻辑层*/
	private ClassInfoService classInfoService = new ClassInfoService();
	// 出生日期控件
	private DatePicker dp_studentBirthday;
	private CheckBox cb_studentBirthday;
	/*查询过滤条件保存到这个对象中*/
	private Student queryConditionStudent = new Student();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.student_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置学生信息查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_studentNumber = (EditText) findViewById(R.id.ET_studentNumber);
		ET_studentName = (EditText) findViewById(R.id.ET_studentName);
		spinner_studentClassNumber = (Spinner) findViewById(R.id.Spinner_studentClassNumber);
		// 获取所有的班级信息
		try {
			classInfoList = classInfoService.QueryClassInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int classInfoCount = classInfoList.size();
		studentClassNumber_ShowText = new String[classInfoCount+1];
		studentClassNumber_ShowText[0] = "不限制";
		for(int i=1;i<=classInfoCount;i++) { 
			studentClassNumber_ShowText[i] = classInfoList.get(i-1).getClassName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		studentClassNumber_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, studentClassNumber_ShowText);
		// 设置所在班级下拉列表的风格
		studentClassNumber_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_studentClassNumber.setAdapter(studentClassNumber_adapter);
		// 添加事件Spinner事件监听
		spinner_studentClassNumber.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionStudent.setStudentClassNumber(classInfoList.get(arg2-1).getClassNumber()); 
				else
					queryConditionStudent.setStudentClassNumber("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_studentClassNumber.setVisibility(View.VISIBLE);
		dp_studentBirthday = (DatePicker) findViewById(R.id.dp_studentBirthday);
		cb_studentBirthday = (CheckBox) findViewById(R.id.cb_studentBirthday);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionStudent.setStudentNumber(ET_studentNumber.getText().toString());
					queryConditionStudent.setStudentName(ET_studentName.getText().toString());
					if(cb_studentBirthday.isChecked()) {
						/*获取出生日期*/
						Date studentBirthday = new Date(dp_studentBirthday.getYear()-1900,dp_studentBirthday.getMonth(),dp_studentBirthday.getDayOfMonth());
						queryConditionStudent.setStudentBirthday(new Timestamp(studentBirthday.getTime()));
					} else {
						queryConditionStudent.setStudentBirthday(null);
					} 
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionStudent", queryConditionStudent);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
