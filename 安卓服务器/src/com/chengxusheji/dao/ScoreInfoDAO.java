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
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
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
    	/*计算当前显示页码的开始记录*/
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

    /*计算总的页数和记录数*/
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

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ScoreInfo GetScoreInfoByScoreId(int scoreId) {
        Session s = factory.getCurrentSession();
        ScoreInfo scoreInfo = (ScoreInfo)s.get(ScoreInfo.class, scoreId);
        return scoreInfo;
    }

    /*更新ScoreInfo信息*/
    public void UpdateScoreInfo(ScoreInfo scoreInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(scoreInfo);
    }

    /*删除ScoreInfo信息*/
    public void DeleteScoreInfo (int scoreId) throws Exception {
        Session s = factory.getCurrentSession();
        Object scoreInfo = s.load(ScoreInfo.class, scoreId);
        s.delete(scoreInfo);
    }

}
