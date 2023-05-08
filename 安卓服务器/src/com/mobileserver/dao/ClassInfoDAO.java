package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.ClassInfo;
import com.mobileserver.util.DB;

public class ClassInfoDAO {

	public List<ClassInfo> QueryClassInfo(String classNumber,String className,String classSpecialFieldNumber,Timestamp classBirthDate) {
		List<ClassInfo> classInfoList = new ArrayList<ClassInfo>();
		DB db = new DB();
		String sql = "select * from ClassInfo where 1=1";
		if (!classNumber.equals(""))
			sql += " and classNumber like '%" + classNumber + "%'";
		if (!className.equals(""))
			sql += " and className like '%" + className + "%'";
		if (!classSpecialFieldNumber.equals(""))
			sql += " and classSpecialFieldNumber = '" + classSpecialFieldNumber + "'";
		if(classBirthDate!=null)
			sql += " and classBirthDate='" + classBirthDate + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				ClassInfo classInfo = new ClassInfo();
				classInfo.setClassNumber(rs.getString("classNumber"));
				classInfo.setClassName(rs.getString("className"));
				classInfo.setClassSpecialFieldNumber(rs.getString("classSpecialFieldNumber"));
				classInfo.setClassBirthDate(rs.getTimestamp("classBirthDate"));
				classInfo.setClassTeacherCharge(rs.getString("classTeacherCharge"));
				classInfo.setClassTelephone(rs.getString("classTelephone"));
				classInfo.setClassMemo(rs.getString("classMemo"));
				classInfoList.add(classInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return classInfoList;
	}
	/* ����༶��Ϣ���󣬽��а༶��Ϣ�����ҵ�� */
	public String AddClassInfo(ClassInfo classInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����°༶��Ϣ */
			String sqlString = "insert into ClassInfo(classNumber,className,classSpecialFieldNumber,classBirthDate,classTeacherCharge,classTelephone,classMemo) values (";
			sqlString += "'" + classInfo.getClassNumber() + "',";
			sqlString += "'" + classInfo.getClassName() + "',";
			sqlString += "'" + classInfo.getClassSpecialFieldNumber() + "',";
			sqlString += "'" + classInfo.getClassBirthDate() + "',";
			sqlString += "'" + classInfo.getClassTeacherCharge() + "',";
			sqlString += "'" + classInfo.getClassTelephone() + "',";
			sqlString += "'" + classInfo.getClassMemo() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "�༶��Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�༶��Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ���༶��Ϣ */
	public String DeleteClassInfo(String classNumber) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from ClassInfo where classNumber='" + classNumber + "'";
			db.executeUpdate(sqlString);
			result = "�༶��Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�༶��Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݰ༶��Ż�ȡ���༶��Ϣ */
	public ClassInfo GetClassInfo(String classNumber) {
		ClassInfo classInfo = null;
		DB db = new DB();
		String sql = "select * from ClassInfo where classNumber='" + classNumber + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				classInfo = new ClassInfo();
				classInfo.setClassNumber(rs.getString("classNumber"));
				classInfo.setClassName(rs.getString("className"));
				classInfo.setClassSpecialFieldNumber(rs.getString("classSpecialFieldNumber"));
				classInfo.setClassBirthDate(rs.getTimestamp("classBirthDate"));
				classInfo.setClassTeacherCharge(rs.getString("classTeacherCharge"));
				classInfo.setClassTelephone(rs.getString("classTelephone"));
				classInfo.setClassMemo(rs.getString("classMemo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return classInfo;
	}
	/* ���°༶��Ϣ */
	public String UpdateClassInfo(ClassInfo classInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update ClassInfo set ";
			sql += "className='" + classInfo.getClassName() + "',";
			sql += "classSpecialFieldNumber='" + classInfo.getClassSpecialFieldNumber() + "',";
			sql += "classBirthDate='" + classInfo.getClassBirthDate() + "',";
			sql += "classTeacherCharge='" + classInfo.getClassTeacherCharge() + "',";
			sql += "classTelephone='" + classInfo.getClassTelephone() + "',";
			sql += "classMemo='" + classInfo.getClassMemo() + "'";
			sql += " where classNumber='" + classInfo.getClassNumber() + "'";
			db.executeUpdate(sql);
			result = "�༶��Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�༶��Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
