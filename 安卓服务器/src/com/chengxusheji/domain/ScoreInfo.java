package com.chengxusheji.domain;

import java.sql.Timestamp;
public class ScoreInfo {
    /*��¼���*/
    private int scoreId;
    public int getScoreId() {
        return scoreId;
    }
    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }

    /*ѧ������*/
    private Student studentNumber;
    public Student getStudentNumber() {
        return studentNumber;
    }
    public void setStudentNumber(Student studentNumber) {
        this.studentNumber = studentNumber;
    }

    /*�γ̶���*/
    private CourseInfo courseNumber;
    public CourseInfo getCourseNumber() {
        return courseNumber;
    }
    public void setCourseNumber(CourseInfo courseNumber) {
        this.courseNumber = courseNumber;
    }

    /*�ɼ��÷�*/
    private float scoreValue;
    public float getScoreValue() {
        return scoreValue;
    }
    public void setScoreValue(float scoreValue) {
        this.scoreValue = scoreValue;
    }

    /*ѧ������*/
    private String studentEvaluate;
    public String getStudentEvaluate() {
        return studentEvaluate;
    }
    public void setStudentEvaluate(String studentEvaluate) {
        this.studentEvaluate = studentEvaluate;
    }

}