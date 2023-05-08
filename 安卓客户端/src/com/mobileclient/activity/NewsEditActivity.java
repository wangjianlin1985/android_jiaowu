package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class NewsEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明记录编号TextView
	private TextView TV_newsId;
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

	private int newsId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.news_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑新闻信息信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_newsId = (TextView) findViewById(R.id.TV_newsId);
		ET_newsTitle = (EditText) findViewById(R.id.ET_newsTitle);
		ET_newsContent = (EditText) findViewById(R.id.ET_newsContent);
		dp_newsDate = (DatePicker)this.findViewById(R.id.dp_newsDate);
		iv_newsPhoto = (ImageView) findViewById(R.id.iv_newsPhoto);
		/*单击图片显示控件时进行图片的选择*/
		iv_newsPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(NewsEditActivity.this,photoListActivity.class);
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
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		newsId = extras.getInt("newsId");
		/*单击修改新闻信息按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取新闻标题*/ 
					if(ET_newsTitle.getText().toString().equals("")) {
						Toast.makeText(NewsEditActivity.this, "新闻标题输入不能为空!", Toast.LENGTH_LONG).show();
						ET_newsTitle.setFocusable(true);
						ET_newsTitle.requestFocus();
						return;	
					}
					news.setNewsTitle(ET_newsTitle.getText().toString());
					/*验证获取新闻内容*/ 
					if(ET_newsContent.getText().toString().equals("")) {
						Toast.makeText(NewsEditActivity.this, "新闻内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_newsContent.setFocusable(true);
						ET_newsContent.requestFocus();
						return;	
					}
					news.setNewsContent(ET_newsContent.getText().toString());
					/*获取出版日期*/
					Date newsDate = new Date(dp_newsDate.getYear()-1900,dp_newsDate.getMonth(),dp_newsDate.getDayOfMonth());
					news.setNewsDate(new Timestamp(newsDate.getTime()));
					if (!news.getNewsPhoto().startsWith("upload/")) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						NewsEditActivity.this.setTitle("正在上传图片，稍等...");
						String newsPhoto = HttpUtil.uploadFile(news.getNewsPhoto());
						NewsEditActivity.this.setTitle("图片上传完毕！");
						news.setNewsPhoto(newsPhoto);
					} 
					/*调用业务逻辑层上传新闻信息信息*/
					NewsEditActivity.this.setTitle("正在更新新闻信息信息，稍等...");
					String result = newsService.UpdateNews(news);
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
	    news = newsService.GetNews(newsId);
		this.TV_newsId.setText(newsId+"");
		this.ET_newsTitle.setText(news.getNewsTitle());
		this.ET_newsContent.setText(news.getNewsContent());
		Date newsDate = new Date(news.getNewsDate().getTime());
		this.dp_newsDate.init(newsDate.getYear() + 1900,newsDate.getMonth(), newsDate.getDate(), null);
		byte[] newsPhoto_data = null;
		try {
			// 获取图片数据
			newsPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + news.getNewsPhoto());
			Bitmap newsPhoto = BitmapFactory.decodeByteArray(newsPhoto_data, 0, newsPhoto_data.length);
			this.iv_newsPhoto.setImageBitmap(newsPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
