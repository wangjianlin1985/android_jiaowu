package com.mobileclient.domain;

import java.io.Serializable;

public class SpecialFieldInfo implements Serializable {
    /*专业编号*/
    private String specialFieldNumber;
    public String getSpecialFieldNumber() {
        return specialFieldNumber;
    }
    public void setSpecialFieldNumber(String specialFieldNumber) {
        this.specialFieldNumber = specialFieldNumber;
    }

    /*专业名称*/
    private String specialFieldName;
    public String getSpecialFieldName() {
        return specialFieldName;
    }
    public void setSpecialFieldName(String specialFieldName) {
        this.specialFieldName = specialFieldName;
    }

    /*所在学院*/
    private String specialCollegeNumber;
    public String getSpecialCollegeNumber() {
        return specialCollegeNumber;
    }
    public void setSpecialCollegeNumber(String specialCollegeNumber) {
        this.specialCollegeNumber = specialCollegeNumber;
    }

    /*成立日期*/
    private java.sql.Timestamp specialBirthDate;
    public java.sql.Timestamp getSpecialBirthDate() {
        return specialBirthDate;
    }
    public void setSpecialBirthDate(java.sql.Timestamp specialBirthDate) {
        this.specialBirthDate = specialBirthDate;
    }

    /*联系人*/
    private String specialMan;
    public String getSpecialMan() {
        return specialMan;
    }
    public void setSpecialMan(String specialMan) {
        this.specialMan = specialMan;
    }

    /*联系电话*/
    private String specialTelephone;
    public String getSpecialTelephone() {
        return specialTelephone;
    }
    public void setSpecialTelephone(String specialTelephone) {
        this.specialTelephone = specialTelephone;
    }

    /*附加信息*/
    private String specialMemo;
    public String getSpecialMemo() {
        return specialMemo;
    }
    public void setSpecialMemo(String specialMemo) {
        this.specialMemo = specialMemo;
    }

}