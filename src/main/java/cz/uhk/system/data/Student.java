package cz.uhk.system.data;

import java.util.ArrayList;
import java.util.List;

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
    public Student(List<String> data) {
        if (data.get(0).isEmpty() || data.get(0).trim().isEmpty()) {
            String msg = "The first record in a table row (here: " + data.get(0) +
                    ") should be the name of a student, not an empty string.";
            throw new IllegalArgumentException(msg);
        }
        int inEnglishInd = data.size() - 1;
        int gradesCount = inEnglishInd - 1;

        ArrayList<Integer> gradesList = new ArrayList<>();
        boolean inEnglish;

        for (int i = 1; i < gradesCount + 1; i++) {
            try {
                int grade = Integer.parseInt(data.get(i));
                gradesList.add(grade);
            } catch (NumberFormatException e) {
                String msg = "Error when transforming file content to float number at column " + i;
                throw new RuntimeException(msg, e);
            }
        }

        String contractValue = data.get(inEnglishInd);

        if (contractValue.equalsIgnoreCase("FALSE")) {
            inEnglish = false;
        } else if (contractValue.equalsIgnoreCase("TRUE")) {
            inEnglish = true;
        } else {
            throw new IllegalArgumentException("The last value in a file row should be either TRUE or FALSE");
        }

        this.name = data.get(0);
        this.grades = gradesList;
        this.inEnglish = inEnglish;
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
