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
	/* �����ʦ��Ϣ���󣬽��н�ʦ��Ϣ�����ҵ�� */
	public String AddTeacher(Teacher teacher) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����½�ʦ��Ϣ */
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
			result = "��ʦ��Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ʦ��Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ����ʦ��Ϣ */
	public String DeleteTeacher(String teacherNumber) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Teacher where teacherNumber='" + teacherNumber + "'";
			db.executeUpdate(sqlString);
			result = "��ʦ��Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ʦ��Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݽ�ʦ��Ż�ȡ����ʦ��Ϣ */
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
	/* ���½�ʦ��Ϣ */
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
			result = "��ʦ��Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ʦ��Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
