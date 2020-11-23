package com.sarker.ereg;

public class StudentInfo {

    String studentName,studentClass,studentKey;

    public StudentInfo (){

    }

    public StudentInfo(String studentName, String studentClass, String studentKey) {
        this.studentName = studentName;
        this.studentClass = studentClass;
        this.studentKey = studentKey;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getStudentKey() {
        return studentKey;
    }

    public void setStudentKey(String studentKey) {
        this.studentKey = studentKey;
    }
}
