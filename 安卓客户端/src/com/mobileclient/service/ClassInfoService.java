package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.ClassInfo;
import com.mobileclient.util.HttpUtil;

/*班级信息管理业务逻辑层*/
public class ClassInfoService {
	/* 添加班级信息 */
	public String AddClassInfo(ClassInfo classInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("classNumber", classInfo.getClassNumber());
		params.put("className", classInfo.getClassName());
		params.put("classSpecialFieldNumber", classInfo.getClassSpecialFieldNumber());
		params.put("classBirthDate", classInfo.getClassBirthDate().toString());
		params.put("classTeacherCharge", classInfo.getClassTeacherCharge());
		params.put("classTelephone", classInfo.getClassTelephone());
		params.put("classMemo", classInfo.getClassMemo());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ClassInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询班级信息 */
	public List<ClassInfo> QueryClassInfo(ClassInfo queryConditionClassInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ClassInfoServlet?action=query";
		if(queryConditionClassInfo != null) {
			urlString += "&classNumber=" + URLEncoder.encode(queryConditionClassInfo.getClassNumber(), "UTF-8") + "";
			urlString += "&className=" + URLEncoder.encode(queryConditionClassInfo.getClassName(), "UTF-8") + "";
			urlString += "&classSpecialFieldNumber=" + URLEncoder.encode(queryConditionClassInfo.getClassSpecialFieldNumber(), "UTF-8") + "";
			if(queryConditionClassInfo.getClassBirthDate() != null) {
				urlString += "&classBirthDate=" + URLEncoder.encode(queryConditionClassInfo.getClassBirthDate().toString(), "UTF-8");
			}
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		ClassInfoListHandler classInfoListHander = new ClassInfoListHandler();
		xr.setContentHandler(classInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<ClassInfo> classInfoList = classInfoListHander.getClassInfoList();
		return classInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<ClassInfo> classInfoList = new ArrayList<ClassInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ClassInfo classInfo = new ClassInfo();
				classInfo.setClassNumber(object.getString("classNumber"));
				classInfo.setClassName(object.getString("className"));
				classInfo.setClassSpecialFieldNumber(object.getString("classSpecialFieldNumber"));
				classInfo.setClassBirthDate(Timestamp.valueOf(object.getString("classBirthDate")));
				classInfo.setClassTeacherCharge(object.getString("classTeacherCharge"));
				classInfo.setClassTelephone(object.getString("classTelephone"));
				classInfo.setClassMemo(object.getString("classMemo"));
				classInfoList.add(classInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classInfoList;
	}

	/* 更新班级信息 */
	public String UpdateClassInfo(ClassInfo classInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("classNumber", classInfo.getClassNumber());
		params.put("className", classInfo.getClassName());
		params.put("classSpecialFieldNumber", classInfo.getClassSpecialFieldNumber());
		params.put("classBirthDate", classInfo.getClassBirthDate().toString());
		params.put("classTeacherCharge", classInfo.getClassTeacherCharge());
		params.put("classTelephone", classInfo.getClassTelephone());
		params.put("classMemo", classInfo.getClassMemo());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ClassInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除班级信息 */
	public String DeleteClassInfo(String classNumber) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("classNumber", classNumber);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ClassInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "班级信息信息删除失败!";
		}
	}

	/* 根据班级编号获取班级信息对象 */
	public ClassInfo GetClassInfo(String classNumber)  {
		List<ClassInfo> classInfoList = new ArrayList<ClassInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("classNumber", classNumber);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ClassInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ClassInfo classInfo = new ClassInfo();
				classInfo.setClassNumber(object.getString("classNumber"));
				classInfo.setClassName(object.getString("className"));
				classInfo.setClassSpecialFieldNumber(object.getString("classSpecialFieldNumber"));
				classInfo.setClassBirthDate(Timestamp.valueOf(object.getString("classBirthDate")));
				classInfo.setClassTeacherCharge(object.getString("classTeacherCharge"));
				classInfo.setClassTelephone(object.getString("classTelephone"));
				classInfo.setClassMemo(object.getString("classMemo"));
				classInfoList.add(classInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = classInfoList.size();
		if(size>0) return classInfoList.get(0); 
		else return null; 
	}
}
