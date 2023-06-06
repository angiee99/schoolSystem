package cz.uhk.system;

import java.util.ArrayList;

public class Student {
    private String name;
    private int[] grades;
    private boolean onContract;
    private float avarage;
    static private int studentCount;

    public Student(){
        studentCount+=1;
    }
    public Student(String name, int[] grades, boolean onContract) {
        this.name = name;
        this.grades = grades;
        this.onContract = onContract;
        studentCount+=1;
    }
    public Student(Student another){
        this.name = another.name;
        this.grades = another.grades;
        this.onContract = another.onContract;
        this.avarage = another.avarage;
        studentCount+=1;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getGrades() {
        return grades;
    }

    public void setGrades(int[] grades) {
        this.grades = grades;
    }

    public boolean getOnContract() {
        return onContract;
    }

    public void setOnContract(boolean onContract) {
        this.onContract = onContract;
    }

    public float getAvarage() {
        if (this.avarage == 0){
            this.countAvarage();
        }
        return avarage;
    }

    public void setAvarage(float avarage) {
        this.avarage = avarage;
    }

    public static int getStudentCount() {
        return studentCount;
    }

    protected void countAvarage() {
        float sum = 0;
        for ( int grade: this.grades
             ) {
                sum += grade;
        }
        this.avarage = sum / this.grades.length;
    }
    public static void main(String[] args) {
        System.out.println("hey");
        Student st1 = new Student("angie", new int[]{1, 2, 4}, false);
        Student st2 = new Student(st1);
        System.out.println(st2.getName());
        System.out.println(st1.getAvarage());
        System.out.println(Student.getStudentCount());
    }
}
