package com.chengxusheji.domain;

import java.sql.Timestamp;
public class CollegeInfo {
    /*ѧԺ���*/
    private String collegeNumber;
    public String getCollegeNumber() {
        return collegeNumber;
    }
    public void setCollegeNumber(String collegeNumber) {
        this.collegeNumber = collegeNumber;
    }

    /*ѧԺ����*/
    private String collegeName;
    public String getCollegeName() {
        return collegeName;
    }
    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    /*��������*/
    private Timestamp collegeBirthDate;
    public Timestamp getCollegeBirthDate() {
        return collegeBirthDate;
    }
    public void setCollegeBirthDate(Timestamp collegeBirthDate) {
        this.collegeBirthDate = collegeBirthDate;
    }

    /*Ժ������*/
    private String collegeMan;
    public String getCollegeMan() {
        return collegeMan;
    }
    public void setCollegeMan(String collegeMan) {
        this.collegeMan = collegeMan;
    }

    /*��ϵ�绰*/
    private String collegeTelephone;
    public String getCollegeTelephone() {
        return collegeTelephone;
    }
    public void setCollegeTelephone(String collegeTelephone) {
        this.collegeTelephone = collegeTelephone;
    }

    /*������Ϣ*/
    private String collegeMemo;
    public String getCollegeMemo() {
        return collegeMemo;
    }
    public void setCollegeMemo(String collegeMemo) {
        this.collegeMemo = collegeMemo;
    }

}