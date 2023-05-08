package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.SpecialFieldInfo;
import com.mobileserver.util.DB;

public class SpecialFieldInfoDAO {

	public List<SpecialFieldInfo> QuerySpecialFieldInfo(String specialFieldNumber,String specialFieldName,String specialCollegeNumber,Timestamp specialBirthDate) {
		List<SpecialFieldInfo> specialFieldInfoList = new ArrayList<SpecialFieldInfo>();
		DB db = new DB();
		String sql = "select * from SpecialFieldInfo where 1=1";
		if (!specialFieldNumber.equals(""))
			sql += " and specialFieldNumber like '%" + specialFieldNumber + "%'";
		if (!specialFieldName.equals(""))
			sql += " and specialFieldName like '%" + specialFieldName + "%'";
		if (!specialCollegeNumber.equals(""))
			sql += " and specialCollegeNumber = '" + specialCollegeNumber + "'";
		if(specialBirthDate!=null)
			sql += " and specialBirthDate='" + specialBirthDate + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				SpecialFieldInfo specialFieldInfo = new SpecialFieldInfo();
				specialFieldInfo.setSpecialFieldNumber(rs.getString("specialFieldNumber"));
				specialFieldInfo.setSpecialFieldName(rs.getString("specialFieldName"));
				specialFieldInfo.setSpecialCollegeNumber(rs.getString("specialCollegeNumber"));
				specialFieldInfo.setSpecialBirthDate(rs.getTimestamp("specialBirthDate"));
				specialFieldInfo.setSpecialMan(rs.getString("specialMan"));
				specialFieldInfo.setSpecialTelephone(rs.getString("specialTelephone"));
				specialFieldInfo.setSpecialMemo(rs.getString("specialMemo"));
				specialFieldInfoList.add(specialFieldInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return specialFieldInfoList;
	}
	/* ����רҵ��Ϣ���󣬽���רҵ��Ϣ�����ҵ�� */
	public String AddSpecialFieldInfo(SpecialFieldInfo specialFieldInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����רҵ��Ϣ */
			String sqlString = "insert into SpecialFieldInfo(specialFieldNumber,specialFieldName,specialCollegeNumber,specialBirthDate,specialMan,specialTelephone,specialMemo) values (";
			sqlString += "'" + specialFieldInfo.getSpecialFieldNumber() + "',";
			sqlString += "'" + specialFieldInfo.getSpecialFieldName() + "',";
			sqlString += "'" + specialFieldInfo.getSpecialCollegeNumber() + "',";
			sqlString += "'" + specialFieldInfo.getSpecialBirthDate() + "',";
			sqlString += "'" + specialFieldInfo.getSpecialMan() + "',";
			sqlString += "'" + specialFieldInfo.getSpecialTelephone() + "',";
			sqlString += "'" + specialFieldInfo.getSpecialMemo() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "רҵ��Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "רҵ��Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��רҵ��Ϣ */
	public String DeleteSpecialFieldInfo(String specialFieldNumber) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from SpecialFieldInfo where specialFieldNumber='" + specialFieldNumber + "'";
			db.executeUpdate(sqlString);
			result = "רҵ��Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "רҵ��Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����רҵ��Ż�ȡ��רҵ��Ϣ */
	public SpecialFieldInfo GetSpecialFieldInfo(String specialFieldNumber) {
		SpecialFieldInfo specialFieldInfo = null;
		DB db = new DB();
		String sql = "select * from SpecialFieldInfo where specialFieldNumber='" + specialFieldNumber + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				specialFieldInfo = new SpecialFieldInfo();
				specialFieldInfo.setSpecialFieldNumber(rs.getString("specialFieldNumber"));
				specialFieldInfo.setSpecialFieldName(rs.getString("specialFieldName"));
				specialFieldInfo.setSpecialCollegeNumber(rs.getString("specialCollegeNumber"));
				specialFieldInfo.setSpecialBirthDate(rs.getTimestamp("specialBirthDate"));
				specialFieldInfo.setSpecialMan(rs.getString("specialMan"));
				specialFieldInfo.setSpecialTelephone(rs.getString("specialTelephone"));
				specialFieldInfo.setSpecialMemo(rs.getString("specialMemo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return specialFieldInfo;
	}
	/* ����רҵ��Ϣ */
	public String UpdateSpecialFieldInfo(SpecialFieldInfo specialFieldInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update SpecialFieldInfo set ";
			sql += "specialFieldName='" + specialFieldInfo.getSpecialFieldName() + "',";
			sql += "specialCollegeNumber='" + specialFieldInfo.getSpecialCollegeNumber() + "',";
			sql += "specialBirthDate='" + specialFieldInfo.getSpecialBirthDate() + "',";
			sql += "specialMan='" + specialFieldInfo.getSpecialMan() + "',";
			sql += "specialTelephone='" + specialFieldInfo.getSpecialTelephone() + "',";
			sql += "specialMemo='" + specialFieldInfo.getSpecialMemo() + "'";
			sql += " where specialFieldNumber='" + specialFieldInfo.getSpecialFieldNumber() + "'";
			db.executeUpdate(sql);
			result = "רҵ��Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "רҵ��Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
