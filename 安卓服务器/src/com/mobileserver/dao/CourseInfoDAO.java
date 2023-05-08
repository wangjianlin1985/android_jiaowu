package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.CourseInfo;
import com.mobileserver.util.DB;

public class CourseInfoDAO {

	public List<CourseInfo> QueryCourseInfo(String courseNumber,String courseName,String courseTeacher) {
		List<CourseInfo> courseInfoList = new ArrayList<CourseInfo>();
		DB db = new DB();
		String sql = "select * from CourseInfo where 1=1";
		if (!courseNumber.equals(""))
			sql += " and courseNumber like '%" + courseNumber + "%'";
		if (!courseName.equals(""))
			sql += " and courseName like '%" + courseName + "%'";
		if (!courseTeacher.equals(""))
			sql += " and courseTeacher = '" + courseTeacher + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				CourseInfo courseInfo = new CourseInfo();
				courseInfo.setCourseNumber(rs.getString("courseNumber"));
				courseInfo.setCourseName(rs.getString("courseName"));
				courseInfo.setCourseTeacher(rs.getString("courseTeacher"));
				courseInfo.setCourseTime(rs.getString("courseTime"));
				courseInfo.setCoursePlace(rs.getString("coursePlace"));
				courseInfo.setCourseScore(rs.getFloat("courseScore"));
				courseInfo.setCourseMemo(rs.getString("courseMemo"));
				courseInfoList.add(courseInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return courseInfoList;
	}
	/* 传入课程信息对象，进行课程信息的添加业务 */
	public String AddCourseInfo(CourseInfo courseInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新课程信息 */
			String sqlString = "insert into CourseInfo(courseNumber,courseName,courseTeacher,courseTime,coursePlace,courseScore,courseMemo) values (";
			sqlString += "'" + courseInfo.getCourseNumber() + "',";
			sqlString += "'" + courseInfo.getCourseName() + "',";
			sqlString += "'" + courseInfo.getCourseTeacher() + "',";
			sqlString += "'" + courseInfo.getCourseTime() + "',";
			sqlString += "'" + courseInfo.getCoursePlace() + "',";
			sqlString += courseInfo.getCourseScore() + ",";
			sqlString += "'" + courseInfo.getCourseMemo() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "课程信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "课程信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除课程信息 */
	public String DeleteCourseInfo(String courseNumber) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from CourseInfo where courseNumber='" + courseNumber + "'";
			db.executeUpdate(sqlString);
			result = "课程信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "课程信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据课程编号获取到课程信息 */
	public CourseInfo GetCourseInfo(String courseNumber) {
		CourseInfo courseInfo = null;
		DB db = new DB();
		String sql = "select * from CourseInfo where courseNumber='" + courseNumber + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				courseInfo = new CourseInfo();
				courseInfo.setCourseNumber(rs.getString("courseNumber"));
				courseInfo.setCourseName(rs.getString("courseName"));
				courseInfo.setCourseTeacher(rs.getString("courseTeacher"));
				courseInfo.setCourseTime(rs.getString("courseTime"));
				courseInfo.setCoursePlace(rs.getString("coursePlace"));
				courseInfo.setCourseScore(rs.getFloat("courseScore"));
				courseInfo.setCourseMemo(rs.getString("courseMemo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return courseInfo;
	}
	/* 更新课程信息 */
	public String UpdateCourseInfo(CourseInfo courseInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update CourseInfo set ";
			sql += "courseName='" + courseInfo.getCourseName() + "',";
			sql += "courseTeacher='" + courseInfo.getCourseTeacher() + "',";
			sql += "courseTime='" + courseInfo.getCourseTime() + "',";
			sql += "coursePlace='" + courseInfo.getCoursePlace() + "',";
			sql += "courseScore=" + courseInfo.getCourseScore() + ",";
			sql += "courseMemo='" + courseInfo.getCourseMemo() + "'";
			sql += " where courseNumber='" + courseInfo.getCourseNumber() + "'";
			db.executeUpdate(sql);
			result = "课程信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "课程信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
