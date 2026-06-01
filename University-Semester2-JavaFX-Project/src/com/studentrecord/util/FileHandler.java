package com.studentrecord.util;

import com.studentrecord.model.Student;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler
{
    private static final String FILE_PATH = "data/students.txt";


    public List<Student> loadAllStudents()
    {
        List<Student> students = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists())
        {
            return students;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line;

            while ((line = reader.readLine()) != null)
            {
                line = line.trim();

                if (line.isEmpty())
                {
                    continue;
                }

                Student student = parseCSVLine(line);

                if (student != null)
                {
                    students.add(student);
                }
            }
        }
        catch (IOException e)
        {
            System.err.println("Could not read student records: " + e.getMessage());
        }

        return students;
    }


    public void saveAllStudents(List<Student> students)
    {
        File directory = new File("data");

        if (!directory.exists())
        {
            directory.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false)))
        {
            for (Student student : students)
            {
                writer.write(student.toCSV());
                writer.newLine();
            }
        }
        catch (IOException e)
        {
            System.err.println("Could not save student records: " + e.getMessage());
        }
    }


    private Student parseCSVLine(String line)
    {
        String[] parts = line.split(",");

        if (parts.length != 6)
        {
            return null;
        }

        try
        {
            int studentID = Integer.parseInt(parts[0].trim());
            String name = parts[1].trim();
            int age = Integer.parseInt(parts[2].trim());
            String email = parts[3].trim();
            String phone = parts[4].trim();
            String grade = parts[5].trim();

            return new Student(studentID, name, age, email, phone, grade);
        }
        catch (NumberFormatException e)
        {
            System.err.println("Skipping malformed record: " + line);
            return null;
        }
    }
}