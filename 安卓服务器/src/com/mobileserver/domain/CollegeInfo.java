package com.mobileserver.domain;

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
    private java.sql.Timestamp collegeBirthDate;
    public java.sql.Timestamp getCollegeBirthDate() {
        return collegeBirthDate;
    }
    public void setCollegeBirthDate(java.sql.Timestamp collegeBirthDate) {
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