package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.NewsDAO;
import com.mobileserver.domain.News;

import org.json.JSONStringer;

public class NewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����������Ϣҵ������*/
	private NewsDAO newsDAO = new NewsDAO();

	/*Ĭ�Ϲ��캯��*/
	public NewsServlet() {
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
			/*��ȡ��ѯ������Ϣ�Ĳ�����Ϣ*/
			String newsTitle = request.getParameter("newsTitle");
			newsTitle = newsTitle == null ? "" : new String(request.getParameter(
					"newsTitle").getBytes("iso-8859-1"), "UTF-8");
			Timestamp newsDate = null;
			if (request.getParameter("newsDate") != null)
				newsDate = Timestamp.valueOf(request.getParameter("newsDate"));

			/*����ҵ���߼���ִ��������Ϣ��ѯ*/
			List<News> newsList = newsDAO.QueryNews(newsTitle,newsDate);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Newss>").append("\r\n");
			for (int i = 0; i < newsList.size(); i++) {
				sb.append("	<News>").append("\r\n")
				.append("		<newsId>")
				.append(newsList.get(i).getNewsId())
				.append("</newsId>").append("\r\n")
				.append("		<newsTitle>")
				.append(newsList.get(i).getNewsTitle())
				.append("</newsTitle>").append("\r\n")
				.append("		<newsContent>")
				.append(newsList.get(i).getNewsContent())
				.append("</newsContent>").append("\r\n")
				.append("		<newsDate>")
				.append(newsList.get(i).getNewsDate())
				.append("</newsDate>").append("\r\n")
				.append("		<newsPhoto>")
				.append(newsList.get(i).getNewsPhoto())
				.append("</newsPhoto>").append("\r\n")
				.append("	</News>").append("\r\n");
			}
			sb.append("</Newss>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(News news: newsList) {
				  stringer.object();
			  stringer.key("newsId").value(news.getNewsId());
			  stringer.key("newsTitle").value(news.getNewsTitle());
			  stringer.key("newsContent").value(news.getNewsContent());
			  stringer.key("newsDate").value(news.getNewsDate());
			  stringer.key("newsPhoto").value(news.getNewsPhoto());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���������Ϣ����ȡ������Ϣ�������������浽�½���������Ϣ���� */ 
			News news = new News();
			int newsId = Integer.parseInt(request.getParameter("newsId"));
			news.setNewsId(newsId);
			String newsTitle = new String(request.getParameter("newsTitle").getBytes("iso-8859-1"), "UTF-8");
			news.setNewsTitle(newsTitle);
			String newsContent = new String(request.getParameter("newsContent").getBytes("iso-8859-1"), "UTF-8");
			news.setNewsContent(newsContent);
			Timestamp newsDate = Timestamp.valueOf(request.getParameter("newsDate"));
			news.setNewsDate(newsDate);
			String newsPhoto = new String(request.getParameter("newsPhoto").getBytes("iso-8859-1"), "UTF-8");
			news.setNewsPhoto(newsPhoto);

			/* ����ҵ���ִ����Ӳ��� */
			String result = newsDAO.AddNews(news);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��������Ϣ����ȡ������Ϣ�ļ�¼���*/
			int newsId = Integer.parseInt(request.getParameter("newsId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = newsDAO.DeleteNews(newsId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����������Ϣ֮ǰ�ȸ���newsId��ѯĳ��������Ϣ*/
			int newsId = Integer.parseInt(request.getParameter("newsId"));
			News news = newsDAO.GetNews(newsId);

			// �ͻ��˲�ѯ��������Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("newsId").value(news.getNewsId());
			  stringer.key("newsTitle").value(news.getNewsTitle());
			  stringer.key("newsContent").value(news.getNewsContent());
			  stringer.key("newsDate").value(news.getNewsDate());
			  stringer.key("newsPhoto").value(news.getNewsPhoto());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����������Ϣ����ȡ������Ϣ�������������浽�½���������Ϣ���� */ 
			News news = new News();
			int newsId = Integer.parseInt(request.getParameter("newsId"));
			news.setNewsId(newsId);
			String newsTitle = new String(request.getParameter("newsTitle").getBytes("iso-8859-1"), "UTF-8");
			news.setNewsTitle(newsTitle);
			String newsContent = new String(request.getParameter("newsContent").getBytes("iso-8859-1"), "UTF-8");
			news.setNewsContent(newsContent);
			Timestamp newsDate = Timestamp.valueOf(request.getParameter("newsDate"));
			news.setNewsDate(newsDate);
			String newsPhoto = new String(request.getParameter("newsPhoto").getBytes("iso-8859-1"), "UTF-8");
			news.setNewsPhoto(newsPhoto);

			/* ����ҵ���ִ�и��²��� */
			String result = newsDAO.UpdateNews(news);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
