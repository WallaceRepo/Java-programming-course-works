package model;
import util.QueryItem;

import java.util.Random;

public class Student implements QueryItem, Comparable <Student> {
    private String name;
    private static int LAST_ID = 10_000;
    private int studentId;
    private String course;
    private int yearStarted;
    protected static Random random = new Random();
    // I've made them static, because I don't want each instance to have this data,
    //it can be stored with the class instance instead.
    private static String[] firstName = { "Ann", "Bill", "Cathy", "John", "Sophia"};
    private static String [] courses = { "Java", "Python", "C++"};


    public Student() {
        int lastNameIndex = random.nextInt(65, 91);
        name = firstName[random.nextInt(5)] + " " + (char) lastNameIndex;
        course = courses[random.nextInt(3)];
        yearStarted = random.nextInt(2018, 2023);
        studentId = LAST_ID++;

    }

    @Override
    public String toString() {
        return "%d %-15s %-15s %d".formatted(studentId, name, course, yearStarted);
    }

    public int getYearStarted() {
        return yearStarted;
    }

    @Override
    public boolean matchFieldValue(String fieldName, String value) {
        String fName = fieldName.toUpperCase();
        return switch(fName) {
            case "NAME" -> name.equalsIgnoreCase(value); // it means match is found if name and value is equal
            case "COURSE" -> course.equalsIgnoreCase(value);
            case "YEARSTARTED" -> yearStarted == (Integer.parseInt(value));
            // if the course field's value is equal to what the user is trying to match on, it will return true.
            default -> false;
        };
    }

    @Override
    public int compareTo(Student o) {
        return Integer.valueOf(studentId).compareTo(o.studentId);
    }
}
