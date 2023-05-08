package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Teacher;
import com.mobileserver.util.DB;

public class TeacherDAO {

	public List<Teacher> QueryTeacher(String teacherNumber,String teacherName,Timestamp teacherBirthday,Timestamp teacherArriveDate) {
		List<Teacher> teacherList = new ArrayList<Teacher>();
		DB db = new DB();
		String sql = "select * from Teacher where 1=1";
		if (!teacherNumber.equals(""))
			sql += " and teacherNumber like '%" + teacherNumber + "%'";
		if (!teacherName.equals(""))
			sql += " and teacherName like '%" + teacherName + "%'";
		if(teacherBirthday!=null)
			sql += " and teacherBirthday='" + teacherBirthday + "'";
		if(teacherArriveDate!=null)
			sql += " and teacherArriveDate='" + teacherArriveDate + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Teacher teacher = new Teacher();
				teacher.setTeacherNumber(rs.getString("teacherNumber"));
				teacher.setTeacherName(rs.getString("teacherName"));
				teacher.setTeacherPassword(rs.getString("teacherPassword"));
				teacher.setTeacherSex(rs.getString("teacherSex"));
				teacher.setTeacherBirthday(rs.getTimestamp("teacherBirthday"));
				teacher.setTeacherArriveDate(rs.getTimestamp("teacherArriveDate"));
				teacher.setTeacherCardNumber(rs.getString("teacherCardNumber"));
				teacher.setTeacherPhone(rs.getString("teacherPhone"));
				teacher.setTeacherPhoto(rs.getString("teacherPhoto"));
				teacher.setTeacherAddress(rs.getString("teacherAddress"));
				teacher.setTeacherMemo(rs.getString("teacherMemo"));
				teacherList.add(teacher);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return teacherList;
	}
	/* 传入教师信息对象，进行教师信息的添加业务 */
	public String AddTeacher(Teacher teacher) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新教师信息 */
			String sqlString = "insert into Teacher(teacherNumber,teacherName,teacherPassword,teacherSex,teacherBirthday,teacherArriveDate,teacherCardNumber,teacherPhone,teacherPhoto,teacherAddress,teacherMemo) values (";
			sqlString += "'" + teacher.getTeacherNumber() + "',";
			sqlString += "'" + teacher.getTeacherName() + "',";
			sqlString += "'" + teacher.getTeacherPassword() + "',";
			sqlString += "'" + teacher.getTeacherSex() + "',";
			sqlString += "'" + teacher.getTeacherBirthday() + "',";
			sqlString += "'" + teacher.getTeacherArriveDate() + "',";
			sqlString += "'" + teacher.getTeacherCardNumber() + "',";
			sqlString += "'" + teacher.getTeacherPhone() + "',";
			sqlString += "'" + teacher.getTeacherPhoto() + "',";
			sqlString += "'" + teacher.getTeacherAddress() + "',";
			sqlString += "'" + teacher.getTeacherMemo() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "教师信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "教师信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除教师信息 */
	public String DeleteTeacher(String teacherNumber) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Teacher where teacherNumber='" + teacherNumber + "'";
			db.executeUpdate(sqlString);
			result = "教师信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "教师信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据教师编号获取到教师信息 */
	public Teacher GetTeacher(String teacherNumber) {
		Teacher teacher = null;
		DB db = new DB();
		String sql = "select * from Teacher where teacherNumber='" + teacherNumber + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				teacher = new Teacher();
				teacher.setTeacherNumber(rs.getString("teacherNumber"));
				teacher.setTeacherName(rs.getString("teacherName"));
				teacher.setTeacherPassword(rs.getString("teacherPassword"));
				teacher.setTeacherSex(rs.getString("teacherSex"));
				teacher.setTeacherBirthday(rs.getTimestamp("teacherBirthday"));
				teacher.setTeacherArriveDate(rs.getTimestamp("teacherArriveDate"));
				teacher.setTeacherCardNumber(rs.getString("teacherCardNumber"));
				teacher.setTeacherPhone(rs.getString("teacherPhone"));
				teacher.setTeacherPhoto(rs.getString("teacherPhoto"));
				teacher.setTeacherAddress(rs.getString("teacherAddress"));
				teacher.setTeacherMemo(rs.getString("teacherMemo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return teacher;
	}
	/* 更新教师信息 */
	public String UpdateTeacher(Teacher teacher) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Teacher set ";
			sql += "teacherName='" + teacher.getTeacherName() + "',";
			sql += "teacherPassword='" + teacher.getTeacherPassword() + "',";
			sql += "teacherSex='" + teacher.getTeacherSex() + "',";
			sql += "teacherBirthday='" + teacher.getTeacherBirthday() + "',";
			sql += "teacherArriveDate='" + teacher.getTeacherArriveDate() + "',";
			sql += "teacherCardNumber='" + teacher.getTeacherCardNumber() + "',";
			sql += "teacherPhone='" + teacher.getTeacherPhone() + "',";
			sql += "teacherPhoto='" + teacher.getTeacherPhoto() + "',";
			sql += "teacherAddress='" + teacher.getTeacherAddress() + "',";
			sql += "teacherMemo='" + teacher.getTeacherMemo() + "'";
			sql += " where teacherNumber='" + teacher.getTeacherNumber() + "'";
			db.executeUpdate(sql);
			result = "教师信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "教师信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
