package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.News;
import com.mobileserver.util.DB;

public class NewsDAO {

	public List<News> QueryNews(String newsTitle,Timestamp newsDate) {
		List<News> newsList = new ArrayList<News>();
		DB db = new DB();
		String sql = "select * from News where 1=1";
		if (!newsTitle.equals(""))
			sql += " and newsTitle like '%" + newsTitle + "%'";
		if(newsDate!=null)
			sql += " and newsDate='" + newsDate + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				News news = new News();
				news.setNewsId(rs.getInt("newsId"));
				news.setNewsTitle(rs.getString("newsTitle"));
				news.setNewsContent(rs.getString("newsContent"));
				news.setNewsDate(rs.getTimestamp("newsDate"));
				news.setNewsPhoto(rs.getString("newsPhoto"));
				newsList.add(news);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return newsList;
	}
	/* 传入新闻信息对象，进行新闻信息的添加业务 */
	public String AddNews(News news) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新新闻信息 */
			String sqlString = "insert into News(newsTitle,newsContent,newsDate,newsPhoto) values (";
			sqlString += "'" + news.getNewsTitle() + "',";
			sqlString += "'" + news.getNewsContent() + "',";
			sqlString += "'" + news.getNewsDate() + "',";
			sqlString += "'" + news.getNewsPhoto() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "新闻信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "新闻信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除新闻信息 */
	public String DeleteNews(int newsId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from News where newsId=" + newsId;
			db.executeUpdate(sqlString);
			result = "新闻信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "新闻信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到新闻信息 */
	public News GetNews(int newsId) {
		News news = null;
		DB db = new DB();
		String sql = "select * from News where newsId=" + newsId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				news = new News();
				news.setNewsId(rs.getInt("newsId"));
				news.setNewsTitle(rs.getString("newsTitle"));
				news.setNewsContent(rs.getString("newsContent"));
				news.setNewsDate(rs.getTimestamp("newsDate"));
				news.setNewsPhoto(rs.getString("newsPhoto"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return news;
	}
	/* 更新新闻信息 */
	public String UpdateNews(News news) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update News set ";
			sql += "newsTitle='" + news.getNewsTitle() + "',";
			sql += "newsContent='" + news.getNewsContent() + "',";
			sql += "newsDate='" + news.getNewsDate() + "',";
			sql += "newsPhoto='" + news.getNewsPhoto() + "'";
			sql += " where newsId=" + news.getNewsId();
			db.executeUpdate(sql);
			result = "新闻信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "新闻信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
