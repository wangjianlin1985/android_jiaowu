package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.StudentSelectCourseInfo;
import com.mobileclient.util.HttpUtil;

/*选课信息管理业务逻辑层*/
public class StudentSelectCourseInfoService {
	/* 添加选课信息 */
	public String AddStudentSelectCourseInfo(StudentSelectCourseInfo studentSelectCourseInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("selectId", studentSelectCourseInfo.getSelectId() + "");
		params.put("studentNumber", studentSelectCourseInfo.getStudentNumber());
		params.put("courseNumber", studentSelectCourseInfo.getCourseNumber());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "StudentSelectCourseInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询选课信息 */
	public List<StudentSelectCourseInfo> QueryStudentSelectCourseInfo(StudentSelectCourseInfo queryConditionStudentSelectCourseInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "StudentSelectCourseInfoServlet?action=query";
		if(queryConditionStudentSelectCourseInfo != null) {
			urlString += "&studentNumber=" + URLEncoder.encode(queryConditionStudentSelectCourseInfo.getStudentNumber(), "UTF-8") + "";
			urlString += "&courseNumber=" + URLEncoder.encode(queryConditionStudentSelectCourseInfo.getCourseNumber(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		StudentSelectCourseInfoListHandler studentSelectCourseInfoListHander = new StudentSelectCourseInfoListHandler();
		xr.setContentHandler(studentSelectCourseInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<StudentSelectCourseInfo> studentSelectCourseInfoList = studentSelectCourseInfoListHander.getStudentSelectCourseInfoList();
		return studentSelectCourseInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<StudentSelectCourseInfo> studentSelectCourseInfoList = new ArrayList<StudentSelectCourseInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				StudentSelectCourseInfo studentSelectCourseInfo = new StudentSelectCourseInfo();
				studentSelectCourseInfo.setSelectId(object.getInt("selectId"));
				studentSelectCourseInfo.setStudentNumber(object.getString("studentNumber"));
				studentSelectCourseInfo.setCourseNumber(object.getString("courseNumber"));
				studentSelectCourseInfoList.add(studentSelectCourseInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentSelectCourseInfoList;
	}

	/* 更新选课信息 */
	public String UpdateStudentSelectCourseInfo(StudentSelectCourseInfo studentSelectCourseInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("selectId", studentSelectCourseInfo.getSelectId() + "");
		params.put("studentNumber", studentSelectCourseInfo.getStudentNumber());
		params.put("courseNumber", studentSelectCourseInfo.getCourseNumber());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "StudentSelectCourseInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除选课信息 */
	public String DeleteStudentSelectCourseInfo(int selectId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("selectId", selectId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "StudentSelectCourseInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "选课信息信息删除失败!";
		}
	}

	/* 根据记录编号获取选课信息对象 */
	public StudentSelectCourseInfo GetStudentSelectCourseInfo(int selectId)  {
		List<StudentSelectCourseInfo> studentSelectCourseInfoList = new ArrayList<StudentSelectCourseInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("selectId", selectId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "StudentSelectCourseInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				StudentSelectCourseInfo studentSelectCourseInfo = new StudentSelectCourseInfo();
				studentSelectCourseInfo.setSelectId(object.getInt("selectId"));
				studentSelectCourseInfo.setStudentNumber(object.getString("studentNumber"));
				studentSelectCourseInfo.setCourseNumber(object.getString("courseNumber"));
				studentSelectCourseInfoList.add(studentSelectCourseInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = studentSelectCourseInfoList.size();
		if(size>0) return studentSelectCourseInfoList.get(0); 
		else return null; 
	}
}
