package com.mobileclient.domain;

import java.io.Serializable;

public class CollegeInfo implements Serializable {
    /*学院编号*/
    private String collegeNumber;
    public String getCollegeNumber() {
        return collegeNumber;
    }
    public void setCollegeNumber(String collegeNumber) {
        this.collegeNumber = collegeNumber;
    }

    /*学院名称*/
    private String collegeName;
    public String getCollegeName() {
        return collegeName;
    }
    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    /*成立日期*/
    private java.sql.Timestamp collegeBirthDate;
    public java.sql.Timestamp getCollegeBirthDate() {
        return collegeBirthDate;
    }
    public void setCollegeBirthDate(java.sql.Timestamp collegeBirthDate) {
        this.collegeBirthDate = collegeBirthDate;
    }

    /*院长姓名*/
    private String collegeMan;
    public String getCollegeMan() {
        return collegeMan;
    }
    public void setCollegeMan(String collegeMan) {
        this.collegeMan = collegeMan;
    }

    /*联系电话*/
    private String collegeTelephone;
    public String getCollegeTelephone() {
        return collegeTelephone;
    }
    public void setCollegeTelephone(String collegeTelephone) {
        this.collegeTelephone = collegeTelephone;
    }

    /*附加信息*/
    private String collegeMemo;
    public String getCollegeMemo() {
        return collegeMemo;
    }
    public void setCollegeMemo(String collegeMemo) {
        this.collegeMemo = collegeMemo;
    }

}