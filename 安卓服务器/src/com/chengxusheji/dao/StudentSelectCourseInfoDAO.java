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
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
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

    /*�����ܵ�ҳ���ͼ�¼��*/
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

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public StudentSelectCourseInfo GetStudentSelectCourseInfoBySelectId(int selectId) {
        Session s = factory.getCurrentSession();
        StudentSelectCourseInfo studentSelectCourseInfo = (StudentSelectCourseInfo)s.get(StudentSelectCourseInfo.class, selectId);
        return studentSelectCourseInfo;
    }

    /*����StudentSelectCourseInfo��Ϣ*/
    public void UpdateStudentSelectCourseInfo(StudentSelectCourseInfo studentSelectCourseInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(studentSelectCourseInfo);
    }

    /*ɾ��StudentSelectCourseInfo��Ϣ*/
    public void DeleteStudentSelectCourseInfo (int selectId) throws Exception {
        Session s = factory.getCurrentSession();
        Object studentSelectCourseInfo = s.load(StudentSelectCourseInfo.class, selectId);
        s.delete(studentSelectCourseInfo);
    }

}
