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
import com.chengxusheji.domain.Teacher;

@Service @Transactional
public class TeacherDAO {

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
    public void AddTeacher(Teacher teacher) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(teacher);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Teacher> QueryTeacherInfo(String teacherNumber,String teacherName,String teacherBirthday,String teacherArriveDate,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Teacher teacher where 1=1";
    	if(!teacherNumber.equals("")) hql = hql + " and teacher.teacherNumber like '%" + teacherNumber + "%'";
    	if(!teacherName.equals("")) hql = hql + " and teacher.teacherName like '%" + teacherName + "%'";
    	if(!teacherBirthday.equals("")) hql = hql + " and teacher.teacherBirthday like '%" + teacherBirthday + "%'";
    	if(!teacherArriveDate.equals("")) hql = hql + " and teacher.teacherArriveDate like '%" + teacherArriveDate + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List teacherList = q.list();
    	return (ArrayList<Teacher>) teacherList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Teacher> QueryTeacherInfo(String teacherNumber,String teacherName,String teacherBirthday,String teacherArriveDate) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Teacher teacher where 1=1";
    	if(!teacherNumber.equals("")) hql = hql + " and teacher.teacherNumber like '%" + teacherNumber + "%'";
    	if(!teacherName.equals("")) hql = hql + " and teacher.teacherName like '%" + teacherName + "%'";
    	if(!teacherBirthday.equals("")) hql = hql + " and teacher.teacherBirthday like '%" + teacherBirthday + "%'";
    	if(!teacherArriveDate.equals("")) hql = hql + " and teacher.teacherArriveDate like '%" + teacherArriveDate + "%'";
    	Query q = s.createQuery(hql);
    	List teacherList = q.list();
    	return (ArrayList<Teacher>) teacherList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Teacher> QueryAllTeacherInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Teacher";
        Query q = s.createQuery(hql);
        List teacherList = q.list();
        return (ArrayList<Teacher>) teacherList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String teacherNumber,String teacherName,String teacherBirthday,String teacherArriveDate) {
        Session s = factory.getCurrentSession();
        String hql = "From Teacher teacher where 1=1";
        if(!teacherNumber.equals("")) hql = hql + " and teacher.teacherNumber like '%" + teacherNumber + "%'";
        if(!teacherName.equals("")) hql = hql + " and teacher.teacherName like '%" + teacherName + "%'";
        if(!teacherBirthday.equals("")) hql = hql + " and teacher.teacherBirthday like '%" + teacherBirthday + "%'";
        if(!teacherArriveDate.equals("")) hql = hql + " and teacher.teacherArriveDate like '%" + teacherArriveDate + "%'";
        Query q = s.createQuery(hql);
        List teacherList = q.list();
        recordNumber = teacherList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Teacher GetTeacherByTeacherNumber(String teacherNumber) {
        Session s = factory.getCurrentSession();
        Teacher teacher = (Teacher)s.get(Teacher.class, teacherNumber);
        return teacher;
    }

    /*����Teacher��Ϣ*/
    public void UpdateTeacher(Teacher teacher) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(teacher);
    }

    /*ɾ��Teacher��Ϣ*/
    public void DeleteTeacher (String teacherNumber) throws Exception {
        Session s = factory.getCurrentSession();
        Object teacher = s.load(Teacher.class, teacherNumber);
        s.delete(teacher);
    }

}
