package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.ClassInfoDAO;
import com.mobileserver.domain.ClassInfo;

import org.json.JSONStringer;

public class ClassInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����༶��Ϣҵ������*/
	private ClassInfoDAO classInfoDAO = new ClassInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public ClassInfoServlet() {
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
			/*��ȡ��ѯ�༶��Ϣ�Ĳ�����Ϣ*/
			String classNumber = request.getParameter("classNumber");
			classNumber = classNumber == null ? "" : new String(request.getParameter(
					"classNumber").getBytes("iso-8859-1"), "UTF-8");
			String className = request.getParameter("className");
			className = className == null ? "" : new String(request.getParameter(
					"className").getBytes("iso-8859-1"), "UTF-8");
			String classSpecialFieldNumber = "";
			if (request.getParameter("classSpecialFieldNumber") != null)
				classSpecialFieldNumber = request.getParameter("classSpecialFieldNumber");
			Timestamp classBirthDate = null;
			if (request.getParameter("classBirthDate") != null)
				classBirthDate = Timestamp.valueOf(request.getParameter("classBirthDate"));

			/*����ҵ���߼���ִ�а༶��Ϣ��ѯ*/
			List<ClassInfo> classInfoList = classInfoDAO.QueryClassInfo(classNumber,className,classSpecialFieldNumber,classBirthDate);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<ClassInfos>").append("\r\n");
			for (int i = 0; i < classInfoList.size(); i++) {
				sb.append("	<ClassInfo>").append("\r\n")
				.append("		<classNumber>")
				.append(classInfoList.get(i).getClassNumber())
				.append("</classNumber>").append("\r\n")
				.append("		<className>")
				.append(classInfoList.get(i).getClassName())
				.append("</className>").append("\r\n")
				.append("		<classSpecialFieldNumber>")
				.append(classInfoList.get(i).getClassSpecialFieldNumber())
				.append("</classSpecialFieldNumber>").append("\r\n")
				.append("		<classBirthDate>")
				.append(classInfoList.get(i).getClassBirthDate())
				.append("</classBirthDate>").append("\r\n")
				.append("		<classTeacherCharge>")
				.append(classInfoList.get(i).getClassTeacherCharge())
				.append("</classTeacherCharge>").append("\r\n")
				.append("		<classTelephone>")
				.append(classInfoList.get(i).getClassTelephone())
				.append("</classTelephone>").append("\r\n")
				.append("		<classMemo>")
				.append(classInfoList.get(i).getClassMemo())
				.append("</classMemo>").append("\r\n")
				.append("	</ClassInfo>").append("\r\n");
			}
			sb.append("</ClassInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(ClassInfo classInfo: classInfoList) {
				  stringer.object();
			  stringer.key("classNumber").value(classInfo.getClassNumber());
			  stringer.key("className").value(classInfo.getClassName());
			  stringer.key("classSpecialFieldNumber").value(classInfo.getClassSpecialFieldNumber());
			  stringer.key("classBirthDate").value(classInfo.getClassBirthDate());
			  stringer.key("classTeacherCharge").value(classInfo.getClassTeacherCharge());
			  stringer.key("classTelephone").value(classInfo.getClassTelephone());
			  stringer.key("classMemo").value(classInfo.getClassMemo());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��Ӱ༶��Ϣ����ȡ�༶��Ϣ�������������浽�½��İ༶��Ϣ���� */ 
			ClassInfo classInfo = new ClassInfo();
			String classNumber = new String(request.getParameter("classNumber").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setClassNumber(classNumber);
			String className = new String(request.getParameter("className").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setClassName(className);
			String classSpecialFieldNumber = new String(request.getParameter("classSpecialFieldNumber").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setClassSpecialFieldNumber(classSpecialFieldNumber);
			Timestamp classBirthDate = Timestamp.valueOf(request.getParameter("classBirthDate"));
			classInfo.setClassBirthDate(classBirthDate);
			String classTeacherCharge = new String(request.getParameter("classTeacherCharge").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setClassTeacherCharge(classTeacherCharge);
			String classTelephone = new String(request.getParameter("classTelephone").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setClassTelephone(classTelephone);
			String classMemo = new String(request.getParameter("classMemo").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setClassMemo(classMemo);

			/* ����ҵ���ִ����Ӳ��� */
			String result = classInfoDAO.AddClassInfo(classInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ���༶��Ϣ����ȡ�༶��Ϣ�İ༶���*/
			String classNumber = new String(request.getParameter("classNumber").getBytes("iso-8859-1"), "UTF-8");
			/*����ҵ���߼���ִ��ɾ������*/
			String result = classInfoDAO.DeleteClassInfo(classNumber);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���°༶��Ϣ֮ǰ�ȸ���classNumber��ѯĳ���༶��Ϣ*/
			String classNumber = new String(request.getParameter("classNumber").getBytes("iso-8859-1"), "UTF-8");
			ClassInfo classInfo = classInfoDAO.GetClassInfo(classNumber);

			// �ͻ��˲�ѯ�İ༶��Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("classNumber").value(classInfo.getClassNumber());
			  stringer.key("className").value(classInfo.getClassName());
			  stringer.key("classSpecialFieldNumber").value(classInfo.getClassSpecialFieldNumber());
			  stringer.key("classBirthDate").value(classInfo.getClassBirthDate());
			  stringer.key("classTeacherCharge").value(classInfo.getClassTeacherCharge());
			  stringer.key("classTelephone").value(classInfo.getClassTelephone());
			  stringer.key("classMemo").value(classInfo.getClassMemo());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���°༶��Ϣ����ȡ�༶��Ϣ�������������浽�½��İ༶��Ϣ���� */ 
			ClassInfo classInfo = new ClassInfo();
			String classNumber = new String(request.getParameter("classNumber").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setClassNumber(classNumber);
			String className = new String(request.getParameter("className").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setClassName(className);
			String classSpecialFieldNumber = new String(request.getParameter("classSpecialFieldNumber").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setClassSpecialFieldNumber(classSpecialFieldNumber);
			Timestamp classBirthDate = Timestamp.valueOf(request.getParameter("classBirthDate"));
			classInfo.setClassBirthDate(classBirthDate);
			String classTeacherCharge = new String(request.getParameter("classTeacherCharge").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setClassTeacherCharge(classTeacherCharge);
			String classTelephone = new String(request.getParameter("classTelephone").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setClassTelephone(classTelephone);
			String classMemo = new String(request.getParameter("classMemo").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setClassMemo(classMemo);

			/* ����ҵ���ִ�и��²��� */
			String result = classInfoDAO.UpdateClassInfo(classInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
