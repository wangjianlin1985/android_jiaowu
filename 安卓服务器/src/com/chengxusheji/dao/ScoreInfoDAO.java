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
import com.chengxusheji.domain.Student;
import com.chengxusheji.domain.CourseInfo;
import com.chengxusheji.domain.ScoreInfo;

@Service @Transactional
public class ScoreInfoDAO {

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
    public void AddScoreInfo(ScoreInfo scoreInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(scoreInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ScoreInfo> QueryScoreInfoInfo(Student studentNumber,CourseInfo courseNumber,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ScoreInfo scoreInfo where 1=1";
    	if(null != studentNumber && !studentNumber.getStudentNumber().equals("")) hql += " and scoreInfo.studentNumber.studentNumber='" + studentNumber.getStudentNumber() + "'";
    	if(null != courseNumber && !courseNumber.getCourseNumber().equals("")) hql += " and scoreInfo.courseNumber.courseNumber='" + courseNumber.getCourseNumber() + "'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List scoreInfoList = q.list();
    	return (ArrayList<ScoreInfo>) scoreInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ScoreInfo> QueryScoreInfoInfo(Student studentNumber,CourseInfo courseNumber) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ScoreInfo scoreInfo where 1=1";
    	if(null != studentNumber && !studentNumber.getStudentNumber().equals("")) hql += " and scoreInfo.studentNumber.studentNumber='" + studentNumber.getStudentNumber() + "'";
    	if(null != courseNumber && !courseNumber.getCourseNumber().equals("")) hql += " and scoreInfo.courseNumber.courseNumber='" + courseNumber.getCourseNumber() + "'";
    	Query q = s.createQuery(hql);
    	List scoreInfoList = q.list();
    	return (ArrayList<ScoreInfo>) scoreInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ScoreInfo> QueryAllScoreInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From ScoreInfo";
        Query q = s.createQuery(hql);
        List scoreInfoList = q.list();
        return (ArrayList<ScoreInfo>) scoreInfoList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Student studentNumber,CourseInfo courseNumber) {
        Session s = factory.getCurrentSession();
        String hql = "From ScoreInfo scoreInfo where 1=1";
        if(null != studentNumber && !studentNumber.getStudentNumber().equals("")) hql += " and scoreInfo.studentNumber.studentNumber='" + studentNumber.getStudentNumber() + "'";
        if(null != courseNumber && !courseNumber.getCourseNumber().equals("")) hql += " and scoreInfo.courseNumber.courseNumber='" + courseNumber.getCourseNumber() + "'";
        Query q = s.createQuery(hql);
        List scoreInfoList = q.list();
        recordNumber = scoreInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ScoreInfo GetScoreInfoByScoreId(int scoreId) {
        Session s = factory.getCurrentSession();
        ScoreInfo scoreInfo = (ScoreInfo)s.get(ScoreInfo.class, scoreId);
        return scoreInfo;
    }

    /*����ScoreInfo��Ϣ*/
    public void UpdateScoreInfo(ScoreInfo scoreInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(scoreInfo);
    }

    /*ɾ��ScoreInfo��Ϣ*/
    public void DeleteScoreInfo (int scoreId) throws Exception {
        Session s = factory.getCurrentSession();
        Object scoreInfo = s.load(ScoreInfo.class, scoreId);
        s.delete(scoreInfo);
    }

}
