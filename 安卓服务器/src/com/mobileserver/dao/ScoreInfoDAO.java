package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.ScoreInfo;
import com.mobileserver.util.DB;

public class ScoreInfoDAO {

	public List<ScoreInfo> QueryScoreInfo(String studentNumber,String courseNumber) {
		List<ScoreInfo> scoreInfoList = new ArrayList<ScoreInfo>();
		DB db = new DB();
		String sql = "select * from ScoreInfo where 1=1";
		if (!studentNumber.equals(""))
			sql += " and studentNumber = '" + studentNumber + "'";
		if (!courseNumber.equals(""))
			sql += " and courseNumber = '" + courseNumber + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				ScoreInfo scoreInfo = new ScoreInfo();
				scoreInfo.setScoreId(rs.getInt("scoreId"));
				scoreInfo.setStudentNumber(rs.getString("studentNumber"));
				scoreInfo.setCourseNumber(rs.getString("courseNumber"));
				scoreInfo.setScoreValue(rs.getFloat("scoreValue"));
				scoreInfo.setStudentEvaluate(rs.getString("studentEvaluate"));
				scoreInfoList.add(scoreInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return scoreInfoList;
	}
	/* 传入成绩信息对象，进行成绩信息的添加业务 */
	public String AddScoreInfo(ScoreInfo scoreInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新成绩信息 */
			String sqlString = "insert into ScoreInfo(studentNumber,courseNumber,scoreValue,studentEvaluate) values (";
			sqlString += "'" + scoreInfo.getStudentNumber() + "',";
			sqlString += "'" + scoreInfo.getCourseNumber() + "',";
			sqlString += scoreInfo.getScoreValue() + ",";
			sqlString += "'" + scoreInfo.getStudentEvaluate() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "成绩信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "成绩信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除成绩信息 */
	public String DeleteScoreInfo(int scoreId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from ScoreInfo where scoreId=" + scoreId;
			db.executeUpdate(sqlString);
			result = "成绩信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "成绩信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到成绩信息 */
	public ScoreInfo GetScoreInfo(int scoreId) {
		ScoreInfo scoreInfo = null;
		DB db = new DB();
		String sql = "select * from ScoreInfo where scoreId=" + scoreId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				scoreInfo = new ScoreInfo();
				scoreInfo.setScoreId(rs.getInt("scoreId"));
				scoreInfo.setStudentNumber(rs.getString("studentNumber"));
				scoreInfo.setCourseNumber(rs.getString("courseNumber"));
				scoreInfo.setScoreValue(rs.getFloat("scoreValue"));
				scoreInfo.setStudentEvaluate(rs.getString("studentEvaluate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return scoreInfo;
	}
	/* 更新成绩信息 */
	public String UpdateScoreInfo(ScoreInfo scoreInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update ScoreInfo set ";
			sql += "studentNumber='" + scoreInfo.getStudentNumber() + "',";
			sql += "courseNumber='" + scoreInfo.getCourseNumber() + "',";
			sql += "scoreValue=" + scoreInfo.getScoreValue() + ",";
			sql += "studentEvaluate='" + scoreInfo.getStudentEvaluate() + "'";
			sql += " where scoreId=" + scoreInfo.getScoreId();
			db.executeUpdate(sql);
			result = "成绩信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "成绩信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
