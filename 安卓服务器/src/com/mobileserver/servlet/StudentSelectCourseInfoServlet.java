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

	/*构造选课信息业务层对象*/
	private StudentSelectCourseInfoDAO studentSelectCourseInfoDAO = new StudentSelectCourseInfoDAO();

	/*默认构造函数*/
	public StudentSelectCourseInfoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*获取action参数，根据action的值执行不同的业务处理*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*获取查询选课信息的参数信息*/
			String studentNumber = "";
			if (request.getParameter("studentNumber") != null)
				studentNumber = request.getParameter("studentNumber");
			String courseNumber = "";
			if (request.getParameter("courseNumber") != null)
				courseNumber = request.getParameter("courseNumber");

			/*调用业务逻辑层执行选课信息查询*/
			List<StudentSelectCourseInfo> studentSelectCourseInfoList = studentSelectCourseInfoDAO.QueryStudentSelectCourseInfo(studentNumber,courseNumber);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加选课信息：获取选课信息参数，参数保存到新建的选课信息对象 */ 
			StudentSelectCourseInfo studentSelectCourseInfo = new StudentSelectCourseInfo();
			int selectId = Integer.parseInt(request.getParameter("selectId"));
			studentSelectCourseInfo.setSelectId(selectId);
			String studentNumber = new String(request.getParameter("studentNumber").getBytes("iso-8859-1"), "UTF-8");
			studentSelectCourseInfo.setStudentNumber(studentNumber);
			String courseNumber = new String(request.getParameter("courseNumber").getBytes("iso-8859-1"), "UTF-8");
			studentSelectCourseInfo.setCourseNumber(courseNumber);

			/* 调用业务层执行添加操作 */
			String result = studentSelectCourseInfoDAO.AddStudentSelectCourseInfo(studentSelectCourseInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除选课信息：获取选课信息的记录编号*/
			int selectId = Integer.parseInt(request.getParameter("selectId"));
			/*调用业务逻辑层执行删除操作*/
			String result = studentSelectCourseInfoDAO.DeleteStudentSelectCourseInfo(selectId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新选课信息之前先根据selectId查询某个选课信息*/
			int selectId = Integer.parseInt(request.getParameter("selectId"));
			StudentSelectCourseInfo studentSelectCourseInfo = studentSelectCourseInfoDAO.GetStudentSelectCourseInfo(selectId);

			// 客户端查询的选课信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新选课信息：获取选课信息参数，参数保存到新建的选课信息对象 */ 
			StudentSelectCourseInfo studentSelectCourseInfo = new StudentSelectCourseInfo();
			int selectId = Integer.parseInt(request.getParameter("selectId"));
			studentSelectCourseInfo.setSelectId(selectId);
			String studentNumber = new String(request.getParameter("studentNumber").getBytes("iso-8859-1"), "UTF-8");
			studentSelectCourseInfo.setStudentNumber(studentNumber);
			String courseNumber = new String(request.getParameter("courseNumber").getBytes("iso-8859-1"), "UTF-8");
			studentSelectCourseInfo.setCourseNumber(courseNumber);

			/* 调用业务层执行更新操作 */
			String result = studentSelectCourseInfoDAO.UpdateStudentSelectCourseInfo(studentSelectCourseInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
