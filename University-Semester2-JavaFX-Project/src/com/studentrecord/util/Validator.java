package com.studentrecord.util;

public class Validator
{
    public static boolean isValidID(String input)
    {
        if (input == null || input.trim().isEmpty())
        {
            return false;
        }

        return input.trim().matches("\\d+");
    }


    public static boolean isValidAge(String input)
    {
        if (input == null || input.trim().isEmpty())
        {
            return false;
        }

        try
        {
            int age = Integer.parseInt(input.trim());
            return age > 0 && age < 120;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }


    public static boolean isValidEmail(String input)
    {
        if (input == null || input.trim().isEmpty())
        {
            return false;
        }

        return input.trim().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }


    public static boolean isValidPhone(String input)
    {
        if (input == null || input.trim().isEmpty())
        {
            return false;
        }

        return input.trim().matches("[\\d\\-\\+\\s()]{7,15}");
    }


    public static boolean isNotEmpty(String input)
    {
        return input != null && !input.trim().isEmpty();
    }


    public static String validateStudentForm(String idText, String name, String ageText,
                                             String email, String phone, String grade)
    {
        if (!isValidID(idText))
        {
            return "Student ID must be a numeric value.";
        }

        if (!isNotEmpty(name))
        {
            return "Name cannot be left blank.";
        }

        if (!isValidAge(ageText))
        {
            return "Please enter a valid age between 1 and 119.";
        }

        if (!isValidEmail(email))
        {
            return "Email format is invalid. Example: name@domain.com";
        }

        if (!isValidPhone(phone))
        {
            return "Phone number must be 7 to 15 digits and may include +, -, or spaces.";
        }

        if (!isNotEmpty(grade))
        {
            return "Grade cannot be left blank.";
        }

        return null;
    }
}