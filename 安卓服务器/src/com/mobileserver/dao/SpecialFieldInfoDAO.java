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
	/* 传入专业信息对象，进行专业信息的添加业务 */
	public String AddSpecialFieldInfo(SpecialFieldInfo specialFieldInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新专业信息 */
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
			result = "专业信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "专业信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除专业信息 */
	public String DeleteSpecialFieldInfo(String specialFieldNumber) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from SpecialFieldInfo where specialFieldNumber='" + specialFieldNumber + "'";
			db.executeUpdate(sqlString);
			result = "专业信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "专业信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据专业编号获取到专业信息 */
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
	/* 更新专业信息 */
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
			result = "专业信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "专业信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
