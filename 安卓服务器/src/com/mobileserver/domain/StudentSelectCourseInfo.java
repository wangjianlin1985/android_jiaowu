package com.mobileserver.domain;

public class StudentSelectCourseInfo {
    /*记录编号*/
    private int selectId;
    public int getSelectId() {
        return selectId;
    }
    public void setSelectId(int selectId) {
        this.selectId = selectId;
    }

    /*学生对象*/
    private String studentNumber;
    public String getStudentNumber() {
        return studentNumber;
    }
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    /*课程对象*/
    private String courseNumber;
    public String getCourseNumber() {
        return courseNumber;
    }
    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

}