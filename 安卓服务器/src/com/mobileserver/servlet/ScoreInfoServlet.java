package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.ScoreInfoDAO;
import com.mobileserver.domain.ScoreInfo;

import org.json.JSONStringer;

public class ScoreInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造成绩信息业务层对象*/
	private ScoreInfoDAO scoreInfoDAO = new ScoreInfoDAO();

	/*默认构造函数*/
	public ScoreInfoServlet() {
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
			/*获取查询成绩信息的参数信息*/
			String studentNumber = "";
			if (request.getParameter("studentNumber") != null)
				studentNumber = request.getParameter("studentNumber");
			String courseNumber = "";
			if (request.getParameter("courseNumber") != null)
				courseNumber = request.getParameter("courseNumber");

			/*调用业务逻辑层执行成绩信息查询*/
			List<ScoreInfo> scoreInfoList = scoreInfoDAO.QueryScoreInfo(studentNumber,courseNumber);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<ScoreInfos>").append("\r\n");
			for (int i = 0; i < scoreInfoList.size(); i++) {
				sb.append("	<ScoreInfo>").append("\r\n")
				.append("		<scoreId>")
				.append(scoreInfoList.get(i).getScoreId())
				.append("</scoreId>").append("\r\n")
				.append("		<studentNumber>")
				.append(scoreInfoList.get(i).getStudentNumber())
				.append("</studentNumber>").append("\r\n")
				.append("		<courseNumber>")
				.append(scoreInfoList.get(i).getCourseNumber())
				.append("</courseNumber>").append("\r\n")
				.append("		<scoreValue>")
				.append(scoreInfoList.get(i).getScoreValue())
				.append("</scoreValue>").append("\r\n")
				.append("		<studentEvaluate>")
				.append(scoreInfoList.get(i).getStudentEvaluate())
				.append("</studentEvaluate>").append("\r\n")
				.append("	</ScoreInfo>").append("\r\n");
			}
			sb.append("</ScoreInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(ScoreInfo scoreInfo: scoreInfoList) {
				  stringer.object();
			  stringer.key("scoreId").value(scoreInfo.getScoreId());
			  stringer.key("studentNumber").value(scoreInfo.getStudentNumber());
			  stringer.key("courseNumber").value(scoreInfo.getCourseNumber());
			  stringer.key("scoreValue").value(scoreInfo.getScoreValue());
			  stringer.key("studentEvaluate").value(scoreInfo.getStudentEvaluate());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加成绩信息：获取成绩信息参数，参数保存到新建的成绩信息对象 */ 
			ScoreInfo scoreInfo = new ScoreInfo();
			int scoreId = Integer.parseInt(request.getParameter("scoreId"));
			scoreInfo.setScoreId(scoreId);
			String studentNumber = new String(request.getParameter("studentNumber").getBytes("iso-8859-1"), "UTF-8");
			scoreInfo.setStudentNumber(studentNumber);
			String courseNumber = new String(request.getParameter("courseNumber").getBytes("iso-8859-1"), "UTF-8");
			scoreInfo.setCourseNumber(courseNumber);
			float scoreValue = Float.parseFloat(request.getParameter("scoreValue"));
			scoreInfo.setScoreValue(scoreValue);
			String studentEvaluate = new String(request.getParameter("studentEvaluate").getBytes("iso-8859-1"), "UTF-8");
			scoreInfo.setStudentEvaluate(studentEvaluate);

			/* 调用业务层执行添加操作 */
			String result = scoreInfoDAO.AddScoreInfo(scoreInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除成绩信息：获取成绩信息的记录编号*/
			int scoreId = Integer.parseInt(request.getParameter("scoreId"));
			/*调用业务逻辑层执行删除操作*/
			String result = scoreInfoDAO.DeleteScoreInfo(scoreId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新成绩信息之前先根据scoreId查询某个成绩信息*/
			int scoreId = Integer.parseInt(request.getParameter("scoreId"));
			ScoreInfo scoreInfo = scoreInfoDAO.GetScoreInfo(scoreId);

			// 客户端查询的成绩信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("scoreId").value(scoreInfo.getScoreId());
			  stringer.key("studentNumber").value(scoreInfo.getStudentNumber());
			  stringer.key("courseNumber").value(scoreInfo.getCourseNumber());
			  stringer.key("scoreValue").value(scoreInfo.getScoreValue());
			  stringer.key("studentEvaluate").value(scoreInfo.getStudentEvaluate());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新成绩信息：获取成绩信息参数，参数保存到新建的成绩信息对象 */ 
			ScoreInfo scoreInfo = new ScoreInfo();
			int scoreId = Integer.parseInt(request.getParameter("scoreId"));
			scoreInfo.setScoreId(scoreId);
			String studentNumber = new String(request.getParameter("studentNumber").getBytes("iso-8859-1"), "UTF-8");
			scoreInfo.setStudentNumber(studentNumber);
			String courseNumber = new String(request.getParameter("courseNumber").getBytes("iso-8859-1"), "UTF-8");
			scoreInfo.setCourseNumber(courseNumber);
			float scoreValue = Float.parseFloat(request.getParameter("scoreValue"));
			scoreInfo.setScoreValue(scoreValue);
			String studentEvaluate = new String(request.getParameter("studentEvaluate").getBytes("iso-8859-1"), "UTF-8");
			scoreInfo.setStudentEvaluate(studentEvaluate);

			/* 调用业务层执行更新操作 */
			String result = scoreInfoDAO.UpdateScoreInfo(scoreInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
