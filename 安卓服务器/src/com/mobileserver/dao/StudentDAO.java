package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Student;
import com.mobileserver.util.DB;

public class StudentDAO {

	public List<Student> QueryStudent(String studentNumber,String studentName,String studentClassNumber,Timestamp studentBirthday) {
		List<Student> studentList = new ArrayList<Student>();
		DB db = new DB();
		String sql = "select * from Student where 1=1";
		if (!studentNumber.equals(""))
			sql += " and studentNumber like '%" + studentNumber + "%'";
		if (!studentName.equals(""))
			sql += " and studentName like '%" + studentName + "%'";
		if (!studentClassNumber.equals(""))
			sql += " and studentClassNumber = '" + studentClassNumber + "'";
		if(studentBirthday!=null)
			sql += " and studentBirthday='" + studentBirthday + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Student student = new Student();
				student.setStudentNumber(rs.getString("studentNumber"));
				student.setStudentName(rs.getString("studentName"));
				student.setStudentPassword(rs.getString("studentPassword"));
				student.setStudentSex(rs.getString("studentSex"));
				student.setStudentClassNumber(rs.getString("studentClassNumber"));
				student.setStudentBirthday(rs.getTimestamp("studentBirthday"));
				student.setStudentState(rs.getString("studentState"));
				student.setStudentPhoto(rs.getString("studentPhoto"));
				student.setStudentTelephone(rs.getString("studentTelephone"));
				student.setStudentEmail(rs.getString("studentEmail"));
				student.setStudentQQ(rs.getString("studentQQ"));
				student.setStudentAddress(rs.getString("studentAddress"));
				student.setStudentMemo(rs.getString("studentMemo"));
				studentList.add(student);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return studentList;
	}
	/* ����ѧ����Ϣ���󣬽���ѧ����Ϣ�����ҵ�� */
	public String AddStudent(Student student) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����ѧ����Ϣ */
			String sqlString = "insert into Student(studentNumber,studentName,studentPassword,studentSex,studentClassNumber,studentBirthday,studentState,studentPhoto,studentTelephone,studentEmail,studentQQ,studentAddress,studentMemo) values (";
			sqlString += "'" + student.getStudentNumber() + "',";
			sqlString += "'" + student.getStudentName() + "',";
			sqlString += "'" + student.getStudentPassword() + "',";
			sqlString += "'" + student.getStudentSex() + "',";
			sqlString += "'" + student.getStudentClassNumber() + "',";
			sqlString += "'" + student.getStudentBirthday() + "',";
			sqlString += "'" + student.getStudentState() + "',";
			sqlString += "'" + student.getStudentPhoto() + "',";
			sqlString += "'" + student.getStudentTelephone() + "',";
			sqlString += "'" + student.getStudentEmail() + "',";
			sqlString += "'" + student.getStudentQQ() + "',";
			sqlString += "'" + student.getStudentAddress() + "',";
			sqlString += "'" + student.getStudentMemo() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "ѧ����Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѧ����Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��ѧ����Ϣ */
	public String DeleteStudent(String studentNumber) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Student where studentNumber='" + studentNumber + "'";
			db.executeUpdate(sqlString);
			result = "ѧ����Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѧ����Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����ѧ�Ż�ȡ��ѧ����Ϣ */
	public Student GetStudent(String studentNumber) {
		Student student = null;
		DB db = new DB();
		String sql = "select * from Student where studentNumber='" + studentNumber + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				student = new Student();
				student.setStudentNumber(rs.getString("studentNumber"));
				student.setStudentName(rs.getString("studentName"));
				student.setStudentPassword(rs.getString("studentPassword"));
				student.setStudentSex(rs.getString("studentSex"));
				student.setStudentClassNumber(rs.getString("studentClassNumber"));
				student.setStudentBirthday(rs.getTimestamp("studentBirthday"));
				student.setStudentState(rs.getString("studentState"));
				student.setStudentPhoto(rs.getString("studentPhoto"));
				student.setStudentTelephone(rs.getString("studentTelephone"));
				student.setStudentEmail(rs.getString("studentEmail"));
				student.setStudentQQ(rs.getString("studentQQ"));
				student.setStudentAddress(rs.getString("studentAddress"));
				student.setStudentMemo(rs.getString("studentMemo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return student;
	}
	/* ����ѧ����Ϣ */
	public String UpdateStudent(Student student) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Student set ";
			sql += "studentName='" + student.getStudentName() + "',";
			sql += "studentPassword='" + student.getStudentPassword() + "',";
			sql += "studentSex='" + student.getStudentSex() + "',";
			sql += "studentClassNumber='" + student.getStudentClassNumber() + "',";
			sql += "studentBirthday='" + student.getStudentBirthday() + "',";
			sql += "studentState='" + student.getStudentState() + "',";
			sql += "studentPhoto='" + student.getStudentPhoto() + "',";
			sql += "studentTelephone='" + student.getStudentTelephone() + "',";
			sql += "studentEmail='" + student.getStudentEmail() + "',";
			sql += "studentQQ='" + student.getStudentQQ() + "',";
			sql += "studentAddress='" + student.getStudentAddress() + "',";
			sql += "studentMemo='" + student.getStudentMemo() + "'";
			sql += " where studentNumber='" + student.getStudentNumber() + "'";
			db.executeUpdate(sql);
			result = "ѧ����Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѧ����Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
