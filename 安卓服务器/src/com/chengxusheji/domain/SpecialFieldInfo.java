package com.chengxusheji.domain;

import java.sql.Timestamp;
public class SpecialFieldInfo {
    /*רҵ���*/
    private String specialFieldNumber;
    public String getSpecialFieldNumber() {
        return specialFieldNumber;
    }
    public void setSpecialFieldNumber(String specialFieldNumber) {
        this.specialFieldNumber = specialFieldNumber;
    }

    /*רҵ����*/
    private String specialFieldName;
    public String getSpecialFieldName() {
        return specialFieldName;
    }
    public void setSpecialFieldName(String specialFieldName) {
        this.specialFieldName = specialFieldName;
    }

    /*����ѧԺ*/
    private CollegeInfo specialCollegeNumber;
    public CollegeInfo getSpecialCollegeNumber() {
        return specialCollegeNumber;
    }
    public void setSpecialCollegeNumber(CollegeInfo specialCollegeNumber) {
        this.specialCollegeNumber = specialCollegeNumber;
    }

    /*��������*/
    private Timestamp specialBirthDate;
    public Timestamp getSpecialBirthDate() {
        return specialBirthDate;
    }
    public void setSpecialBirthDate(Timestamp specialBirthDate) {
        this.specialBirthDate = specialBirthDate;
    }

    /*��ϵ��*/
    private String specialMan;
    public String getSpecialMan() {
        return specialMan;
    }
    public void setSpecialMan(String specialMan) {
        this.specialMan = specialMan;
    }

    /*��ϵ�绰*/
    private String specialTelephone;
    public String getSpecialTelephone() {
        return specialTelephone;
    }
    public void setSpecialTelephone(String specialTelephone) {
        this.specialTelephone = specialTelephone;
    }

    /*������Ϣ*/
    private String specialMemo;
    public String getSpecialMemo() {
        return specialMemo;
    }
    public void setSpecialMemo(String specialMemo) {
        this.specialMemo = specialMemo;
    }

}