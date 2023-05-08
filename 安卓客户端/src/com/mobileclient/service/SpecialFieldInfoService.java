package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.SpecialFieldInfo;
import com.mobileclient.util.HttpUtil;

/*专业信息管理业务逻辑层*/
public class SpecialFieldInfoService {
	/* 添加专业信息 */
	public String AddSpecialFieldInfo(SpecialFieldInfo specialFieldInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("specialFieldNumber", specialFieldInfo.getSpecialFieldNumber());
		params.put("specialFieldName", specialFieldInfo.getSpecialFieldName());
		params.put("specialCollegeNumber", specialFieldInfo.getSpecialCollegeNumber());
		params.put("specialBirthDate", specialFieldInfo.getSpecialBirthDate().toString());
		params.put("specialMan", specialFieldInfo.getSpecialMan());
		params.put("specialTelephone", specialFieldInfo.getSpecialTelephone());
		params.put("specialMemo", specialFieldInfo.getSpecialMemo());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SpecialFieldInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询专业信息 */
	public List<SpecialFieldInfo> QuerySpecialFieldInfo(SpecialFieldInfo queryConditionSpecialFieldInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "SpecialFieldInfoServlet?action=query";
		if(queryConditionSpecialFieldInfo != null) {
			urlString += "&specialFieldNumber=" + URLEncoder.encode(queryConditionSpecialFieldInfo.getSpecialFieldNumber(), "UTF-8") + "";
			urlString += "&specialFieldName=" + URLEncoder.encode(queryConditionSpecialFieldInfo.getSpecialFieldName(), "UTF-8") + "";
			urlString += "&specialCollegeNumber=" + URLEncoder.encode(queryConditionSpecialFieldInfo.getSpecialCollegeNumber(), "UTF-8") + "";
			if(queryConditionSpecialFieldInfo.getSpecialBirthDate() != null) {
				urlString += "&specialBirthDate=" + URLEncoder.encode(queryConditionSpecialFieldInfo.getSpecialBirthDate().toString(), "UTF-8");
			}
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		SpecialFieldInfoListHandler specialFieldInfoListHander = new SpecialFieldInfoListHandler();
		xr.setContentHandler(specialFieldInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<SpecialFieldInfo> specialFieldInfoList = specialFieldInfoListHander.getSpecialFieldInfoList();
		return specialFieldInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<SpecialFieldInfo> specialFieldInfoList = new ArrayList<SpecialFieldInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				SpecialFieldInfo specialFieldInfo = new SpecialFieldInfo();
				specialFieldInfo.setSpecialFieldNumber(object.getString("specialFieldNumber"));
				specialFieldInfo.setSpecialFieldName(object.getString("specialFieldName"));
				specialFieldInfo.setSpecialCollegeNumber(object.getString("specialCollegeNumber"));
				specialFieldInfo.setSpecialBirthDate(Timestamp.valueOf(object.getString("specialBirthDate")));
				specialFieldInfo.setSpecialMan(object.getString("specialMan"));
				specialFieldInfo.setSpecialTelephone(object.getString("specialTelephone"));
				specialFieldInfo.setSpecialMemo(object.getString("specialMemo"));
				specialFieldInfoList.add(specialFieldInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return specialFieldInfoList;
	}

	/* 更新专业信息 */
	public String UpdateSpecialFieldInfo(SpecialFieldInfo specialFieldInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("specialFieldNumber", specialFieldInfo.getSpecialFieldNumber());
		params.put("specialFieldName", specialFieldInfo.getSpecialFieldName());
		params.put("specialCollegeNumber", specialFieldInfo.getSpecialCollegeNumber());
		params.put("specialBirthDate", specialFieldInfo.getSpecialBirthDate().toString());
		params.put("specialMan", specialFieldInfo.getSpecialMan());
		params.put("specialTelephone", specialFieldInfo.getSpecialTelephone());
		params.put("specialMemo", specialFieldInfo.getSpecialMemo());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SpecialFieldInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除专业信息 */
	public String DeleteSpecialFieldInfo(String specialFieldNumber) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("specialFieldNumber", specialFieldNumber);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SpecialFieldInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "专业信息信息删除失败!";
		}
	}

	/* 根据专业编号获取专业信息对象 */
	public SpecialFieldInfo GetSpecialFieldInfo(String specialFieldNumber)  {
		List<SpecialFieldInfo> specialFieldInfoList = new ArrayList<SpecialFieldInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("specialFieldNumber", specialFieldNumber);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SpecialFieldInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				SpecialFieldInfo specialFieldInfo = new SpecialFieldInfo();
				specialFieldInfo.setSpecialFieldNumber(object.getString("specialFieldNumber"));
				specialFieldInfo.setSpecialFieldName(object.getString("specialFieldName"));
				specialFieldInfo.setSpecialCollegeNumber(object.getString("specialCollegeNumber"));
				specialFieldInfo.setSpecialBirthDate(Timestamp.valueOf(object.getString("specialBirthDate")));
				specialFieldInfo.setSpecialMan(object.getString("specialMan"));
				specialFieldInfo.setSpecialTelephone(object.getString("specialTelephone"));
				specialFieldInfo.setSpecialMemo(object.getString("specialMemo"));
				specialFieldInfoList.add(specialFieldInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = specialFieldInfoList.size();
		if(size>0) return specialFieldInfoList.get(0); 
		else return null; 
	}
}
