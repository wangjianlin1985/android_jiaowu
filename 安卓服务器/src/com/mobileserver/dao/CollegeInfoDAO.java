package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.CollegeInfo;
import com.mobileserver.util.DB;

public class CollegeInfoDAO {

	public List<CollegeInfo> QueryCollegeInfo() {
		List<CollegeInfo> collegeInfoList = new ArrayList<CollegeInfo>();
		DB db = new DB();
		String sql = "select * from CollegeInfo where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				CollegeInfo collegeInfo = new CollegeInfo();
				collegeInfo.setCollegeNumber(rs.getString("collegeNumber"));
				collegeInfo.setCollegeName(rs.getString("collegeName"));
				collegeInfo.setCollegeBirthDate(rs.getTimestamp("collegeBirthDate"));
				collegeInfo.setCollegeMan(rs.getString("collegeMan"));
				collegeInfo.setCollegeTelephone(rs.getString("collegeTelephone"));
				collegeInfo.setCollegeMemo(rs.getString("collegeMemo"));
				collegeInfoList.add(collegeInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return collegeInfoList;
	}
	/* ����ѧԺ��Ϣ���󣬽���ѧԺ��Ϣ�����ҵ�� */
	public String AddCollegeInfo(CollegeInfo collegeInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����ѧԺ��Ϣ */
			String sqlString = "insert into CollegeInfo(collegeNumber,collegeName,collegeBirthDate,collegeMan,collegeTelephone,collegeMemo) values (";
			sqlString += "'" + collegeInfo.getCollegeNumber() + "',";
			sqlString += "'" + collegeInfo.getCollegeName() + "',";
			sqlString += "'" + collegeInfo.getCollegeBirthDate() + "',";
			sqlString += "'" + collegeInfo.getCollegeMan() + "',";
			sqlString += "'" + collegeInfo.getCollegeTelephone() + "',";
			sqlString += "'" + collegeInfo.getCollegeMemo() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "ѧԺ��Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѧԺ��Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��ѧԺ��Ϣ */
	public String DeleteCollegeInfo(String collegeNumber) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from CollegeInfo where collegeNumber='" + collegeNumber + "'";
			db.executeUpdate(sqlString);
			result = "ѧԺ��Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѧԺ��Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����ѧԺ��Ż�ȡ��ѧԺ��Ϣ */
	public CollegeInfo GetCollegeInfo(String collegeNumber) {
		CollegeInfo collegeInfo = null;
		DB db = new DB();
		String sql = "select * from CollegeInfo where collegeNumber='" + collegeNumber + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				collegeInfo = new CollegeInfo();
				collegeInfo.setCollegeNumber(rs.getString("collegeNumber"));
				collegeInfo.setCollegeName(rs.getString("collegeName"));
				collegeInfo.setCollegeBirthDate(rs.getTimestamp("collegeBirthDate"));
				collegeInfo.setCollegeMan(rs.getString("collegeMan"));
				collegeInfo.setCollegeTelephone(rs.getString("collegeTelephone"));
				collegeInfo.setCollegeMemo(rs.getString("collegeMemo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return collegeInfo;
	}
	/* ����ѧԺ��Ϣ */
	public String UpdateCollegeInfo(CollegeInfo collegeInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update CollegeInfo set ";
			sql += "collegeName='" + collegeInfo.getCollegeName() + "',";
			sql += "collegeBirthDate='" + collegeInfo.getCollegeBirthDate() + "',";
			sql += "collegeMan='" + collegeInfo.getCollegeMan() + "',";
			sql += "collegeTelephone='" + collegeInfo.getCollegeTelephone() + "',";
			sql += "collegeMemo='" + collegeInfo.getCollegeMemo() + "'";
			sql += " where collegeNumber='" + collegeInfo.getCollegeNumber() + "'";
			db.executeUpdate(sql);
			result = "ѧԺ��Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѧԺ��Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
