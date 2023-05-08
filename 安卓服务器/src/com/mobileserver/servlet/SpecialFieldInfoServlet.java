package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.SpecialFieldInfoDAO;
import com.mobileserver.domain.SpecialFieldInfo;

import org.json.JSONStringer;

public class SpecialFieldInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����רҵ��Ϣҵ������*/
	private SpecialFieldInfoDAO specialFieldInfoDAO = new SpecialFieldInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public SpecialFieldInfoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯרҵ��Ϣ�Ĳ�����Ϣ*/
			String specialFieldNumber = request.getParameter("specialFieldNumber");
			specialFieldNumber = specialFieldNumber == null ? "" : new String(request.getParameter(
					"specialFieldNumber").getBytes("iso-8859-1"), "UTF-8");
			String specialFieldName = request.getParameter("specialFieldName");
			specialFieldName = specialFieldName == null ? "" : new String(request.getParameter(
					"specialFieldName").getBytes("iso-8859-1"), "UTF-8");
			String specialCollegeNumber = "";
			if (request.getParameter("specialCollegeNumber") != null)
				specialCollegeNumber = request.getParameter("specialCollegeNumber");
			Timestamp specialBirthDate = null;
			if (request.getParameter("specialBirthDate") != null)
				specialBirthDate = Timestamp.valueOf(request.getParameter("specialBirthDate"));

			/*����ҵ���߼���ִ��רҵ��Ϣ��ѯ*/
			List<SpecialFieldInfo> specialFieldInfoList = specialFieldInfoDAO.QuerySpecialFieldInfo(specialFieldNumber,specialFieldName,specialCollegeNumber,specialBirthDate);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<SpecialFieldInfos>").append("\r\n");
			for (int i = 0; i < specialFieldInfoList.size(); i++) {
				sb.append("	<SpecialFieldInfo>").append("\r\n")
				.append("		<specialFieldNumber>")
				.append(specialFieldInfoList.get(i).getSpecialFieldNumber())
				.append("</specialFieldNumber>").append("\r\n")
				.append("		<specialFieldName>")
				.append(specialFieldInfoList.get(i).getSpecialFieldName())
				.append("</specialFieldName>").append("\r\n")
				.append("		<specialCollegeNumber>")
				.append(specialFieldInfoList.get(i).getSpecialCollegeNumber())
				.append("</specialCollegeNumber>").append("\r\n")
				.append("		<specialBirthDate>")
				.append(specialFieldInfoList.get(i).getSpecialBirthDate())
				.append("</specialBirthDate>").append("\r\n")
				.append("		<specialMan>")
				.append(specialFieldInfoList.get(i).getSpecialMan())
				.append("</specialMan>").append("\r\n")
				.append("		<specialTelephone>")
				.append(specialFieldInfoList.get(i).getSpecialTelephone())
				.append("</specialTelephone>").append("\r\n")
				.append("		<specialMemo>")
				.append(specialFieldInfoList.get(i).getSpecialMemo())
				.append("</specialMemo>").append("\r\n")
				.append("	</SpecialFieldInfo>").append("\r\n");
			}
			sb.append("</SpecialFieldInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(SpecialFieldInfo specialFieldInfo: specialFieldInfoList) {
				  stringer.object();
			  stringer.key("specialFieldNumber").value(specialFieldInfo.getSpecialFieldNumber());
			  stringer.key("specialFieldName").value(specialFieldInfo.getSpecialFieldName());
			  stringer.key("specialCollegeNumber").value(specialFieldInfo.getSpecialCollegeNumber());
			  stringer.key("specialBirthDate").value(specialFieldInfo.getSpecialBirthDate());
			  stringer.key("specialMan").value(specialFieldInfo.getSpecialMan());
			  stringer.key("specialTelephone").value(specialFieldInfo.getSpecialTelephone());
			  stringer.key("specialMemo").value(specialFieldInfo.getSpecialMemo());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���רҵ��Ϣ����ȡרҵ��Ϣ�������������浽�½���רҵ��Ϣ���� */ 
			SpecialFieldInfo specialFieldInfo = new SpecialFieldInfo();
			String specialFieldNumber = new String(request.getParameter("specialFieldNumber").getBytes("iso-8859-1"), "UTF-8");
			specialFieldInfo.setSpecialFieldNumber(specialFieldNumber);
			String specialFieldName = new String(request.getParameter("specialFieldName").getBytes("iso-8859-1"), "UTF-8");
			specialFieldInfo.setSpecialFieldName(specialFieldName);
			String specialCollegeNumber = new String(request.getParameter("specialCollegeNumber").getBytes("iso-8859-1"), "UTF-8");
			specialFieldInfo.setSpecialCollegeNumber(specialCollegeNumber);
			Timestamp specialBirthDate = Timestamp.valueOf(request.getParameter("specialBirthDate"));
			specialFieldInfo.setSpecialBirthDate(specialBirthDate);
			String specialMan = new String(request.getParameter("specialMan").getBytes("iso-8859-1"), "UTF-8");
			specialFieldInfo.setSpecialMan(specialMan);
			String specialTelephone = new String(request.getParameter("specialTelephone").getBytes("iso-8859-1"), "UTF-8");
			specialFieldInfo.setSpecialTelephone(specialTelephone);
			String specialMemo = new String(request.getParameter("specialMemo").getBytes("iso-8859-1"), "UTF-8");
			specialFieldInfo.setSpecialMemo(specialMemo);

			/* ����ҵ���ִ����Ӳ��� */
			String result = specialFieldInfoDAO.AddSpecialFieldInfo(specialFieldInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��רҵ��Ϣ����ȡרҵ��Ϣ��רҵ���*/
			String specialFieldNumber = new String(request.getParameter("specialFieldNumber").getBytes("iso-8859-1"), "UTF-8");
			/*����ҵ���߼���ִ��ɾ������*/
			String result = specialFieldInfoDAO.DeleteSpecialFieldInfo(specialFieldNumber);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����רҵ��Ϣ֮ǰ�ȸ���specialFieldNumber��ѯĳ��רҵ��Ϣ*/
			String specialFieldNumber = new String(request.getParameter("specialFieldNumber").getBytes("iso-8859-1"), "UTF-8");
			SpecialFieldInfo specialFieldInfo = specialFieldInfoDAO.GetSpecialFieldInfo(specialFieldNumber);

			// �ͻ��˲�ѯ��רҵ��Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("specialFieldNumber").value(specialFieldInfo.getSpecialFieldNumber());
			  stringer.key("specialFieldName").value(specialFieldInfo.getSpecialFieldName());
			  stringer.key("specialCollegeNumber").value(specialFieldInfo.getSpecialCollegeNumber());
			  stringer.key("specialBirthDate").value(specialFieldInfo.getSpecialBirthDate());
			  stringer.key("specialMan").value(specialFieldInfo.getSpecialMan());
			  stringer.key("specialTelephone").value(specialFieldInfo.getSpecialTelephone());
			  stringer.key("specialMemo").value(specialFieldInfo.getSpecialMemo());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����רҵ��Ϣ����ȡרҵ��Ϣ�������������浽�½���רҵ��Ϣ���� */ 
			SpecialFieldInfo specialFieldInfo = new SpecialFieldInfo();
			String specialFieldNumber = new String(request.getParameter("specialFieldNumber").getBytes("iso-8859-1"), "UTF-8");
			specialFieldInfo.setSpecialFieldNumber(specialFieldNumber);
			String specialFieldName = new String(request.getParameter("specialFieldName").getBytes("iso-8859-1"), "UTF-8");
			specialFieldInfo.setSpecialFieldName(specialFieldName);
			String specialCollegeNumber = new String(request.getParameter("specialCollegeNumber").getBytes("iso-8859-1"), "UTF-8");
			specialFieldInfo.setSpecialCollegeNumber(specialCollegeNumber);
			Timestamp specialBirthDate = Timestamp.valueOf(request.getParameter("specialBirthDate"));
			specialFieldInfo.setSpecialBirthDate(specialBirthDate);
			String specialMan = new String(request.getParameter("specialMan").getBytes("iso-8859-1"), "UTF-8");
			specialFieldInfo.setSpecialMan(specialMan);
			String specialTelephone = new String(request.getParameter("specialTelephone").getBytes("iso-8859-1"), "UTF-8");
			specialFieldInfo.setSpecialTelephone(specialTelephone);
			String specialMemo = new String(request.getParameter("specialMemo").getBytes("iso-8859-1"), "UTF-8");
			specialFieldInfo.setSpecialMemo(specialMemo);

			/* ����ҵ���ִ�и��²��� */
			String result = specialFieldInfoDAO.UpdateSpecialFieldInfo(specialFieldInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
