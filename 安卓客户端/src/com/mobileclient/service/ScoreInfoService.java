package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.ScoreInfo;
import com.mobileclient.util.HttpUtil;

/*成绩信息管理业务逻辑层*/
public class ScoreInfoService {
	/* 添加成绩信息 */
	public String AddScoreInfo(ScoreInfo scoreInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("scoreId", scoreInfo.getScoreId() + "");
		params.put("studentNumber", scoreInfo.getStudentNumber());
		params.put("courseNumber", scoreInfo.getCourseNumber());
		params.put("scoreValue", scoreInfo.getScoreValue() + "");
		params.put("studentEvaluate", scoreInfo.getStudentEvaluate());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ScoreInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询成绩信息 */
	public List<ScoreInfo> QueryScoreInfo(ScoreInfo queryConditionScoreInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ScoreInfoServlet?action=query";
		if(queryConditionScoreInfo != null) {
			urlString += "&studentNumber=" + URLEncoder.encode(queryConditionScoreInfo.getStudentNumber(), "UTF-8") + "";
			urlString += "&courseNumber=" + URLEncoder.encode(queryConditionScoreInfo.getCourseNumber(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		ScoreInfoListHandler scoreInfoListHander = new ScoreInfoListHandler();
		xr.setContentHandler(scoreInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<ScoreInfo> scoreInfoList = scoreInfoListHander.getScoreInfoList();
		return scoreInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<ScoreInfo> scoreInfoList = new ArrayList<ScoreInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ScoreInfo scoreInfo = new ScoreInfo();
				scoreInfo.setScoreId(object.getInt("scoreId"));
				scoreInfo.setStudentNumber(object.getString("studentNumber"));
				scoreInfo.setCourseNumber(object.getString("courseNumber"));
				scoreInfo.setScoreValue((float) object.getDouble("scoreValue"));
				scoreInfo.setStudentEvaluate(object.getString("studentEvaluate"));
				scoreInfoList.add(scoreInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scoreInfoList;
	}

	/* 更新成绩信息 */
	public String UpdateScoreInfo(ScoreInfo scoreInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("scoreId", scoreInfo.getScoreId() + "");
		params.put("studentNumber", scoreInfo.getStudentNumber());
		params.put("courseNumber", scoreInfo.getCourseNumber());
		params.put("scoreValue", scoreInfo.getScoreValue() + "");
		params.put("studentEvaluate", scoreInfo.getStudentEvaluate());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ScoreInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除成绩信息 */
	public String DeleteScoreInfo(int scoreId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("scoreId", scoreId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ScoreInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "成绩信息信息删除失败!";
		}
	}

	/* 根据记录编号获取成绩信息对象 */
	public ScoreInfo GetScoreInfo(int scoreId)  {
		List<ScoreInfo> scoreInfoList = new ArrayList<ScoreInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("scoreId", scoreId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ScoreInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ScoreInfo scoreInfo = new ScoreInfo();
				scoreInfo.setScoreId(object.getInt("scoreId"));
				scoreInfo.setStudentNumber(object.getString("studentNumber"));
				scoreInfo.setCourseNumber(object.getString("courseNumber"));
				scoreInfo.setScoreValue((float) object.getDouble("scoreValue"));
				scoreInfo.setStudentEvaluate(object.getString("studentEvaluate"));
				scoreInfoList.add(scoreInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = scoreInfoList.size();
		if(size>0) return scoreInfoList.get(0); 
		else return null; 
	}
}
