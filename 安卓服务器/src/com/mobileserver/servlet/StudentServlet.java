package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.StudentDAO;
import com.mobileserver.domain.Student;

import org.json.JSONStringer;

public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造学生信息业务层对象*/
	private StudentDAO studentDAO = new StudentDAO();

	/*默认构造函数*/
	public StudentServlet() {
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
			/*获取查询学生信息的参数信息*/
			String studentNumber = request.getParameter("studentNumber");
			studentNumber = studentNumber == null ? "" : new String(request.getParameter(
					"studentNumber").getBytes("iso-8859-1"), "UTF-8");
			String studentName = request.getParameter("studentName");
			studentName = studentName == null ? "" : new String(request.getParameter(
					"studentName").getBytes("iso-8859-1"), "UTF-8");
			String studentClassNumber = "";
			if (request.getParameter("studentClassNumber") != null)
				studentClassNumber = request.getParameter("studentClassNumber");
			Timestamp studentBirthday = null;
			if (request.getParameter("studentBirthday") != null)
				studentBirthday = Timestamp.valueOf(request.getParameter("studentBirthday"));

			/*调用业务逻辑层执行学生信息查询*/
			List<Student> studentList = studentDAO.QueryStudent(studentNumber,studentName,studentClassNumber,studentBirthday);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Students>").append("\r\n");
			for (int i = 0; i < studentList.size(); i++) {
				sb.append("	<Student>").append("\r\n")
				.append("		<studentNumber>")
				.append(studentList.get(i).getStudentNumber())
				.append("</studentNumber>").append("\r\n")
				.append("		<studentName>")
				.append(studentList.get(i).getStudentName())
				.append("</studentName>").append("\r\n")
				.append("		<studentPassword>")
				.append(studentList.get(i).getStudentPassword())
				.append("</studentPassword>").append("\r\n")
				.append("		<studentSex>")
				.append(studentList.get(i).getStudentSex())
				.append("</studentSex>").append("\r\n")
				.append("		<studentClassNumber>")
				.append(studentList.get(i).getStudentClassNumber())
				.append("</studentClassNumber>").append("\r\n")
				.append("		<studentBirthday>")
				.append(studentList.get(i).getStudentBirthday())
				.append("</studentBirthday>").append("\r\n")
				.append("		<studentState>")
				.append(studentList.get(i).getStudentState())
				.append("</studentState>").append("\r\n")
				.append("		<studentPhoto>")
				.append(studentList.get(i).getStudentPhoto())
				.append("</studentPhoto>").append("\r\n")
				.append("		<studentTelephone>")
				.append(studentList.get(i).getStudentTelephone())
				.append("</studentTelephone>").append("\r\n")
				.append("		<studentEmail>")
				.append(studentList.get(i).getStudentEmail())
				.append("</studentEmail>").append("\r\n")
				.append("		<studentQQ>")
				.append(studentList.get(i).getStudentQQ())
				.append("</studentQQ>").append("\r\n")
				.append("		<studentAddress>")
				.append(studentList.get(i).getStudentAddress())
				.append("</studentAddress>").append("\r\n")
				.append("		<studentMemo>")
				.append(studentList.get(i).getStudentMemo())
				.append("</studentMemo>").append("\r\n")
				.append("	</Student>").append("\r\n");
			}
			sb.append("</Students>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Student student: studentList) {
				  stringer.object();
			  stringer.key("studentNumber").value(student.getStudentNumber());
			  stringer.key("studentName").value(student.getStudentName());
			  stringer.key("studentPassword").value(student.getStudentPassword());
			  stringer.key("studentSex").value(student.getStudentSex());
			  stringer.key("studentClassNumber").value(student.getStudentClassNumber());
			  stringer.key("studentBirthday").value(student.getStudentBirthday());
			  stringer.key("studentState").value(student.getStudentState());
			  stringer.key("studentPhoto").value(student.getStudentPhoto());
			  stringer.key("studentTelephone").value(student.getStudentTelephone());
			  stringer.key("studentEmail").value(student.getStudentEmail());
			  stringer.key("studentQQ").value(student.getStudentQQ());
			  stringer.key("studentAddress").value(student.getStudentAddress());
			  stringer.key("studentMemo").value(student.getStudentMemo());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加学生信息：获取学生信息参数，参数保存到新建的学生信息对象 */ 
			Student student = new Student();
			String studentNumber = new String(request.getParameter("studentNumber").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentNumber(studentNumber);
			String studentName = new String(request.getParameter("studentName").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentName(studentName);
			String studentPassword = new String(request.getParameter("studentPassword").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentPassword(studentPassword);
			String studentSex = new String(request.getParameter("studentSex").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentSex(studentSex);
			String studentClassNumber = new String(request.getParameter("studentClassNumber").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentClassNumber(studentClassNumber);
			Timestamp studentBirthday = Timestamp.valueOf(request.getParameter("studentBirthday"));
			student.setStudentBirthday(studentBirthday);
			String studentState = new String(request.getParameter("studentState").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentState(studentState);
			String studentPhoto = new String(request.getParameter("studentPhoto").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentPhoto(studentPhoto);
			String studentTelephone = new String(request.getParameter("studentTelephone").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentTelephone(studentTelephone);
			String studentEmail = new String(request.getParameter("studentEmail").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentEmail(studentEmail);
			String studentQQ = new String(request.getParameter("studentQQ").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentQQ(studentQQ);
			String studentAddress = new String(request.getParameter("studentAddress").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentAddress(studentAddress);
			String studentMemo = new String(request.getParameter("studentMemo").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentMemo(studentMemo);

			/* 调用业务层执行添加操作 */
			String result = studentDAO.AddStudent(student);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除学生信息：获取学生信息的学号*/
			String studentNumber = new String(request.getParameter("studentNumber").getBytes("iso-8859-1"), "UTF-8");
			/*调用业务逻辑层执行删除操作*/
			String result = studentDAO.DeleteStudent(studentNumber);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新学生信息之前先根据studentNumber查询某个学生信息*/
			String studentNumber = new String(request.getParameter("studentNumber").getBytes("iso-8859-1"), "UTF-8");
			Student student = studentDAO.GetStudent(studentNumber);

			// 客户端查询的学生信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("studentNumber").value(student.getStudentNumber());
			  stringer.key("studentName").value(student.getStudentName());
			  stringer.key("studentPassword").value(student.getStudentPassword());
			  stringer.key("studentSex").value(student.getStudentSex());
			  stringer.key("studentClassNumber").value(student.getStudentClassNumber());
			  stringer.key("studentBirthday").value(student.getStudentBirthday());
			  stringer.key("studentState").value(student.getStudentState());
			  stringer.key("studentPhoto").value(student.getStudentPhoto());
			  stringer.key("studentTelephone").value(student.getStudentTelephone());
			  stringer.key("studentEmail").value(student.getStudentEmail());
			  stringer.key("studentQQ").value(student.getStudentQQ());
			  stringer.key("studentAddress").value(student.getStudentAddress());
			  stringer.key("studentMemo").value(student.getStudentMemo());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新学生信息：获取学生信息参数，参数保存到新建的学生信息对象 */ 
			Student student = new Student();
			String studentNumber = new String(request.getParameter("studentNumber").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentNumber(studentNumber);
			String studentName = new String(request.getParameter("studentName").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentName(studentName);
			String studentPassword = new String(request.getParameter("studentPassword").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentPassword(studentPassword);
			String studentSex = new String(request.getParameter("studentSex").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentSex(studentSex);
			String studentClassNumber = new String(request.getParameter("studentClassNumber").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentClassNumber(studentClassNumber);
			Timestamp studentBirthday = Timestamp.valueOf(request.getParameter("studentBirthday"));
			student.setStudentBirthday(studentBirthday);
			String studentState = new String(request.getParameter("studentState").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentState(studentState);
			String studentPhoto = new String(request.getParameter("studentPhoto").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentPhoto(studentPhoto);
			String studentTelephone = new String(request.getParameter("studentTelephone").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentTelephone(studentTelephone);
			String studentEmail = new String(request.getParameter("studentEmail").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentEmail(studentEmail);
			String studentQQ = new String(request.getParameter("studentQQ").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentQQ(studentQQ);
			String studentAddress = new String(request.getParameter("studentAddress").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentAddress(studentAddress);
			String studentMemo = new String(request.getParameter("studentMemo").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentMemo(studentMemo);

			/* 调用业务层执行更新操作 */
			String result = studentDAO.UpdateStudent(student);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
