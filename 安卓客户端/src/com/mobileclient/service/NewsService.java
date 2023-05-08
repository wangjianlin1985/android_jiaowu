package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.News;
import com.mobileclient.util.HttpUtil;

/*新闻信息管理业务逻辑层*/
public class NewsService {
	/* 添加新闻信息 */
	public String AddNews(News news) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("newsId", news.getNewsId() + "");
		params.put("newsTitle", news.getNewsTitle());
		params.put("newsContent", news.getNewsContent());
		params.put("newsDate", news.getNewsDate().toString());
		params.put("newsPhoto", news.getNewsPhoto());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NewsServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询新闻信息 */
	public List<News> QueryNews(News queryConditionNews) throws Exception {
		String urlString = HttpUtil.BASE_URL + "NewsServlet?action=query";
		if(queryConditionNews != null) {
			urlString += "&newsTitle=" + URLEncoder.encode(queryConditionNews.getNewsTitle(), "UTF-8") + "";
			if(queryConditionNews.getNewsDate() != null) {
				urlString += "&newsDate=" + URLEncoder.encode(queryConditionNews.getNewsDate().toString(), "UTF-8");
			}
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		NewsListHandler newsListHander = new NewsListHandler();
		xr.setContentHandler(newsListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<News> newsList = newsListHander.getNewsList();
		return newsList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<News> newsList = new ArrayList<News>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				News news = new News();
				news.setNewsId(object.getInt("newsId"));
				news.setNewsTitle(object.getString("newsTitle"));
				news.setNewsContent(object.getString("newsContent"));
				news.setNewsDate(Timestamp.valueOf(object.getString("newsDate")));
				news.setNewsPhoto(object.getString("newsPhoto"));
				newsList.add(news);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newsList;
	}

	/* 更新新闻信息 */
	public String UpdateNews(News news) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("newsId", news.getNewsId() + "");
		params.put("newsTitle", news.getNewsTitle());
		params.put("newsContent", news.getNewsContent());
		params.put("newsDate", news.getNewsDate().toString());
		params.put("newsPhoto", news.getNewsPhoto());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NewsServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除新闻信息 */
	public String DeleteNews(int newsId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("newsId", newsId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NewsServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "新闻信息信息删除失败!";
		}
	}

	/* 根据记录编号获取新闻信息对象 */
	public News GetNews(int newsId)  {
		List<News> newsList = new ArrayList<News>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("newsId", newsId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NewsServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				News news = new News();
				news.setNewsId(object.getInt("newsId"));
				news.setNewsTitle(object.getString("newsTitle"));
				news.setNewsContent(object.getString("newsContent"));
				news.setNewsDate(Timestamp.valueOf(object.getString("newsDate")));
				news.setNewsPhoto(object.getString("newsPhoto"));
				newsList.add(news);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = newsList.size();
		if(size>0) return newsList.get(0); 
		else return null; 
	}
}
