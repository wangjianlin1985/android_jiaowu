package com.chengxusheji.domain;

import java.sql.Timestamp;
public class ClassInfo {
    /*�༶���*/
    private String classNumber;
    public String getClassNumber() {
        return classNumber;
    }
    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    /*�༶����*/
    private String className;
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    /*����רҵ*/
    private SpecialFieldInfo classSpecialFieldNumber;
    public SpecialFieldInfo getClassSpecialFieldNumber() {
        return classSpecialFieldNumber;
    }
    public void setClassSpecialFieldNumber(SpecialFieldInfo classSpecialFieldNumber) {
        this.classSpecialFieldNumber = classSpecialFieldNumber;
    }

    /*��������*/
    private Timestamp classBirthDate;
    public Timestamp getClassBirthDate() {
        return classBirthDate;
    }
    public void setClassBirthDate(Timestamp classBirthDate) {
        this.classBirthDate = classBirthDate;
    }

    /*������*/
    private String classTeacherCharge;
    public String getClassTeacherCharge() {
        return classTeacherCharge;
    }
    public void setClassTeacherCharge(String classTeacherCharge) {
        this.classTeacherCharge = classTeacherCharge;
    }

    /*��ϵ�绰*/
    private String classTelephone;
    public String getClassTelephone() {
        return classTelephone;
    }
    public void setClassTelephone(String classTelephone) {
        this.classTelephone = classTelephone;
    }

    /*������Ϣ*/
    private String classMemo;
    public String getClassMemo() {
        return classMemo;
    }
    public void setClassMemo(String classMemo) {
        this.classMemo = classMemo;
    }

}