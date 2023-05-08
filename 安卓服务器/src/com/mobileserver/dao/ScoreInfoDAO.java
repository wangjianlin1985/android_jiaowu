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
	/* ����ɼ���Ϣ���󣬽��гɼ���Ϣ�����ҵ�� */
	public String AddScoreInfo(ScoreInfo scoreInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����³ɼ���Ϣ */
			String sqlString = "insert into ScoreInfo(studentNumber,courseNumber,scoreValue,studentEvaluate) values (";
			sqlString += "'" + scoreInfo.getStudentNumber() + "',";
			sqlString += "'" + scoreInfo.getCourseNumber() + "',";
			sqlString += scoreInfo.getScoreValue() + ",";
			sqlString += "'" + scoreInfo.getStudentEvaluate() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "�ɼ���Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�ɼ���Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ���ɼ���Ϣ */
	public String DeleteScoreInfo(int scoreId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from ScoreInfo where scoreId=" + scoreId;
			db.executeUpdate(sqlString);
			result = "�ɼ���Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�ɼ���Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ���ɼ���Ϣ */
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
	/* ���³ɼ���Ϣ */
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
			result = "�ɼ���Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�ɼ���Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
