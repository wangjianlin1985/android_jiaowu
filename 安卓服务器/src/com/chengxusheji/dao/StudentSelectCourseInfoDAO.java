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
import com.chengxusheji.domain.StudentSelectCourseInfo;

@Service @Transactional
public class StudentSelectCourseInfoDAO {

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
    public void AddStudentSelectCourseInfo(StudentSelectCourseInfo studentSelectCourseInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(studentSelectCourseInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<StudentSelectCourseInfo> QueryStudentSelectCourseInfoInfo(Student studentNumber,CourseInfo courseNumber,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From StudentSelectCourseInfo studentSelectCourseInfo where 1=1";
    	if(null != studentNumber && !studentNumber.getStudentNumber().equals("")) hql += " and studentSelectCourseInfo.studentNumber.studentNumber='" + studentNumber.getStudentNumber() + "'";
    	if(null != courseNumber && !courseNumber.getCourseNumber().equals("")) hql += " and studentSelectCourseInfo.courseNumber.courseNumber='" + courseNumber.getCourseNumber() + "'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List studentSelectCourseInfoList = q.list();
    	return (ArrayList<StudentSelectCourseInfo>) studentSelectCourseInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<StudentSelectCourseInfo> QueryStudentSelectCourseInfoInfo(Student studentNumber,CourseInfo courseNumber) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From StudentSelectCourseInfo studentSelectCourseInfo where 1=1";
    	if(null != studentNumber && !studentNumber.getStudentNumber().equals("")) hql += " and studentSelectCourseInfo.studentNumber.studentNumber='" + studentNumber.getStudentNumber() + "'";
    	if(null != courseNumber && !courseNumber.getCourseNumber().equals("")) hql += " and studentSelectCourseInfo.courseNumber.courseNumber='" + courseNumber.getCourseNumber() + "'";
    	Query q = s.createQuery(hql);
    	List studentSelectCourseInfoList = q.list();
    	return (ArrayList<StudentSelectCourseInfo>) studentSelectCourseInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<StudentSelectCourseInfo> QueryAllStudentSelectCourseInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From StudentSelectCourseInfo";
        Query q = s.createQuery(hql);
        List studentSelectCourseInfoList = q.list();
        return (ArrayList<StudentSelectCourseInfo>) studentSelectCourseInfoList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Student studentNumber,CourseInfo courseNumber) {
        Session s = factory.getCurrentSession();
        String hql = "From StudentSelectCourseInfo studentSelectCourseInfo where 1=1";
        if(null != studentNumber && !studentNumber.getStudentNumber().equals("")) hql += " and studentSelectCourseInfo.studentNumber.studentNumber='" + studentNumber.getStudentNumber() + "'";
        if(null != courseNumber && !courseNumber.getCourseNumber().equals("")) hql += " and studentSelectCourseInfo.courseNumber.courseNumber='" + courseNumber.getCourseNumber() + "'";
        Query q = s.createQuery(hql);
        List studentSelectCourseInfoList = q.list();
        recordNumber = studentSelectCourseInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public StudentSelectCourseInfo GetStudentSelectCourseInfoBySelectId(int selectId) {
        Session s = factory.getCurrentSession();
        StudentSelectCourseInfo studentSelectCourseInfo = (StudentSelectCourseInfo)s.get(StudentSelectCourseInfo.class, selectId);
        return studentSelectCourseInfo;
    }

    /*更新StudentSelectCourseInfo信息*/
    public void UpdateStudentSelectCourseInfo(StudentSelectCourseInfo studentSelectCourseInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(studentSelectCourseInfo);
    }

    /*删除StudentSelectCourseInfo信息*/
    public void DeleteStudentSelectCourseInfo (int selectId) throws Exception {
        Session s = factory.getCurrentSession();
        Object studentSelectCourseInfo = s.load(StudentSelectCourseInfo.class, selectId);
        s.delete(studentSelectCourseInfo);
    }

}
