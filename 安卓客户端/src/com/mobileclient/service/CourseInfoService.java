package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.CourseInfo;
import com.mobileclient.util.HttpUtil;

/*课程信息管理业务逻辑层*/
public class CourseInfoService {
	/* 添加课程信息 */
	public String AddCourseInfo(CourseInfo courseInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("courseNumber", courseInfo.getCourseNumber());
		params.put("courseName", courseInfo.getCourseName());
		params.put("courseTeacher", courseInfo.getCourseTeacher());
		params.put("courseTime", courseInfo.getCourseTime());
		params.put("coursePlace", courseInfo.getCoursePlace());
		params.put("courseScore", courseInfo.getCourseScore() + "");
		params.put("courseMemo", courseInfo.getCourseMemo());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CourseInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询课程信息 */
	public List<CourseInfo> QueryCourseInfo(CourseInfo queryConditionCourseInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "CourseInfoServlet?action=query";
		if(queryConditionCourseInfo != null) {
			urlString += "&courseNumber=" + URLEncoder.encode(queryConditionCourseInfo.getCourseNumber(), "UTF-8") + "";
			urlString += "&courseName=" + URLEncoder.encode(queryConditionCourseInfo.getCourseName(), "UTF-8") + "";
			urlString += "&courseTeacher=" + URLEncoder.encode(queryConditionCourseInfo.getCourseTeacher(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		CourseInfoListHandler courseInfoListHander = new CourseInfoListHandler();
		xr.setContentHandler(courseInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<CourseInfo> courseInfoList = courseInfoListHander.getCourseInfoList();
		return courseInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<CourseInfo> courseInfoList = new ArrayList<CourseInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				CourseInfo courseInfo = new CourseInfo();
				courseInfo.setCourseNumber(object.getString("courseNumber"));
				courseInfo.setCourseName(object.getString("courseName"));
				courseInfo.setCourseTeacher(object.getString("courseTeacher"));
				courseInfo.setCourseTime(object.getString("courseTime"));
				courseInfo.setCoursePlace(object.getString("coursePlace"));
				courseInfo.setCourseScore((float) object.getDouble("courseScore"));
				courseInfo.setCourseMemo(object.getString("courseMemo"));
				courseInfoList.add(courseInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return courseInfoList;
	}

	/* 更新课程信息 */
	public String UpdateCourseInfo(CourseInfo courseInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("courseNumber", courseInfo.getCourseNumber());
		params.put("courseName", courseInfo.getCourseName());
		params.put("courseTeacher", courseInfo.getCourseTeacher());
		params.put("courseTime", courseInfo.getCourseTime());
		params.put("coursePlace", courseInfo.getCoursePlace());
		params.put("courseScore", courseInfo.getCourseScore() + "");
		params.put("courseMemo", courseInfo.getCourseMemo());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CourseInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除课程信息 */
	public String DeleteCourseInfo(String courseNumber) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("courseNumber", courseNumber);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CourseInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "课程信息信息删除失败!";
		}
	}

	/* 根据课程编号获取课程信息对象 */
	public CourseInfo GetCourseInfo(String courseNumber)  {
		List<CourseInfo> courseInfoList = new ArrayList<CourseInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("courseNumber", courseNumber);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CourseInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				CourseInfo courseInfo = new CourseInfo();
				courseInfo.setCourseNumber(object.getString("courseNumber"));
				courseInfo.setCourseName(object.getString("courseName"));
				courseInfo.setCourseTeacher(object.getString("courseTeacher"));
				courseInfo.setCourseTime(object.getString("courseTime"));
				courseInfo.setCoursePlace(object.getString("coursePlace"));
				courseInfo.setCourseScore((float) object.getDouble("courseScore"));
				courseInfo.setCourseMemo(object.getString("courseMemo"));
				courseInfoList.add(courseInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = courseInfoList.size();
		if(size>0) return courseInfoList.get(0); 
		else return null; 
	}
}
