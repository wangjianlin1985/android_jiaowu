package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.TeacherDAO;
import com.mobileserver.domain.Teacher;

import org.json.JSONStringer;

public class TeacherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*�����ʦ��Ϣҵ������*/
	private TeacherDAO teacherDAO = new TeacherDAO();

	/*Ĭ�Ϲ��캯��*/
	public TeacherServlet() {
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
			/*��ȡ��ѯ��ʦ��Ϣ�Ĳ�����Ϣ*/
			String teacherNumber = request.getParameter("teacherNumber");
			teacherNumber = teacherNumber == null ? "" : new String(request.getParameter(
					"teacherNumber").getBytes("iso-8859-1"), "UTF-8");
			String teacherName = request.getParameter("teacherName");
			teacherName = teacherName == null ? "" : new String(request.getParameter(
					"teacherName").getBytes("iso-8859-1"), "UTF-8");
			Timestamp teacherBirthday = null;
			if (request.getParameter("teacherBirthday") != null)
				teacherBirthday = Timestamp.valueOf(request.getParameter("teacherBirthday"));
			Timestamp teacherArriveDate = null;
			if (request.getParameter("teacherArriveDate") != null)
				teacherArriveDate = Timestamp.valueOf(request.getParameter("teacherArriveDate"));

			/*����ҵ���߼���ִ�н�ʦ��Ϣ��ѯ*/
			List<Teacher> teacherList = teacherDAO.QueryTeacher(teacherNumber,teacherName,teacherBirthday,teacherArriveDate);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Teachers>").append("\r\n");
			for (int i = 0; i < teacherList.size(); i++) {
				sb.append("	<Teacher>").append("\r\n")
				.append("		<teacherNumber>")
				.append(teacherList.get(i).getTeacherNumber())
				.append("</teacherNumber>").append("\r\n")
				.append("		<teacherName>")
				.append(teacherList.get(i).getTeacherName())
				.append("</teacherName>").append("\r\n")
				.append("		<teacherPassword>")
				.append(teacherList.get(i).getTeacherPassword())
				.append("</teacherPassword>").append("\r\n")
				.append("		<teacherSex>")
				.append(teacherList.get(i).getTeacherSex())
				.append("</teacherSex>").append("\r\n")
				.append("		<teacherBirthday>")
				.append(teacherList.get(i).getTeacherBirthday())
				.append("</teacherBirthday>").append("\r\n")
				.append("		<teacherArriveDate>")
				.append(teacherList.get(i).getTeacherArriveDate())
				.append("</teacherArriveDate>").append("\r\n")
				.append("		<teacherCardNumber>")
				.append(teacherList.get(i).getTeacherCardNumber())
				.append("</teacherCardNumber>").append("\r\n")
				.append("		<teacherPhone>")
				.append(teacherList.get(i).getTeacherPhone())
				.append("</teacherPhone>").append("\r\n")
				.append("		<teacherPhoto>")
				.append(teacherList.get(i).getTeacherPhoto())
				.append("</teacherPhoto>").append("\r\n")
				.append("		<teacherAddress>")
				.append(teacherList.get(i).getTeacherAddress())
				.append("</teacherAddress>").append("\r\n")
				.append("		<teacherMemo>")
				.append(teacherList.get(i).getTeacherMemo())
				.append("</teacherMemo>").append("\r\n")
				.append("	</Teacher>").append("\r\n");
			}
			sb.append("</Teachers>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Teacher teacher: teacherList) {
				  stringer.object();
			  stringer.key("teacherNumber").value(teacher.getTeacherNumber());
			  stringer.key("teacherName").value(teacher.getTeacherName());
			  stringer.key("teacherPassword").value(teacher.getTeacherPassword());
			  stringer.key("teacherSex").value(teacher.getTeacherSex());
			  stringer.key("teacherBirthday").value(teacher.getTeacherBirthday());
			  stringer.key("teacherArriveDate").value(teacher.getTeacherArriveDate());
			  stringer.key("teacherCardNumber").value(teacher.getTeacherCardNumber());
			  stringer.key("teacherPhone").value(teacher.getTeacherPhone());
			  stringer.key("teacherPhoto").value(teacher.getTeacherPhoto());
			  stringer.key("teacherAddress").value(teacher.getTeacherAddress());
			  stringer.key("teacherMemo").value(teacher.getTeacherMemo());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӽ�ʦ��Ϣ����ȡ��ʦ��Ϣ�������������浽�½��Ľ�ʦ��Ϣ���� */ 
			Teacher teacher = new Teacher();
			String teacherNumber = new String(request.getParameter("teacherNumber").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTeacherNumber(teacherNumber);
			String teacherName = new String(request.getParameter("teacherName").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTeacherName(teacherName);
			String teacherPassword = new String(request.getParameter("teacherPassword").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTeacherPassword(teacherPassword);
			String teacherSex = new String(request.getParameter("teacherSex").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTeacherSex(teacherSex);
			Timestamp teacherBirthday = Timestamp.valueOf(request.getParameter("teacherBirthday"));
			teacher.setTeacherBirthday(teacherBirthday);
			Timestamp teacherArriveDate = Timestamp.valueOf(request.getParameter("teacherArriveDate"));
			teacher.setTeacherArriveDate(teacherArriveDate);
			String teacherCardNumber = new String(request.getParameter("teacherCardNumber").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTeacherCardNumber(teacherCardNumber);
			String teacherPhone = new String(request.getParameter("teacherPhone").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTeacherPhone(teacherPhone);
			String teacherPhoto = new String(request.getParameter("teacherPhoto").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTeacherPhoto(teacherPhoto);
			String teacherAddress = new String(request.getParameter("teacherAddress").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTeacherAddress(teacherAddress);
			String teacherMemo = new String(request.getParameter("teacherMemo").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTeacherMemo(teacherMemo);

			/* ����ҵ���ִ����Ӳ��� */
			String result = teacherDAO.AddTeacher(teacher);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����ʦ��Ϣ����ȡ��ʦ��Ϣ�Ľ�ʦ���*/
			String teacherNumber = new String(request.getParameter("teacherNumber").getBytes("iso-8859-1"), "UTF-8");
			/*����ҵ���߼���ִ��ɾ������*/
			String result = teacherDAO.DeleteTeacher(teacherNumber);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���½�ʦ��Ϣ֮ǰ�ȸ���teacherNumber��ѯĳ����ʦ��Ϣ*/
			String teacherNumber = new String(request.getParameter("teacherNumber").getBytes("iso-8859-1"), "UTF-8");
			Teacher teacher = teacherDAO.GetTeacher(teacherNumber);

			// �ͻ��˲�ѯ�Ľ�ʦ��Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("teacherNumber").value(teacher.getTeacherNumber());
			  stringer.key("teacherName").value(teacher.getTeacherName());
			  stringer.key("teacherPassword").value(teacher.getTeacherPassword());
			  stringer.key("teacherSex").value(teacher.getTeacherSex());
			  stringer.key("teacherBirthday").value(teacher.getTeacherBirthday());
			  stringer.key("teacherArriveDate").value(teacher.getTeacherArriveDate());
			  stringer.key("teacherCardNumber").value(teacher.getTeacherCardNumber());
			  stringer.key("teacherPhone").value(teacher.getTeacherPhone());
			  stringer.key("teacherPhoto").value(teacher.getTeacherPhoto());
			  stringer.key("teacherAddress").value(teacher.getTeacherAddress());
			  stringer.key("teacherMemo").value(teacher.getTeacherMemo());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���½�ʦ��Ϣ����ȡ��ʦ��Ϣ�������������浽�½��Ľ�ʦ��Ϣ���� */ 
			Teacher teacher = new Teacher();
			String teacherNumber = new String(request.getParameter("teacherNumber").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTeacherNumber(teacherNumber);
			String teacherName = new String(request.getParameter("teacherName").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTeacherName(teacherName);
			String teacherPassword = new String(request.getParameter("teacherPassword").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTeacherPassword(teacherPassword);
			String teacherSex = new String(request.getParameter("teacherSex").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTeacherSex(teacherSex);
			Timestamp teacherBirthday = Timestamp.valueOf(request.getParameter("teacherBirthday"));
			teacher.setTeacherBirthday(teacherBirthday);
			Timestamp teacherArriveDate = Timestamp.valueOf(request.getParameter("teacherArriveDate"));
			teacher.setTeacherArriveDate(teacherArriveDate);
			String teacherCardNumber = new String(request.getParameter("teacherCardNumber").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTeacherCardNumber(teacherCardNumber);
			String teacherPhone = new String(request.getParameter("teacherPhone").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTeacherPhone(teacherPhone);
			String teacherPhoto = new String(request.getParameter("teacherPhoto").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTeacherPhoto(teacherPhoto);
			String teacherAddress = new String(request.getParameter("teacherAddress").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTeacherAddress(teacherAddress);
			String teacherMemo = new String(request.getParameter("teacherMemo").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTeacherMemo(teacherMemo);

			/* ����ҵ���ִ�и��²��� */
			String result = teacherDAO.UpdateTeacher(teacher);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
