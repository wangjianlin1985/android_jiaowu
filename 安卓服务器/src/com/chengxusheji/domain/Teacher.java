package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Teacher {
    /*教师编号*/
    private String teacherNumber;
    public String getTeacherNumber() {
        return teacherNumber;
    }
    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    /*教师姓名*/
    private String teacherName;
    public String getTeacherName() {
        return teacherName;
    }
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    /*登录密码*/
    private String teacherPassword;
    public String getTeacherPassword() {
        return teacherPassword;
    }
    public void setTeacherPassword(String teacherPassword) {
        this.teacherPassword = teacherPassword;
    }

    /*性别*/
    private String teacherSex;
    public String getTeacherSex() {
        return teacherSex;
    }
    public void setTeacherSex(String teacherSex) {
        this.teacherSex = teacherSex;
    }

    /*出生日期*/
    private Timestamp teacherBirthday;
    public Timestamp getTeacherBirthday() {
        return teacherBirthday;
    }
    public void setTeacherBirthday(Timestamp teacherBirthday) {
        this.teacherBirthday = teacherBirthday;
    }

    /*入职日期*/
    private Timestamp teacherArriveDate;
    public Timestamp getTeacherArriveDate() {
        return teacherArriveDate;
    }
    public void setTeacherArriveDate(Timestamp teacherArriveDate) {
        this.teacherArriveDate = teacherArriveDate;
    }

    /*身份证号*/
    private String teacherCardNumber;
    public String getTeacherCardNumber() {
        return teacherCardNumber;
    }
    public void setTeacherCardNumber(String teacherCardNumber) {
        this.teacherCardNumber = teacherCardNumber;
    }

    /*联系电话*/
    private String teacherPhone;
    public String getTeacherPhone() {
        return teacherPhone;
    }
    public void setTeacherPhone(String teacherPhone) {
        this.teacherPhone = teacherPhone;
    }

    /*教师照片*/
    private String teacherPhoto;
    public String getTeacherPhoto() {
        return teacherPhoto;
    }
    public void setTeacherPhoto(String teacherPhoto) {
        this.teacherPhoto = teacherPhoto;
    }

    /*家庭地址*/
    private String teacherAddress;
    public String getTeacherAddress() {
        return teacherAddress;
    }
    public void setTeacherAddress(String teacherAddress) {
        this.teacherAddress = teacherAddress;
    }

    /*附加信息*/
    private String teacherMemo;
    public String getTeacherMemo() {
        return teacherMemo;
    }
    public void setTeacherMemo(String teacherMemo) {
        this.teacherMemo = teacherMemo;
    }

}