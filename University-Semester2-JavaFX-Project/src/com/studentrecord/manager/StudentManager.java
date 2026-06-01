package com.studentrecord.manager;

// StudentManager is the only class that implements this interface.

import com.studentrecord.model.Student;
import com.studentrecord.util.FileHandler;

import java.util.ArrayList;
import java.util.List;

public class StudentManager implements RecordManager
{
    private List<Student> studentList;
    private FileHandler   fileHandler;


    public StudentManager()
    {
        fileHandler = new FileHandler();
        studentList = fileHandler.loadAllStudents();
    }


    @Override
    public void addStudent(Student student)
    {
        studentList.add(student);
        fileHandler.saveAllStudents(studentList);
    }

    // addStudent()
    // Adds the new Student object to studentList and immediately calls fileHandler.saveAllStudents()
    // to write the updated list to the file.


    @Override
    public void updateStudent(Student updatedStudent)
    {
        for (int i = 0; i < studentList.size(); i++)
        {
            if (studentList.get(i).getStudentID() == updatedStudent.getStudentID())
            {
                studentList.set(i, updatedStudent);
                break;
            }
        }

        fileHandler.saveAllStudents(studentList);
    }

    // updateStudent()
    // Loops through studentList looking for a matching student ID. When found, it replaces that entry with the updated
    // object and saves the file. The loop breaks immediately after the replacement to avoid unnecessary iterations.


    @Override
    public void deleteStudent(int studentID)
    {
        studentList.removeIf(s -> s.getStudentID() == studentID);
        fileHandler.saveAllStudents(studentList);
    }

    // deleteStudent()
    // Uses removeIf() with a lambda expression to remove any student whose ID matches the given value.
    // The file is saved immediately after.


    @Override
    public Student searchByID(int studentID)
    {
        for (Student student : studentList)
        {
            if (student.getStudentID() == studentID)
            {
                return student;
            }
        }

        return null;
    }

    // searchByID()
    // Loops through studentList and returns the first Student whose ID matches. Returns null if no match is found.


    @Override
    public List<Student> searchByName(String name)
    {
        List<Student> results = new ArrayList<>();
        String        keyword = name.toLowerCase().trim();

        for (Student student : studentList)
        {
            if (student.getName().toLowerCase().contains(keyword))
            {
                results.add(student);
            }
        }

        return results;
    }

    // searchByName()
    // Builds a new list of all students whose names contain the search keyword, using case-insensitive matching via
    // toLowerCase() and contains(). Returns all matches, not just the first one.


    @Override
    public List<Student> getAllStudents()
    {
        return new ArrayList<>(studentList);
    }

    // getAllStudents()
    // Returns a copy of studentList using new ArrayList<>(studentList) rather than the list itself. This protects the
    // internal data from being accidentally modified by the UI.


    public boolean idAlreadyExists(int studentID)
    {
        return searchByID(studentID) != null;
    }

    // idAlreadyExists()
    // A convenience method used by AddStudentView before saving a new record. It calls searchByID() and returns true if
    // the result is not null.
}