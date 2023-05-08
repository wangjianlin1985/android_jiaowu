package com.mobileclient.domain;

import java.io.Serializable;

public class ClassInfo implements Serializable {
    /*班级编号*/
    private String classNumber;
    public String getClassNumber() {
        return classNumber;
    }
    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    /*班级名称*/
    private String className;
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    /*所属专业*/
    private String classSpecialFieldNumber;
    public String getClassSpecialFieldNumber() {
        return classSpecialFieldNumber;
    }
    public void setClassSpecialFieldNumber(String classSpecialFieldNumber) {
        this.classSpecialFieldNumber = classSpecialFieldNumber;
    }

    /*成立日期*/
    private java.sql.Timestamp classBirthDate;
    public java.sql.Timestamp getClassBirthDate() {
        return classBirthDate;
    }
    public void setClassBirthDate(java.sql.Timestamp classBirthDate) {
        this.classBirthDate = classBirthDate;
    }

    /*班主任*/
    private String classTeacherCharge;
    public String getClassTeacherCharge() {
        return classTeacherCharge;
    }
    public void setClassTeacherCharge(String classTeacherCharge) {
        this.classTeacherCharge = classTeacherCharge;
    }

    /*联系电话*/
    private String classTelephone;
    public String getClassTelephone() {
        return classTelephone;
    }
    public void setClassTelephone(String classTelephone) {
        this.classTelephone = classTelephone;
    }

    /*附加信息*/
    private String classMemo;
    public String getClassMemo() {
        return classMemo;
    }
    public void setClassMemo(String classMemo) {
        this.classMemo = classMemo;
    }

}