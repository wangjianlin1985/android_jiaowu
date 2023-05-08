package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.CollegeInfoDAO;
import com.mobileserver.domain.CollegeInfo;

import org.json.JSONStringer;

public class CollegeInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����ѧԺ��Ϣҵ������*/
	private CollegeInfoDAO collegeInfoDAO = new CollegeInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public CollegeInfoServlet() {
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
			/*��ȡ��ѯѧԺ��Ϣ�Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ��ѧԺ��Ϣ��ѯ*/
			List<CollegeInfo> collegeInfoList = collegeInfoDAO.QueryCollegeInfo();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<CollegeInfos>").append("\r\n");
			for (int i = 0; i < collegeInfoList.size(); i++) {
				sb.append("	<CollegeInfo>").append("\r\n")
				.append("		<collegeNumber>")
				.append(collegeInfoList.get(i).getCollegeNumber())
				.append("</collegeNumber>").append("\r\n")
				.append("		<collegeName>")
				.append(collegeInfoList.get(i).getCollegeName())
				.append("</collegeName>").append("\r\n")
				.append("		<collegeBirthDate>")
				.append(collegeInfoList.get(i).getCollegeBirthDate())
				.append("</collegeBirthDate>").append("\r\n")
				.append("		<collegeMan>")
				.append(collegeInfoList.get(i).getCollegeMan())
				.append("</collegeMan>").append("\r\n")
				.append("		<collegeTelephone>")
				.append(collegeInfoList.get(i).getCollegeTelephone())
				.append("</collegeTelephone>").append("\r\n")
				.append("		<collegeMemo>")
				.append(collegeInfoList.get(i).getCollegeMemo())
				.append("</collegeMemo>").append("\r\n")
				.append("	</CollegeInfo>").append("\r\n");
			}
			sb.append("</CollegeInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(CollegeInfo collegeInfo: collegeInfoList) {
				  stringer.object();
			  stringer.key("collegeNumber").value(collegeInfo.getCollegeNumber());
			  stringer.key("collegeName").value(collegeInfo.getCollegeName());
			  stringer.key("collegeBirthDate").value(collegeInfo.getCollegeBirthDate());
			  stringer.key("collegeMan").value(collegeInfo.getCollegeMan());
			  stringer.key("collegeTelephone").value(collegeInfo.getCollegeTelephone());
			  stringer.key("collegeMemo").value(collegeInfo.getCollegeMemo());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���ѧԺ��Ϣ����ȡѧԺ��Ϣ�������������浽�½���ѧԺ��Ϣ���� */ 
			CollegeInfo collegeInfo = new CollegeInfo();
			String collegeNumber = new String(request.getParameter("collegeNumber").getBytes("iso-8859-1"), "UTF-8");
			collegeInfo.setCollegeNumber(collegeNumber);
			String collegeName = new String(request.getParameter("collegeName").getBytes("iso-8859-1"), "UTF-8");
			collegeInfo.setCollegeName(collegeName);
			Timestamp collegeBirthDate = Timestamp.valueOf(request.getParameter("collegeBirthDate"));
			collegeInfo.setCollegeBirthDate(collegeBirthDate);
			String collegeMan = new String(request.getParameter("collegeMan").getBytes("iso-8859-1"), "UTF-8");
			collegeInfo.setCollegeMan(collegeMan);
			String collegeTelephone = new String(request.getParameter("collegeTelephone").getBytes("iso-8859-1"), "UTF-8");
			collegeInfo.setCollegeTelephone(collegeTelephone);
			String collegeMemo = new String(request.getParameter("collegeMemo").getBytes("iso-8859-1"), "UTF-8");
			collegeInfo.setCollegeMemo(collegeMemo);

			/* ����ҵ���ִ����Ӳ��� */
			String result = collegeInfoDAO.AddCollegeInfo(collegeInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��ѧԺ��Ϣ����ȡѧԺ��Ϣ��ѧԺ���*/
			String collegeNumber = new String(request.getParameter("collegeNumber").getBytes("iso-8859-1"), "UTF-8");
			/*����ҵ���߼���ִ��ɾ������*/
			String result = collegeInfoDAO.DeleteCollegeInfo(collegeNumber);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����ѧԺ��Ϣ֮ǰ�ȸ���collegeNumber��ѯĳ��ѧԺ��Ϣ*/
			String collegeNumber = new String(request.getParameter("collegeNumber").getBytes("iso-8859-1"), "UTF-8");
			CollegeInfo collegeInfo = collegeInfoDAO.GetCollegeInfo(collegeNumber);

			// �ͻ��˲�ѯ��ѧԺ��Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("collegeNumber").value(collegeInfo.getCollegeNumber());
			  stringer.key("collegeName").value(collegeInfo.getCollegeName());
			  stringer.key("collegeBirthDate").value(collegeInfo.getCollegeBirthDate());
			  stringer.key("collegeMan").value(collegeInfo.getCollegeMan());
			  stringer.key("collegeTelephone").value(collegeInfo.getCollegeTelephone());
			  stringer.key("collegeMemo").value(collegeInfo.getCollegeMemo());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����ѧԺ��Ϣ����ȡѧԺ��Ϣ�������������浽�½���ѧԺ��Ϣ���� */ 
			CollegeInfo collegeInfo = new CollegeInfo();
			String collegeNumber = new String(request.getParameter("collegeNumber").getBytes("iso-8859-1"), "UTF-8");
			collegeInfo.setCollegeNumber(collegeNumber);
			String collegeName = new String(request.getParameter("collegeName").getBytes("iso-8859-1"), "UTF-8");
			collegeInfo.setCollegeName(collegeName);
			Timestamp collegeBirthDate = Timestamp.valueOf(request.getParameter("collegeBirthDate"));
			collegeInfo.setCollegeBirthDate(collegeBirthDate);
			String collegeMan = new String(request.getParameter("collegeMan").getBytes("iso-8859-1"), "UTF-8");
			collegeInfo.setCollegeMan(collegeMan);
			String collegeTelephone = new String(request.getParameter("collegeTelephone").getBytes("iso-8859-1"), "UTF-8");
			collegeInfo.setCollegeTelephone(collegeTelephone);
			String collegeMemo = new String(request.getParameter("collegeMemo").getBytes("iso-8859-1"), "UTF-8");
			collegeInfo.setCollegeMemo(collegeMemo);

			/* ����ҵ���ִ�и��²��� */
			String result = collegeInfoDAO.UpdateCollegeInfo(collegeInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
