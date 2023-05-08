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

@Service @Transactional
public class CollegeInfoDAO {

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
    public void AddCollegeInfo(CollegeInfo collegeInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(collegeInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<CollegeInfo> QueryCollegeInfoInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From CollegeInfo collegeInfo where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List collegeInfoList = q.list();
    	return (ArrayList<CollegeInfo>) collegeInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<CollegeInfo> QueryCollegeInfoInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From CollegeInfo collegeInfo where 1=1";
    	Query q = s.createQuery(hql);
    	List collegeInfoList = q.list();
    	return (ArrayList<CollegeInfo>) collegeInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<CollegeInfo> QueryAllCollegeInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From CollegeInfo";
        Query q = s.createQuery(hql);
        List collegeInfoList = q.list();
        return (ArrayList<CollegeInfo>) collegeInfoList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From CollegeInfo collegeInfo where 1=1";
        Query q = s.createQuery(hql);
        List collegeInfoList = q.list();
        recordNumber = collegeInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public CollegeInfo GetCollegeInfoByCollegeNumber(String collegeNumber) {
        Session s = factory.getCurrentSession();
        CollegeInfo collegeInfo = (CollegeInfo)s.get(CollegeInfo.class, collegeNumber);
        return collegeInfo;
    }

    /*����CollegeInfo��Ϣ*/
    public void UpdateCollegeInfo(CollegeInfo collegeInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(collegeInfo);
    }

    /*ɾ��CollegeInfo��Ϣ*/
    public void DeleteCollegeInfo (String collegeNumber) throws Exception {
        Session s = factory.getCurrentSession();
        Object collegeInfo = s.load(CollegeInfo.class, collegeNumber);
        s.delete(collegeInfo);
    }

}
