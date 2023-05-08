package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Student;
import com.mobileclient.util.HttpUtil;

/*学生信息管理业务逻辑层*/
public class StudentService {
	/* 添加学生信息 */
	public String AddStudent(Student student) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("studentNumber", student.getStudentNumber());
		params.put("studentName", student.getStudentName());
		params.put("studentPassword", student.getStudentPassword());
		params.put("studentSex", student.getStudentSex());
		params.put("studentClassNumber", student.getStudentClassNumber());
		params.put("studentBirthday", student.getStudentBirthday().toString());
		params.put("studentState", student.getStudentState());
		params.put("studentPhoto", student.getStudentPhoto());
		params.put("studentTelephone", student.getStudentTelephone());
		params.put("studentEmail", student.getStudentEmail());
		params.put("studentQQ", student.getStudentQQ());
		params.put("studentAddress", student.getStudentAddress());
		params.put("studentMemo", student.getStudentMemo());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "StudentServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询学生信息 */
	public List<Student> QueryStudent(Student queryConditionStudent) throws Exception {
		String urlString = HttpUtil.BASE_URL + "StudentServlet?action=query";
		if(queryConditionStudent != null) {
			urlString += "&studentNumber=" + URLEncoder.encode(queryConditionStudent.getStudentNumber(), "UTF-8") + "";
			urlString += "&studentName=" + URLEncoder.encode(queryConditionStudent.getStudentName(), "UTF-8") + "";
			urlString += "&studentClassNumber=" + URLEncoder.encode(queryConditionStudent.getStudentClassNumber(), "UTF-8") + "";
			if(queryConditionStudent.getStudentBirthday() != null) {
				urlString += "&studentBirthday=" + URLEncoder.encode(queryConditionStudent.getStudentBirthday().toString(), "UTF-8");
			}
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		StudentListHandler studentListHander = new StudentListHandler();
		xr.setContentHandler(studentListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Student> studentList = studentListHander.getStudentList();
		return studentList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Student> studentList = new ArrayList<Student>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Student student = new Student();
				student.setStudentNumber(object.getString("studentNumber"));
				student.setStudentName(object.getString("studentName"));
				student.setStudentPassword(object.getString("studentPassword"));
				student.setStudentSex(object.getString("studentSex"));
				student.setStudentClassNumber(object.getString("studentClassNumber"));
				student.setStudentBirthday(Timestamp.valueOf(object.getString("studentBirthday")));
				student.setStudentState(object.getString("studentState"));
				student.setStudentPhoto(object.getString("studentPhoto"));
				student.setStudentTelephone(object.getString("studentTelephone"));
				student.setStudentEmail(object.getString("studentEmail"));
				student.setStudentQQ(object.getString("studentQQ"));
				student.setStudentAddress(object.getString("studentAddress"));
				student.setStudentMemo(object.getString("studentMemo"));
				studentList.add(student);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentList;
	}

	/* 更新学生信息 */
	public String UpdateStudent(Student student) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("studentNumber", student.getStudentNumber());
		params.put("studentName", student.getStudentName());
		params.put("studentPassword", student.getStudentPassword());
		params.put("studentSex", student.getStudentSex());
		params.put("studentClassNumber", student.getStudentClassNumber());
		params.put("studentBirthday", student.getStudentBirthday().toString());
		params.put("studentState", student.getStudentState());
		params.put("studentPhoto", student.getStudentPhoto());
		params.put("studentTelephone", student.getStudentTelephone());
		params.put("studentEmail", student.getStudentEmail());
		params.put("studentQQ", student.getStudentQQ());
		params.put("studentAddress", student.getStudentAddress());
		params.put("studentMemo", student.getStudentMemo());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "StudentServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除学生信息 */
	public String DeleteStudent(String studentNumber) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("studentNumber", studentNumber);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "StudentServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "学生信息信息删除失败!";
		}
	}

	/* 根据学号获取学生信息对象 */
	public Student GetStudent(String studentNumber)  {
		List<Student> studentList = new ArrayList<Student>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("studentNumber", studentNumber);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "StudentServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Student student = new Student();
				student.setStudentNumber(object.getString("studentNumber"));
				student.setStudentName(object.getString("studentName"));
				student.setStudentPassword(object.getString("studentPassword"));
				student.setStudentSex(object.getString("studentSex"));
				student.setStudentClassNumber(object.getString("studentClassNumber"));
				student.setStudentBirthday(Timestamp.valueOf(object.getString("studentBirthday")));
				student.setStudentState(object.getString("studentState"));
				student.setStudentPhoto(object.getString("studentPhoto"));
				student.setStudentTelephone(object.getString("studentTelephone"));
				student.setStudentEmail(object.getString("studentEmail"));
				student.setStudentQQ(object.getString("studentQQ"));
				student.setStudentAddress(object.getString("studentAddress"));
				student.setStudentMemo(object.getString("studentMemo"));
				studentList.add(student);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = studentList.size();
		if(size>0) return studentList.get(0); 
		else return null; 
	}
}
