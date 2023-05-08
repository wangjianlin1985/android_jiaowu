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
import com.chengxusheji.domain.CollegeInfo;
import com.chengxusheji.domain.SpecialFieldInfo;

@Service @Transactional
public class SpecialFieldInfoDAO {

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
    public void AddSpecialFieldInfo(SpecialFieldInfo specialFieldInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(specialFieldInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SpecialFieldInfo> QuerySpecialFieldInfoInfo(String specialFieldNumber,String specialFieldName,CollegeInfo specialCollegeNumber,String specialBirthDate,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SpecialFieldInfo specialFieldInfo where 1=1";
    	if(!specialFieldNumber.equals("")) hql = hql + " and specialFieldInfo.specialFieldNumber like '%" + specialFieldNumber + "%'";
    	if(!specialFieldName.equals("")) hql = hql + " and specialFieldInfo.specialFieldName like '%" + specialFieldName + "%'";
    	if(null != specialCollegeNumber && !specialCollegeNumber.getCollegeNumber().equals("")) hql += " and specialFieldInfo.specialCollegeNumber.collegeNumber='" + specialCollegeNumber.getCollegeNumber() + "'";
    	if(!specialBirthDate.equals("")) hql = hql + " and specialFieldInfo.specialBirthDate like '%" + specialBirthDate + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List specialFieldInfoList = q.list();
    	return (ArrayList<SpecialFieldInfo>) specialFieldInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SpecialFieldInfo> QuerySpecialFieldInfoInfo(String specialFieldNumber,String specialFieldName,CollegeInfo specialCollegeNumber,String specialBirthDate) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SpecialFieldInfo specialFieldInfo where 1=1";
    	if(!specialFieldNumber.equals("")) hql = hql + " and specialFieldInfo.specialFieldNumber like '%" + specialFieldNumber + "%'";
    	if(!specialFieldName.equals("")) hql = hql + " and specialFieldInfo.specialFieldName like '%" + specialFieldName + "%'";
    	if(null != specialCollegeNumber && !specialCollegeNumber.getCollegeNumber().equals("")) hql += " and specialFieldInfo.specialCollegeNumber.collegeNumber='" + specialCollegeNumber.getCollegeNumber() + "'";
    	if(!specialBirthDate.equals("")) hql = hql + " and specialFieldInfo.specialBirthDate like '%" + specialBirthDate + "%'";
    	Query q = s.createQuery(hql);
    	List specialFieldInfoList = q.list();
    	return (ArrayList<SpecialFieldInfo>) specialFieldInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SpecialFieldInfo> QueryAllSpecialFieldInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From SpecialFieldInfo";
        Query q = s.createQuery(hql);
        List specialFieldInfoList = q.list();
        return (ArrayList<SpecialFieldInfo>) specialFieldInfoList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String specialFieldNumber,String specialFieldName,CollegeInfo specialCollegeNumber,String specialBirthDate) {
        Session s = factory.getCurrentSession();
        String hql = "From SpecialFieldInfo specialFieldInfo where 1=1";
        if(!specialFieldNumber.equals("")) hql = hql + " and specialFieldInfo.specialFieldNumber like '%" + specialFieldNumber + "%'";
        if(!specialFieldName.equals("")) hql = hql + " and specialFieldInfo.specialFieldName like '%" + specialFieldName + "%'";
        if(null != specialCollegeNumber && !specialCollegeNumber.getCollegeNumber().equals("")) hql += " and specialFieldInfo.specialCollegeNumber.collegeNumber='" + specialCollegeNumber.getCollegeNumber() + "'";
        if(!specialBirthDate.equals("")) hql = hql + " and specialFieldInfo.specialBirthDate like '%" + specialBirthDate + "%'";
        Query q = s.createQuery(hql);
        List specialFieldInfoList = q.list();
        recordNumber = specialFieldInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public SpecialFieldInfo GetSpecialFieldInfoBySpecialFieldNumber(String specialFieldNumber) {
        Session s = factory.getCurrentSession();
        SpecialFieldInfo specialFieldInfo = (SpecialFieldInfo)s.get(SpecialFieldInfo.class, specialFieldNumber);
        return specialFieldInfo;
    }

    /*����SpecialFieldInfo��Ϣ*/
    public void UpdateSpecialFieldInfo(SpecialFieldInfo specialFieldInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(specialFieldInfo);
    }

    /*ɾ��SpecialFieldInfo��Ϣ*/
    public void DeleteSpecialFieldInfo (String specialFieldNumber) throws Exception {
        Session s = factory.getCurrentSession();
        Object specialFieldInfo = s.load(SpecialFieldInfo.class, specialFieldNumber);
        s.delete(specialFieldInfo);
    }

}
