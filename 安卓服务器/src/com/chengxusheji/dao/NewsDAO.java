package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.News;

@Service @Transactional
public class NewsDAO {

	@Resource SessionFactory factory;
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
    public void AddNews(News news) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(news);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<News> QueryNewsInfo(String newsTitle,String newsDate,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From News news where 1=1";
    	if(!newsTitle.equals("")) hql = hql + " and news.newsTitle like '%" + newsTitle + "%'";
    	if(!newsDate.equals("")) hql = hql + " and news.newsDate like '%" + newsDate + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List newsList = q.list();
    	return (ArrayList<News>) newsList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<News> QueryNewsInfo(String newsTitle,String newsDate) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From News news where 1=1";
    	if(!newsTitle.equals("")) hql = hql + " and news.newsTitle like '%" + newsTitle + "%'";
    	if(!newsDate.equals("")) hql = hql + " and news.newsDate like '%" + newsDate + "%'";
    	Query q = s.createQuery(hql);
    	List newsList = q.list();
    	return (ArrayList<News>) newsList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<News> QueryAllNewsInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From News";
        Query q = s.createQuery(hql);
        List newsList = q.list();
        return (ArrayList<News>) newsList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String newsTitle,String newsDate) {
        Session s = factory.getCurrentSession();
        String hql = "From News news where 1=1";
        if(!newsTitle.equals("")) hql = hql + " and news.newsTitle like '%" + newsTitle + "%'";
        if(!newsDate.equals("")) hql = hql + " and news.newsDate like '%" + newsDate + "%'";
        Query q = s.createQuery(hql);
        List newsList = q.list();
        recordNumber = newsList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public News GetNewsByNewsId(int newsId) {
        Session s = factory.getCurrentSession();
        News news = (News)s.get(News.class, newsId);
        return news;
    }

    /*����News��Ϣ*/
    public void UpdateNews(News news) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(news);
    }

    /*ɾ��News��Ϣ*/
    public void DeleteNews (int newsId) throws Exception {
        Session s = factory.getCurrentSession();
        Object news = s.load(News.class, newsId);
        s.delete(news);
    }

}
