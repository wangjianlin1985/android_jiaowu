package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Teacher;
import com.mobileclient.util.HttpUtil;

/*教师信息管理业务逻辑层*/
public class TeacherService {
	/* 添加教师信息 */
	public String AddTeacher(Teacher teacher) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("teacherNumber", teacher.getTeacherNumber());
		params.put("teacherName", teacher.getTeacherName());
		params.put("teacherPassword", teacher.getTeacherPassword());
		params.put("teacherSex", teacher.getTeacherSex());
		params.put("teacherBirthday", teacher.getTeacherBirthday().toString());
		params.put("teacherArriveDate", teacher.getTeacherArriveDate().toString());
		params.put("teacherCardNumber", teacher.getTeacherCardNumber());
		params.put("teacherPhone", teacher.getTeacherPhone());
		params.put("teacherPhoto", teacher.getTeacherPhoto());
		params.put("teacherAddress", teacher.getTeacherAddress());
		params.put("teacherMemo", teacher.getTeacherMemo());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TeacherServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询教师信息 */
	public List<Teacher> QueryTeacher(Teacher queryConditionTeacher) throws Exception {
		String urlString = HttpUtil.BASE_URL + "TeacherServlet?action=query";
		if(queryConditionTeacher != null) {
			urlString += "&teacherNumber=" + URLEncoder.encode(queryConditionTeacher.getTeacherNumber(), "UTF-8") + "";
			urlString += "&teacherName=" + URLEncoder.encode(queryConditionTeacher.getTeacherName(), "UTF-8") + "";
			if(queryConditionTeacher.getTeacherBirthday() != null) {
				urlString += "&teacherBirthday=" + URLEncoder.encode(queryConditionTeacher.getTeacherBirthday().toString(), "UTF-8");
			}
			if(queryConditionTeacher.getTeacherArriveDate() != null) {
				urlString += "&teacherArriveDate=" + URLEncoder.encode(queryConditionTeacher.getTeacherArriveDate().toString(), "UTF-8");
			}
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		TeacherListHandler teacherListHander = new TeacherListHandler();
		xr.setContentHandler(teacherListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Teacher> teacherList = teacherListHander.getTeacherList();
		return teacherList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Teacher> teacherList = new ArrayList<Teacher>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Teacher teacher = new Teacher();
				teacher.setTeacherNumber(object.getString("teacherNumber"));
				teacher.setTeacherName(object.getString("teacherName"));
				teacher.setTeacherPassword(object.getString("teacherPassword"));
				teacher.setTeacherSex(object.getString("teacherSex"));
				teacher.setTeacherBirthday(Timestamp.valueOf(object.getString("teacherBirthday")));
				teacher.setTeacherArriveDate(Timestamp.valueOf(object.getString("teacherArriveDate")));
				teacher.setTeacherCardNumber(object.getString("teacherCardNumber"));
				teacher.setTeacherPhone(object.getString("teacherPhone"));
				teacher.setTeacherPhoto(object.getString("teacherPhoto"));
				teacher.setTeacherAddress(object.getString("teacherAddress"));
				teacher.setTeacherMemo(object.getString("teacherMemo"));
				teacherList.add(teacher);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return teacherList;
	}

	/* 更新教师信息 */
	public String UpdateTeacher(Teacher teacher) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("teacherNumber", teacher.getTeacherNumber());
		params.put("teacherName", teacher.getTeacherName());
		params.put("teacherPassword", teacher.getTeacherPassword());
		params.put("teacherSex", teacher.getTeacherSex());
		params.put("teacherBirthday", teacher.getTeacherBirthday().toString());
		params.put("teacherArriveDate", teacher.getTeacherArriveDate().toString());
		params.put("teacherCardNumber", teacher.getTeacherCardNumber());
		params.put("teacherPhone", teacher.getTeacherPhone());
		params.put("teacherPhoto", teacher.getTeacherPhoto());
		params.put("teacherAddress", teacher.getTeacherAddress());
		params.put("teacherMemo", teacher.getTeacherMemo());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TeacherServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除教师信息 */
	public String DeleteTeacher(String teacherNumber) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("teacherNumber", teacherNumber);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TeacherServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "教师信息信息删除失败!";
		}
	}

	/* 根据教师编号获取教师信息对象 */
	public Teacher GetTeacher(String teacherNumber)  {
		List<Teacher> teacherList = new ArrayList<Teacher>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("teacherNumber", teacherNumber);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TeacherServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Teacher teacher = new Teacher();
				teacher.setTeacherNumber(object.getString("teacherNumber"));
				teacher.setTeacherName(object.getString("teacherName"));
				teacher.setTeacherPassword(object.getString("teacherPassword"));
				teacher.setTeacherSex(object.getString("teacherSex"));
				teacher.setTeacherBirthday(Timestamp.valueOf(object.getString("teacherBirthday")));
				teacher.setTeacherArriveDate(Timestamp.valueOf(object.getString("teacherArriveDate")));
				teacher.setTeacherCardNumber(object.getString("teacherCardNumber"));
				teacher.setTeacherPhone(object.getString("teacherPhone"));
				teacher.setTeacherPhoto(object.getString("teacherPhoto"));
				teacher.setTeacherAddress(object.getString("teacherAddress"));
				teacher.setTeacherMemo(object.getString("teacherMemo"));
				teacherList.add(teacher);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = teacherList.size();
		if(size>0) return teacherList.get(0); 
		else return null; 
	}
}
