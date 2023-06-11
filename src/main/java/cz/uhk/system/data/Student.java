package cz.uhk.system.data;

import java.util.ArrayList;

public class Student {
    private String name;
    private ArrayList<Integer> grades;
    private boolean inEnglish;
    private float avarage;
    static private int studentCount;

    public Student(){
        studentCount+=1;
    }
    public Student(String name, ArrayList<Integer> grades, boolean inEnglish) {
        this.name = name;
        this.grades = grades;
        this.inEnglish = inEnglish;
        studentCount+=1;
    }
    public Student(Student another) {
        this.name = another.name;
        this.grades = another.grades;
        this.inEnglish = another.inEnglish;
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

    public boolean isInEnglish() {
        return inEnglish;
    }

    public void setInEnglish(boolean inEnglish) {
        this.inEnglish = inEnglish;
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
}
