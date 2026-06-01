package com.studentrecord.model;

// This class is the central data object of the entire system.

public class Student extends Person
{
    private int studentID;
    private String grade;


    public Student(int studentID, String name, int age, String email, String phone, String grade)
    {
        super(name, age, email, phone);
        this.studentID = studentID;
        this.grade     = grade;
    }


    public int getStudentID()
    {
        return studentID;
    }

    public void setStudentID(int studentID)
    {
        this.studentID = studentID;
    }


    public String getGrade()
    {
        return grade;
    }

    public void setGrade(String grade)
    {
        this.grade = grade;
    }


    @Override
    public String toString()
    {
        return studentID + " | " + getName() + " | Age: " + getAge()
                + " | " + getEmail() + " | " + getPhone() + " | Grade: " + grade;
    }


    public String toCSV()
    {
        return studentID + "," + getName() + "," + getAge()
                + "," + getEmail() + "," + getPhone() + "," + grade;
    }
}