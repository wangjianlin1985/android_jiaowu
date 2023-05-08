package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.ClassInfoService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class StudentSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public StudentSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.student_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_studentNumber = (TextView)convertView.findViewById(R.id.tv_studentNumber);
	  holder.tv_studentName = (TextView)convertView.findViewById(R.id.tv_studentName);
	  holder.tv_studentSex = (TextView)convertView.findViewById(R.id.tv_studentSex);
	  holder.tv_studentClassNumber = (TextView)convertView.findViewById(R.id.tv_studentClassNumber);
	  holder.iv_studentPhoto = (ImageView)convertView.findViewById(R.id.iv_studentPhoto);
	  /*设置各个控件的展示内容*/
	  holder.tv_studentNumber.setText("学号：" + mData.get(position).get("studentNumber").toString());
	  holder.tv_studentName.setText("姓名：" + mData.get(position).get("studentName").toString());
	  holder.tv_studentSex.setText("性别：" + mData.get(position).get("studentSex").toString());
	  holder.tv_studentClassNumber.setText("所在班级：" + (new ClassInfoService()).GetClassInfo(mData.get(position).get("studentClassNumber").toString()).getClassName());
	  holder.iv_studentPhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener studentPhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_studentPhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("studentPhoto"),studentPhotoLoadListener);  
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_studentNumber;
    	TextView tv_studentName;
    	TextView tv_studentSex;
    	TextView tv_studentClassNumber;
    	ImageView iv_studentPhoto;
    }
} 
