package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.StudentSelectCourseInfoDAO;
import com.mobileserver.domain.StudentSelectCourseInfo;

import org.json.JSONStringer;

public class StudentSelectCourseInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����ѡ����Ϣҵ������*/
	private StudentSelectCourseInfoDAO studentSelectCourseInfoDAO = new StudentSelectCourseInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public StudentSelectCourseInfoServlet() {
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
			/*��ȡ��ѯѡ����Ϣ�Ĳ�����Ϣ*/
			String studentNumber = "";
			if (request.getParameter("studentNumber") != null)
				studentNumber = request.getParameter("studentNumber");
			String courseNumber = "";
			if (request.getParameter("courseNumber") != null)
				courseNumber = request.getParameter("courseNumber");

			/*����ҵ���߼���ִ��ѡ����Ϣ��ѯ*/
			List<StudentSelectCourseInfo> studentSelectCourseInfoList = studentSelectCourseInfoDAO.QueryStudentSelectCourseInfo(studentNumber,courseNumber);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<StudentSelectCourseInfos>").append("\r\n");
			for (int i = 0; i < studentSelectCourseInfoList.size(); i++) {
				sb.append("	<StudentSelectCourseInfo>").append("\r\n")
				.append("		<selectId>")
				.append(studentSelectCourseInfoList.get(i).getSelectId())
				.append("</selectId>").append("\r\n")
				.append("		<studentNumber>")
				.append(studentSelectCourseInfoList.get(i).getStudentNumber())
				.append("</studentNumber>").append("\r\n")
				.append("		<courseNumber>")
				.append(studentSelectCourseInfoList.get(i).getCourseNumber())
				.append("</courseNumber>").append("\r\n")
				.append("	</StudentSelectCourseInfo>").append("\r\n");
			}
			sb.append("</StudentSelectCourseInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(StudentSelectCourseInfo studentSelectCourseInfo: studentSelectCourseInfoList) {
				  stringer.object();
			  stringer.key("selectId").value(studentSelectCourseInfo.getSelectId());
			  stringer.key("studentNumber").value(studentSelectCourseInfo.getStudentNumber());
			  stringer.key("courseNumber").value(studentSelectCourseInfo.getCourseNumber());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���ѡ����Ϣ����ȡѡ����Ϣ�������������浽�½���ѡ����Ϣ���� */ 
			StudentSelectCourseInfo studentSelectCourseInfo = new StudentSelectCourseInfo();
			int selectId = Integer.parseInt(request.getParameter("selectId"));
			studentSelectCourseInfo.setSelectId(selectId);
			String studentNumber = new String(request.getParameter("studentNumber").getBytes("iso-8859-1"), "UTF-8");
			studentSelectCourseInfo.setStudentNumber(studentNumber);
			String courseNumber = new String(request.getParameter("courseNumber").getBytes("iso-8859-1"), "UTF-8");
			studentSelectCourseInfo.setCourseNumber(courseNumber);

			/* ����ҵ���ִ����Ӳ��� */
			String result = studentSelectCourseInfoDAO.AddStudentSelectCourseInfo(studentSelectCourseInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��ѡ����Ϣ����ȡѡ����Ϣ�ļ�¼���*/
			int selectId = Integer.parseInt(request.getParameter("selectId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = studentSelectCourseInfoDAO.DeleteStudentSelectCourseInfo(selectId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����ѡ����Ϣ֮ǰ�ȸ���selectId��ѯĳ��ѡ����Ϣ*/
			int selectId = Integer.parseInt(request.getParameter("selectId"));
			StudentSelectCourseInfo studentSelectCourseInfo = studentSelectCourseInfoDAO.GetStudentSelectCourseInfo(selectId);

			// �ͻ��˲�ѯ��ѡ����Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("selectId").value(studentSelectCourseInfo.getSelectId());
			  stringer.key("studentNumber").value(studentSelectCourseInfo.getStudentNumber());
			  stringer.key("courseNumber").value(studentSelectCourseInfo.getCourseNumber());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����ѡ����Ϣ����ȡѡ����Ϣ�������������浽�½���ѡ����Ϣ���� */ 
			StudentSelectCourseInfo studentSelectCourseInfo = new StudentSelectCourseInfo();
			int selectId = Integer.parseInt(request.getParameter("selectId"));
			studentSelectCourseInfo.setSelectId(selectId);
			String studentNumber = new String(request.getParameter("studentNumber").getBytes("iso-8859-1"), "UTF-8");
			studentSelectCourseInfo.setStudentNumber(studentNumber);
			String courseNumber = new String(request.getParameter("courseNumber").getBytes("iso-8859-1"), "UTF-8");
			studentSelectCourseInfo.setCourseNumber(courseNumber);

			/* ����ҵ���ִ�и��²��� */
			String result = studentSelectCourseInfoDAO.UpdateStudentSelectCourseInfo(studentSelectCourseInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
