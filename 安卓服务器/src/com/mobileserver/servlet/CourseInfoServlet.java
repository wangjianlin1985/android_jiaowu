package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.CourseInfoDAO;
import com.mobileserver.domain.CourseInfo;

import org.json.JSONStringer;

public class CourseInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����γ���Ϣҵ������*/
	private CourseInfoDAO courseInfoDAO = new CourseInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public CourseInfoServlet() {
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
			/*��ȡ��ѯ�γ���Ϣ�Ĳ�����Ϣ*/
			String courseNumber = request.getParameter("courseNumber");
			courseNumber = courseNumber == null ? "" : new String(request.getParameter(
					"courseNumber").getBytes("iso-8859-1"), "UTF-8");
			String courseName = request.getParameter("courseName");
			courseName = courseName == null ? "" : new String(request.getParameter(
					"courseName").getBytes("iso-8859-1"), "UTF-8");
			String courseTeacher = "";
			if (request.getParameter("courseTeacher") != null)
				courseTeacher = request.getParameter("courseTeacher");

			/*����ҵ���߼���ִ�пγ���Ϣ��ѯ*/
			List<CourseInfo> courseInfoList = courseInfoDAO.QueryCourseInfo(courseNumber,courseName,courseTeacher);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<CourseInfos>").append("\r\n");
			for (int i = 0; i < courseInfoList.size(); i++) {
				sb.append("	<CourseInfo>").append("\r\n")
				.append("		<courseNumber>")
				.append(courseInfoList.get(i).getCourseNumber())
				.append("</courseNumber>").append("\r\n")
				.append("		<courseName>")
				.append(courseInfoList.get(i).getCourseName())
				.append("</courseName>").append("\r\n")
				.append("		<courseTeacher>")
				.append(courseInfoList.get(i).getCourseTeacher())
				.append("</courseTeacher>").append("\r\n")
				.append("		<courseTime>")
				.append(courseInfoList.get(i).getCourseTime())
				.append("</courseTime>").append("\r\n")
				.append("		<coursePlace>")
				.append(courseInfoList.get(i).getCoursePlace())
				.append("</coursePlace>").append("\r\n")
				.append("		<courseScore>")
				.append(courseInfoList.get(i).getCourseScore())
				.append("</courseScore>").append("\r\n")
				.append("		<courseMemo>")
				.append(courseInfoList.get(i).getCourseMemo())
				.append("</courseMemo>").append("\r\n")
				.append("	</CourseInfo>").append("\r\n");
			}
			sb.append("</CourseInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(CourseInfo courseInfo: courseInfoList) {
				  stringer.object();
			  stringer.key("courseNumber").value(courseInfo.getCourseNumber());
			  stringer.key("courseName").value(courseInfo.getCourseName());
			  stringer.key("courseTeacher").value(courseInfo.getCourseTeacher());
			  stringer.key("courseTime").value(courseInfo.getCourseTime());
			  stringer.key("coursePlace").value(courseInfo.getCoursePlace());
			  stringer.key("courseScore").value(courseInfo.getCourseScore());
			  stringer.key("courseMemo").value(courseInfo.getCourseMemo());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӿγ���Ϣ����ȡ�γ���Ϣ�������������浽�½��Ŀγ���Ϣ���� */ 
			CourseInfo courseInfo = new CourseInfo();
			String courseNumber = new String(request.getParameter("courseNumber").getBytes("iso-8859-1"), "UTF-8");
			courseInfo.setCourseNumber(courseNumber);
			String courseName = new String(request.getParameter("courseName").getBytes("iso-8859-1"), "UTF-8");
			courseInfo.setCourseName(courseName);
			String courseTeacher = new String(request.getParameter("courseTeacher").getBytes("iso-8859-1"), "UTF-8");
			courseInfo.setCourseTeacher(courseTeacher);
			String courseTime = new String(request.getParameter("courseTime").getBytes("iso-8859-1"), "UTF-8");
			courseInfo.setCourseTime(courseTime);
			String coursePlace = new String(request.getParameter("coursePlace").getBytes("iso-8859-1"), "UTF-8");
			courseInfo.setCoursePlace(coursePlace);
			float courseScore = Float.parseFloat(request.getParameter("courseScore"));
			courseInfo.setCourseScore(courseScore);
			String courseMemo = new String(request.getParameter("courseMemo").getBytes("iso-8859-1"), "UTF-8");
			courseInfo.setCourseMemo(courseMemo);

			/* ����ҵ���ִ����Ӳ��� */
			String result = courseInfoDAO.AddCourseInfo(courseInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ���γ���Ϣ����ȡ�γ���Ϣ�Ŀγ̱��*/
			String courseNumber = new String(request.getParameter("courseNumber").getBytes("iso-8859-1"), "UTF-8");
			/*����ҵ���߼���ִ��ɾ������*/
			String result = courseInfoDAO.DeleteCourseInfo(courseNumber);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���¿γ���Ϣ֮ǰ�ȸ���courseNumber��ѯĳ���γ���Ϣ*/
			String courseNumber = new String(request.getParameter("courseNumber").getBytes("iso-8859-1"), "UTF-8");
			CourseInfo courseInfo = courseInfoDAO.GetCourseInfo(courseNumber);

			// �ͻ��˲�ѯ�Ŀγ���Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("courseNumber").value(courseInfo.getCourseNumber());
			  stringer.key("courseName").value(courseInfo.getCourseName());
			  stringer.key("courseTeacher").value(courseInfo.getCourseTeacher());
			  stringer.key("courseTime").value(courseInfo.getCourseTime());
			  stringer.key("coursePlace").value(courseInfo.getCoursePlace());
			  stringer.key("courseScore").value(courseInfo.getCourseScore());
			  stringer.key("courseMemo").value(courseInfo.getCourseMemo());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���¿γ���Ϣ����ȡ�γ���Ϣ�������������浽�½��Ŀγ���Ϣ���� */ 
			CourseInfo courseInfo = new CourseInfo();
			String courseNumber = new String(request.getParameter("courseNumber").getBytes("iso-8859-1"), "UTF-8");
			courseInfo.setCourseNumber(courseNumber);
			String courseName = new String(request.getParameter("courseName").getBytes("iso-8859-1"), "UTF-8");
			courseInfo.setCourseName(courseName);
			String courseTeacher = new String(request.getParameter("courseTeacher").getBytes("iso-8859-1"), "UTF-8");
			courseInfo.setCourseTeacher(courseTeacher);
			String courseTime = new String(request.getParameter("courseTime").getBytes("iso-8859-1"), "UTF-8");
			courseInfo.setCourseTime(courseTime);
			String coursePlace = new String(request.getParameter("coursePlace").getBytes("iso-8859-1"), "UTF-8");
			courseInfo.setCoursePlace(coursePlace);
			float courseScore = Float.parseFloat(request.getParameter("courseScore"));
			courseInfo.setCourseScore(courseScore);
			String courseMemo = new String(request.getParameter("courseMemo").getBytes("iso-8859-1"), "UTF-8");
			courseInfo.setCourseMemo(courseMemo);

			/* ����ҵ���ִ�и��²��� */
			String result = courseInfoDAO.UpdateCourseInfo(courseInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
