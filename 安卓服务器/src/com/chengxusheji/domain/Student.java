package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Student {
    /*ѧ��*/
    private String studentNumber;
    public String getStudentNumber() {
        return studentNumber;
    }
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    /*����*/
    private String studentName;
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /*��¼����*/
    private String studentPassword;
    public String getStudentPassword() {
        return studentPassword;
    }
    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    /*�Ա�*/
    private String studentSex;
    public String getStudentSex() {
        return studentSex;
    }
    public void setStudentSex(String studentSex) {
        this.studentSex = studentSex;
    }

    /*���ڰ༶*/
    private ClassInfo studentClassNumber;
    public ClassInfo getStudentClassNumber() {
        return studentClassNumber;
    }
    public void setStudentClassNumber(ClassInfo studentClassNumber) {
        this.studentClassNumber = studentClassNumber;
    }

    /*��������*/
    private Timestamp studentBirthday;
    public Timestamp getStudentBirthday() {
        return studentBirthday;
    }
    public void setStudentBirthday(Timestamp studentBirthday) {
        this.studentBirthday = studentBirthday;
    }

    /*������ò*/
    private String studentState;
    public String getStudentState() {
        return studentState;
    }
    public void setStudentState(String studentState) {
        this.studentState = studentState;
    }

    /*ѧ����Ƭ*/
    private String studentPhoto;
    public String getStudentPhoto() {
        return studentPhoto;
    }
    public void setStudentPhoto(String studentPhoto) {
        this.studentPhoto = studentPhoto;
    }

    /*��ϵ�绰*/
    private String studentTelephone;
    public String getStudentTelephone() {
        return studentTelephone;
    }
    public void setStudentTelephone(String studentTelephone) {
        this.studentTelephone = studentTelephone;
    }

    /*ѧ������*/
    private String studentEmail;
    public String getStudentEmail() {
        return studentEmail;
    }
    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    /*��ϵqq*/
    private String studentQQ;
    public String getStudentQQ() {
        return studentQQ;
    }
    public void setStudentQQ(String studentQQ) {
        this.studentQQ = studentQQ;
    }

    /*��ͥ��ַ*/
    private String studentAddress;
    public String getStudentAddress() {
        return studentAddress;
    }
    public void setStudentAddress(String studentAddress) {
        this.studentAddress = studentAddress;
    }

    /*������Ϣ*/
    private String studentMemo;
    public String getStudentMemo() {
        return studentMemo;
    }
    public void setStudentMemo(String studentMemo) {
        this.studentMemo = studentMemo;
    }

}