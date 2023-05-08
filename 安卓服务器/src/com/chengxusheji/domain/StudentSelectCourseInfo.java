package com.chengxusheji.domain;

import java.sql.Timestamp;
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
    private Student studentNumber;
    public Student getStudentNumber() {
        return studentNumber;
    }
    public void setStudentNumber(Student studentNumber) {
        this.studentNumber = studentNumber;
    }

    /*课程对象*/
    private CourseInfo courseNumber;
    public CourseInfo getCourseNumber() {
        return courseNumber;
    }
    public void setCourseNumber(CourseInfo courseNumber) {
        this.courseNumber = courseNumber;
    }

}