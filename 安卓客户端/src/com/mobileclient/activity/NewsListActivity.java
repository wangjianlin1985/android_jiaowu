package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.News;
import com.mobileclient.service.NewsService;
import com.mobileclient.util.ActivityUtils;import com.mobileclient.util.NewsSimpleAdapter;
import com.mobileclient.util.HttpUtil;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class NewsListActivity extends Activity {
	NewsSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int newsId;
	/* 新闻信息操作业务逻辑层对象 */
	NewsService newsService = new NewsService();
	/*保存查询参数条件的新闻信息对象*/
	private News queryConditionNews;

	private MyProgressDialog dialog; //进度条	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.news_list);
		dialog = MyProgressDialog.getInstance(this);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		//标题栏控件
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(NewsListActivity.this, NewsQueryActivity.class);
				startActivityForResult(intent,ActivityUtils.QUERY_CODE);//此处的requestCode应与下面结果处理函中调用的requestCode一致
			}
		});
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("新闻信息查询列表");
		ImageView add_btn = (ImageView) this.findViewById(R.id.add_btn);
		add_btn.setOnClickListener(new android.view.View.OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(NewsListActivity.this, NewsAddActivity.class);
				startActivityForResult(intent,ActivityUtils.ADD_CODE);
			}
		});
		setViews();
	}

	//结果处理函数，当从secondActivity中返回时调用此函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ActivityUtils.QUERY_CODE && resultCode==RESULT_OK){
        	Bundle extras = data.getExtras();
        	if(extras != null)
        		queryConditionNews = (News)extras.getSerializable("queryConditionNews");
        	setViews();
        }
        if(requestCode==ActivityUtils.EDIT_CODE && resultCode==RESULT_OK){
        	setViews();
        }
        if(requestCode == ActivityUtils.ADD_CODE && resultCode == RESULT_OK) {
        	queryConditionNews = null;
        	setViews();
        }
    }

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		dialog.show();
		final Handler handler = new Handler();
		new Thread(){
			@Override
			public void run() {
				//在子线程中进行下载数据操作
				list = getDatas();
				//发送消失到handler，通知主线程下载完成
				handler.post(new Runnable() {
					@Override
					public void run() {
						dialog.cancel();
						adapter = new NewsSimpleAdapter(NewsListActivity.this, list,
	        					R.layout.news_list_item,
	        					new String[] { "newsId","newsTitle","newsDate","newsPhoto" },
	        					new int[] { R.id.tv_newsId,R.id.tv_newsTitle,R.id.tv_newsDate,R.id.iv_newsPhoto,},lv);
	        			lv.setAdapter(adapter);
					}
				});
			}
		}.start(); 

		// 添加长按点击
		lv.setOnCreateContextMenuListener(newsListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int newsId = Integer.parseInt(list.get(arg2).get("newsId").toString());
            	Intent intent = new Intent();
            	intent.setClass(NewsListActivity.this, NewsDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("newsId", newsId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener newsListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			menu.add(0, 0, 0, "编辑新闻信息信息"); 
			menu.add(0, 1, 0, "删除新闻信息信息");
		}
	};

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //编辑新闻信息信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取记录编号
			newsId = Integer.parseInt(list.get(position).get("newsId").toString());
			Intent intent = new Intent();
			intent.setClass(NewsListActivity.this, NewsEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("newsId", newsId);
			intent.putExtras(bundle);
			startActivityForResult(intent,ActivityUtils.EDIT_CODE);
		} else if (item.getItemId() == 1) {// 删除新闻信息信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取记录编号
			newsId = Integer.parseInt(list.get(position).get("newsId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// 删除
	protected void dialog() {
		Builder builder = new Builder(NewsListActivity.this);
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = newsService.DeleteNews(newsId);
				Toast.makeText(getApplicationContext(), result, 1).show();
				setViews();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private List<Map<String, Object>> getDatas() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			/* 查询新闻信息信息 */
			List<News> newsList = newsService.QueryNews(queryConditionNews);
			for (int i = 0; i < newsList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("newsId", newsList.get(i).getNewsId());
				map.put("newsTitle", newsList.get(i).getNewsTitle());
				map.put("newsDate", newsList.get(i).getNewsDate());
				/*byte[] newsPhoto_data = ImageService.getImage(HttpUtil.BASE_URL+ newsList.get(i).getNewsPhoto());// 获取图片数据
				BitmapFactory.Options newsPhoto_opts = new BitmapFactory.Options();  
				newsPhoto_opts.inJustDecodeBounds = true;  
				BitmapFactory.decodeByteArray(newsPhoto_data, 0, newsPhoto_data.length, newsPhoto_opts); 
				newsPhoto_opts.inSampleSize = photoListActivity.computeSampleSize(newsPhoto_opts, -1, 100*100); 
				newsPhoto_opts.inJustDecodeBounds = false; 
				try {
					Bitmap newsPhoto = BitmapFactory.decodeByteArray(newsPhoto_data, 0, newsPhoto_data.length, newsPhoto_opts);
					map.put("newsPhoto", newsPhoto);
				} catch (OutOfMemoryError err) { }*/
				map.put("newsPhoto", HttpUtil.BASE_URL+ newsList.get(i).getNewsPhoto());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

}
