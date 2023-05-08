package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.StudentSelectCourseInfo;
import com.mobileserver.util.DB;

public class StudentSelectCourseInfoDAO {

	public List<StudentSelectCourseInfo> QueryStudentSelectCourseInfo(String studentNumber,String courseNumber) {
		List<StudentSelectCourseInfo> studentSelectCourseInfoList = new ArrayList<StudentSelectCourseInfo>();
		DB db = new DB();
		String sql = "select * from StudentSelectCourseInfo where 1=1";
		if (!studentNumber.equals(""))
			sql += " and studentNumber = '" + studentNumber + "'";
		if (!courseNumber.equals(""))
			sql += " and courseNumber = '" + courseNumber + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				StudentSelectCourseInfo studentSelectCourseInfo = new StudentSelectCourseInfo();
				studentSelectCourseInfo.setSelectId(rs.getInt("selectId"));
				studentSelectCourseInfo.setStudentNumber(rs.getString("studentNumber"));
				studentSelectCourseInfo.setCourseNumber(rs.getString("courseNumber"));
				studentSelectCourseInfoList.add(studentSelectCourseInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return studentSelectCourseInfoList;
	}
	/* ����ѡ����Ϣ���󣬽���ѡ����Ϣ�����ҵ�� */
	public String AddStudentSelectCourseInfo(StudentSelectCourseInfo studentSelectCourseInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����ѡ����Ϣ */
			String sqlString = "insert into StudentSelectCourseInfo(studentNumber,courseNumber) values (";
			sqlString += "'" + studentSelectCourseInfo.getStudentNumber() + "',";
			sqlString += "'" + studentSelectCourseInfo.getCourseNumber() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "ѡ����Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѡ����Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��ѡ����Ϣ */
	public String DeleteStudentSelectCourseInfo(int selectId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from StudentSelectCourseInfo where selectId=" + selectId;
			db.executeUpdate(sqlString);
			result = "ѡ����Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѡ����Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ��ѡ����Ϣ */
	public StudentSelectCourseInfo GetStudentSelectCourseInfo(int selectId) {
		StudentSelectCourseInfo studentSelectCourseInfo = null;
		DB db = new DB();
		String sql = "select * from StudentSelectCourseInfo where selectId=" + selectId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				studentSelectCourseInfo = new StudentSelectCourseInfo();
				studentSelectCourseInfo.setSelectId(rs.getInt("selectId"));
				studentSelectCourseInfo.setStudentNumber(rs.getString("studentNumber"));
				studentSelectCourseInfo.setCourseNumber(rs.getString("courseNumber"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return studentSelectCourseInfo;
	}
	/* ����ѡ����Ϣ */
	public String UpdateStudentSelectCourseInfo(StudentSelectCourseInfo studentSelectCourseInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update StudentSelectCourseInfo set ";
			sql += "studentNumber='" + studentSelectCourseInfo.getStudentNumber() + "',";
			sql += "courseNumber='" + studentSelectCourseInfo.getCourseNumber() + "'";
			sql += " where selectId=" + studentSelectCourseInfo.getSelectId();
			db.executeUpdate(sql);
			result = "ѡ����Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѡ����Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
