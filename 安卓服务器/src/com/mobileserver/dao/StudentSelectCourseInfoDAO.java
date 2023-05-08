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
	/* 传入选课信息对象，进行选课信息的添加业务 */
	public String AddStudentSelectCourseInfo(StudentSelectCourseInfo studentSelectCourseInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新选课信息 */
			String sqlString = "insert into StudentSelectCourseInfo(studentNumber,courseNumber) values (";
			sqlString += "'" + studentSelectCourseInfo.getStudentNumber() + "',";
			sqlString += "'" + studentSelectCourseInfo.getCourseNumber() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "选课信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "选课信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除选课信息 */
	public String DeleteStudentSelectCourseInfo(int selectId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from StudentSelectCourseInfo where selectId=" + selectId;
			db.executeUpdate(sqlString);
			result = "选课信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "选课信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到选课信息 */
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
	/* 更新选课信息 */
	public String UpdateStudentSelectCourseInfo(StudentSelectCourseInfo studentSelectCourseInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update StudentSelectCourseInfo set ";
			sql += "studentNumber='" + studentSelectCourseInfo.getStudentNumber() + "',";
			sql += "courseNumber='" + studentSelectCourseInfo.getCourseNumber() + "'";
			sql += " where selectId=" + studentSelectCourseInfo.getSelectId();
			db.executeUpdate(sql);
			result = "选课信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "选课信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
