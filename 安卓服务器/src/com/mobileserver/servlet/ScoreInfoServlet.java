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

	/*����ɼ���Ϣҵ������*/
	private ScoreInfoDAO scoreInfoDAO = new ScoreInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public ScoreInfoServlet() {
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
			/*��ȡ��ѯ�ɼ���Ϣ�Ĳ�����Ϣ*/
			String studentNumber = "";
			if (request.getParameter("studentNumber") != null)
				studentNumber = request.getParameter("studentNumber");
			String courseNumber = "";
			if (request.getParameter("courseNumber") != null)
				courseNumber = request.getParameter("courseNumber");

			/*����ҵ���߼���ִ�гɼ���Ϣ��ѯ*/
			List<ScoreInfo> scoreInfoList = scoreInfoDAO.QueryScoreInfo(studentNumber,courseNumber);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӳɼ���Ϣ����ȡ�ɼ���Ϣ�������������浽�½��ĳɼ���Ϣ���� */ 
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

			/* ����ҵ���ִ����Ӳ��� */
			String result = scoreInfoDAO.AddScoreInfo(scoreInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ���ɼ���Ϣ����ȡ�ɼ���Ϣ�ļ�¼���*/
			int scoreId = Integer.parseInt(request.getParameter("scoreId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = scoreInfoDAO.DeleteScoreInfo(scoreId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���³ɼ���Ϣ֮ǰ�ȸ���scoreId��ѯĳ���ɼ���Ϣ*/
			int scoreId = Integer.parseInt(request.getParameter("scoreId"));
			ScoreInfo scoreInfo = scoreInfoDAO.GetScoreInfo(scoreId);

			// �ͻ��˲�ѯ�ĳɼ���Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���³ɼ���Ϣ����ȡ�ɼ���Ϣ�������������浽�½��ĳɼ���Ϣ���� */ 
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

			/* ����ҵ���ִ�и��²��� */
			String result = scoreInfoDAO.UpdateScoreInfo(scoreInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
