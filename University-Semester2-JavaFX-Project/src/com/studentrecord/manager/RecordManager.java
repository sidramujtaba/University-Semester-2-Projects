package com.studentrecord.manager;

// This is the abstraction pillar of OOP in action. The interface separates
// what the system can do from how it actually does it.

import com.studentrecord.model.Student;
import java.util.List;

public interface RecordManager
{
    void addStudent(Student student);

    void updateStudent(Student updatedStudent);

    void deleteStudent(int studentID);

    Student searchByID(int studentID);

    List<Student> searchByName(String name);

    List<Student> getAllStudents();
}