package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Teacher {
    /*��ʦ���*/
    private String teacherNumber;
    public String getTeacherNumber() {
        return teacherNumber;
    }
    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    /*��ʦ����*/
    private String teacherName;
    public String getTeacherName() {
        return teacherName;
    }
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    /*��¼����*/
    private String teacherPassword;
    public String getTeacherPassword() {
        return teacherPassword;
    }
    public void setTeacherPassword(String teacherPassword) {
        this.teacherPassword = teacherPassword;
    }

    /*�Ա�*/
    private String teacherSex;
    public String getTeacherSex() {
        return teacherSex;
    }
    public void setTeacherSex(String teacherSex) {
        this.teacherSex = teacherSex;
    }

    /*��������*/
    private Timestamp teacherBirthday;
    public Timestamp getTeacherBirthday() {
        return teacherBirthday;
    }
    public void setTeacherBirthday(Timestamp teacherBirthday) {
        this.teacherBirthday = teacherBirthday;
    }

    /*��ְ����*/
    private Timestamp teacherArriveDate;
    public Timestamp getTeacherArriveDate() {
        return teacherArriveDate;
    }
    public void setTeacherArriveDate(Timestamp teacherArriveDate) {
        this.teacherArriveDate = teacherArriveDate;
    }

    /*���֤��*/
    private String teacherCardNumber;
    public String getTeacherCardNumber() {
        return teacherCardNumber;
    }
    public void setTeacherCardNumber(String teacherCardNumber) {
        this.teacherCardNumber = teacherCardNumber;
    }

    /*��ϵ�绰*/
    private String teacherPhone;
    public String getTeacherPhone() {
        return teacherPhone;
    }
    public void setTeacherPhone(String teacherPhone) {
        this.teacherPhone = teacherPhone;
    }

    /*��ʦ��Ƭ*/
    private String teacherPhoto;
    public String getTeacherPhoto() {
        return teacherPhoto;
    }
    public void setTeacherPhoto(String teacherPhoto) {
        this.teacherPhoto = teacherPhoto;
    }

    /*��ͥ��ַ*/
    private String teacherAddress;
    public String getTeacherAddress() {
        return teacherAddress;
    }
    public void setTeacherAddress(String teacherAddress) {
        this.teacherAddress = teacherAddress;
    }

    /*������Ϣ*/
    private String teacherMemo;
    public String getTeacherMemo() {
        return teacherMemo;
    }
    public void setTeacherMemo(String teacherMemo) {
        this.teacherMemo = teacherMemo;
    }

}