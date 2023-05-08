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
	/* 传入学院信息对象，进行学院信息的添加业务 */
	public String AddCollegeInfo(CollegeInfo collegeInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新学院信息 */
			String sqlString = "insert into CollegeInfo(collegeNumber,collegeName,collegeBirthDate,collegeMan,collegeTelephone,collegeMemo) values (";
			sqlString += "'" + collegeInfo.getCollegeNumber() + "',";
			sqlString += "'" + collegeInfo.getCollegeName() + "',";
			sqlString += "'" + collegeInfo.getCollegeBirthDate() + "',";
			sqlString += "'" + collegeInfo.getCollegeMan() + "',";
			sqlString += "'" + collegeInfo.getCollegeTelephone() + "',";
			sqlString += "'" + collegeInfo.getCollegeMemo() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "学院信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "学院信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除学院信息 */
	public String DeleteCollegeInfo(String collegeNumber) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from CollegeInfo where collegeNumber='" + collegeNumber + "'";
			db.executeUpdate(sqlString);
			result = "学院信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "学院信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据学院编号获取到学院信息 */
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
	/* 更新学院信息 */
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
			result = "学院信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "学院信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
