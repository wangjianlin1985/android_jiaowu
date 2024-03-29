package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.SpecialFieldInfoService;
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

public class ClassInfoSimpleAdapter extends SimpleAdapter { 
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

    public ClassInfoSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.classinfo_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_classNumber = (TextView)convertView.findViewById(R.id.tv_classNumber);
	  holder.tv_className = (TextView)convertView.findViewById(R.id.tv_className);
	  holder.tv_classSpecialFieldNumber = (TextView)convertView.findViewById(R.id.tv_classSpecialFieldNumber);
	  /*设置各个控件的展示内容*/
	  holder.tv_classNumber.setText("班级编号：" + mData.get(position).get("classNumber").toString());
	  holder.tv_className.setText("班级名称：" + mData.get(position).get("className").toString());
	  holder.tv_classSpecialFieldNumber.setText("所属专业：" + (new SpecialFieldInfoService()).GetSpecialFieldInfo(mData.get(position).get("classSpecialFieldNumber").toString()).getSpecialFieldName());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_classNumber;
    	TextView tv_className;
    	TextView tv_classSpecialFieldNumber;
    }
} 
