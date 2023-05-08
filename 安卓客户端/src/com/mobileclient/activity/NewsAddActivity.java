package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.News;
import com.mobileclient.service.NewsService;
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
public class NewsAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明新闻标题输入框
	private EditText ET_newsTitle;
	// 声明新闻内容输入框
	private EditText ET_newsContent;
	// 出版发布日期控件
	private DatePicker dp_newsDate;
	// 声明新闻图片图片框控件
	private ImageView iv_newsPhoto;
	private Button btn_newsPhoto;
	protected int REQ_CODE_SELECT_IMAGE_newsPhoto = 1;
	private int REQ_CODE_CAMERA_newsPhoto = 2;
	protected String carmera_path;
	/*要保存的新闻信息信息*/
	News news = new News();
	/*新闻信息管理业务逻辑层*/
	private NewsService newsService = new NewsService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.news_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加新闻信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_newsTitle = (EditText) findViewById(R.id.ET_newsTitle);
		ET_newsContent = (EditText) findViewById(R.id.ET_newsContent);
		dp_newsDate = (DatePicker)this.findViewById(R.id.dp_newsDate);
		iv_newsPhoto = (ImageView) findViewById(R.id.iv_newsPhoto);
		/*单击图片显示控件时进行图片的选择*/
		iv_newsPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(NewsAddActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_newsPhoto);
			}
		});
		btn_newsPhoto = (Button) findViewById(R.id.btn_newsPhoto);
		btn_newsPhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_newsPhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_newsPhoto);  
			}
		});
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加新闻信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取新闻标题*/ 
					if(ET_newsTitle.getText().toString().equals("")) {
						Toast.makeText(NewsAddActivity.this, "新闻标题输入不能为空!", Toast.LENGTH_LONG).show();
						ET_newsTitle.setFocusable(true);
						ET_newsTitle.requestFocus();
						return;	
					}
					news.setNewsTitle(ET_newsTitle.getText().toString());
					/*验证获取新闻内容*/ 
					if(ET_newsContent.getText().toString().equals("")) {
						Toast.makeText(NewsAddActivity.this, "新闻内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_newsContent.setFocusable(true);
						ET_newsContent.requestFocus();
						return;	
					}
					news.setNewsContent(ET_newsContent.getText().toString());
					/*获取发布日期*/
					Date newsDate = new Date(dp_newsDate.getYear()-1900,dp_newsDate.getMonth(),dp_newsDate.getDayOfMonth());
					news.setNewsDate(new Timestamp(newsDate.getTime()));
					if(news.getNewsPhoto() != null) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						NewsAddActivity.this.setTitle("正在上传图片，稍等...");
						String newsPhoto = HttpUtil.uploadFile(news.getNewsPhoto());
						NewsAddActivity.this.setTitle("图片上传完毕！");
						news.setNewsPhoto(newsPhoto);
					} else {
						news.setNewsPhoto("upload/noimage.jpg");
					}
					/*调用业务逻辑层上传新闻信息信息*/
					NewsAddActivity.this.setTitle("正在上传新闻信息信息，稍等...");
					String result = newsService.AddNews(news);
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
		if (requestCode == REQ_CODE_CAMERA_newsPhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_newsPhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_newsPhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_newsPhoto.setImageBitmap(booImageBm);
				this.iv_newsPhoto.setScaleType(ScaleType.FIT_CENTER);
				this.news.setNewsPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_newsPhoto && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_newsPhoto.setImageBitmap(bm); 
				this.iv_newsPhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			news.setNewsPhoto(filename); 
		}
	}
}
