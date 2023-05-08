package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.ClassInfo;
import com.mobileclient.service.ClassInfoService;
import com.mobileclient.domain.SpecialFieldInfo;
import com.mobileclient.service.SpecialFieldInfoService;
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
public class ClassInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明班级编号控件
	private TextView TV_classNumber;
	// 声明班级名称控件
	private TextView TV_className;
	// 声明所属专业控件
	private TextView TV_classSpecialFieldNumber;
	// 声明成立日期控件
	private TextView TV_classBirthDate;
	// 声明班主任控件
	private TextView TV_classTeacherCharge;
	// 声明联系电话控件
	private TextView TV_classTelephone;
	// 声明附加信息控件
	private TextView TV_classMemo;
	/* 要保存的班级信息信息 */
	ClassInfo classInfo = new ClassInfo(); 
	/* 班级信息管理业务逻辑层 */
	private ClassInfoService classInfoService = new ClassInfoService();
	private SpecialFieldInfoService specialFieldInfoService = new SpecialFieldInfoService();
	private String classNumber;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.classinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看班级信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_classNumber = (TextView) findViewById(R.id.TV_classNumber);
		TV_className = (TextView) findViewById(R.id.TV_className);
		TV_classSpecialFieldNumber = (TextView) findViewById(R.id.TV_classSpecialFieldNumber);
		TV_classBirthDate = (TextView) findViewById(R.id.TV_classBirthDate);
		TV_classTeacherCharge = (TextView) findViewById(R.id.TV_classTeacherCharge);
		TV_classTelephone = (TextView) findViewById(R.id.TV_classTelephone);
		TV_classMemo = (TextView) findViewById(R.id.TV_classMemo);
		Bundle extras = this.getIntent().getExtras();
		classNumber = extras.getString("classNumber");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ClassInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    classInfo = classInfoService.GetClassInfo(classNumber); 
		this.TV_classNumber.setText(classInfo.getClassNumber());
		this.TV_className.setText(classInfo.getClassName());
		SpecialFieldInfo classSpecialFieldNumber = specialFieldInfoService.GetSpecialFieldInfo(classInfo.getClassSpecialFieldNumber());
		this.TV_classSpecialFieldNumber.setText(classSpecialFieldNumber.getSpecialFieldName());
		Date classBirthDate = new Date(classInfo.getClassBirthDate().getTime());
		String classBirthDateStr = (classBirthDate.getYear() + 1900) + "-" + (classBirthDate.getMonth()+1) + "-" + classBirthDate.getDate();
		this.TV_classBirthDate.setText(classBirthDateStr);
		this.TV_classTeacherCharge.setText(classInfo.getClassTeacherCharge());
		this.TV_classTelephone.setText(classInfo.getClassTelephone());
		this.TV_classMemo.setText(classInfo.getClassMemo());
	} 
}
