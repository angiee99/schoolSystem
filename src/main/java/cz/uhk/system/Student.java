package cz.uhk.system;

import java.util.ArrayList;

public class Student {
    private String name;
    private ArrayList<Integer> grades;
    private boolean onContract;
    private float avarage;
    static private int studentCount;

    public Student(){
        studentCount+=1;
    }
    public Student(String name, ArrayList<Integer> grades, boolean onContract) {
        this.name = name;
        this.grades = grades;
        this.onContract = onContract;
        studentCount+=1;
    }
    public Student(Student another) {
        this.name = another.name;
        this.grades = another.grades;
        this.onContract = another.onContract;
        this.avarage = another.avarage;
        studentCount += 1;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getGrades() {
        return grades;
    }

    public void setGrades(ArrayList<Integer> grades) {
        this.grades = grades;
    }

    public boolean isOnContract() {
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
    protected void countAvarage() {
        float sum = 0;
        for ( int grade: this.grades
        ) {
            sum += grade;
        }
        this.avarage = sum / this.grades.size();
    }

    public static int getStudentCount() {
        return studentCount;
    }


//    public static void main(String[] args) {
//        System.out.println("hey");
//        ArrayList<Integer> grades = new ArrayList<Integer>();
//        grades.add(1); grades.add(2); grades.add(3);
//        Student st1 = new Student("angie", grades, false);
//        Student st2 = new Student(st1);
//        System.out.println(st2.getName());
//        System.out.println(st1.getAvarage());
//        System.out.println(Student.getStudentCount());
//    }
}
