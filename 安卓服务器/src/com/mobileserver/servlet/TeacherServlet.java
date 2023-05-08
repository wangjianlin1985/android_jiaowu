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

	/*构造教师信息业务层对象*/
	private TeacherDAO teacherDAO = new TeacherDAO();

	/*默认构造函数*/
	public TeacherServlet() {
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
			/*获取查询教师信息的参数信息*/
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

			/*调用业务逻辑层执行教师信息查询*/
			List<Teacher> teacherList = teacherDAO.QueryTeacher(teacherNumber,teacherName,teacherBirthday,teacherArriveDate);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加教师信息：获取教师信息参数，参数保存到新建的教师信息对象 */ 
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

			/* 调用业务层执行添加操作 */
			String result = teacherDAO.AddTeacher(teacher);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除教师信息：获取教师信息的教师编号*/
			String teacherNumber = new String(request.getParameter("teacherNumber").getBytes("iso-8859-1"), "UTF-8");
			/*调用业务逻辑层执行删除操作*/
			String result = teacherDAO.DeleteTeacher(teacherNumber);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新教师信息之前先根据teacherNumber查询某个教师信息*/
			String teacherNumber = new String(request.getParameter("teacherNumber").getBytes("iso-8859-1"), "UTF-8");
			Teacher teacher = teacherDAO.GetTeacher(teacherNumber);

			// 客户端查询的教师信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新教师信息：获取教师信息参数，参数保存到新建的教师信息对象 */ 
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

			/* 调用业务层执行更新操作 */
			String result = teacherDAO.UpdateTeacher(teacher);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
