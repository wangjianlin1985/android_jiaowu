package com.chengxusheji.domain;

import java.sql.Timestamp;
public class CourseInfo {
    /*�γ̱��*/
    private String courseNumber;
    public String getCourseNumber() {
        return courseNumber;
    }
    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    /*�γ�����*/
    private String courseName;
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /*�Ͽ���ʦ*/
    private Teacher courseTeacher;
    public Teacher getCourseTeacher() {
        return courseTeacher;
    }
    public void setCourseTeacher(Teacher courseTeacher) {
        this.courseTeacher = courseTeacher;
    }

    /*�Ͽ�ʱ��*/
    private String courseTime;
    public String getCourseTime() {
        return courseTime;
    }
    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    /*�Ͽεص�*/
    private String coursePlace;
    public String getCoursePlace() {
        return coursePlace;
    }
    public void setCoursePlace(String coursePlace) {
        this.coursePlace = coursePlace;
    }

    /*�γ�ѧ��*/
    private float courseScore;
    public float getCourseScore() {
        return courseScore;
    }
    public void setCourseScore(float courseScore) {
        this.courseScore = courseScore;
    }

    /*������Ϣ*/
    private String courseMemo;
    public String getCourseMemo() {
        return courseMemo;
    }
    public void setCourseMemo(String courseMemo) {
        this.courseMemo = courseMemo;
    }

}