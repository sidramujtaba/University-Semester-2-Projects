package com.studentrecord.model;

// Parent class for 'Student'

public class Person
{
    private String name;
    private int    age;
    private String email;
    private String phone;


    public Person(String name, int age, String email, String phone)
    {
        this.name  = name;
        this.age   = age;
        this.email = email;
        this.phone = phone;
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }


    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }


    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }


    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }


    @Override
    public String toString()
    {
        return name + " | Age: " + age + " | " + email + " | " + phone;
    }

    // toString() (POLYMORPHISM)
    // Overrides Java's default Object.toString() and returns a readable one-line summary of the person's information.
    // This is polymorphism — the method behaves differently here than it does in the default Object class.
    // The Student class further overrides this method to include student-specific fields.
}