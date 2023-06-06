package cz.uhk.system;
import java.util.ArrayList;
import java.util.List;
public class StudentList {
    private List<Student> studentList  = new ArrayList<>();
    private int size;
    private boolean isSorted;
    private int budgetStCount;
    private int scholarshipPercent;

    public StudentList(){
        this.size = 0;
        this.isSorted = false;
        this.budgetStCount = 0;
        this.scholarshipPercent = 0;
    }
    public StudentList(List<Student> students){
        this.studentList = students;
        this.size = students.size();
        this.isSorted = false;
        this.budgetStCount = 0;
        this.scholarshipPercent = 0;
    }
    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public void add(Student student){
        this.studentList.add(student);
        this.size++;
        this.isSorted = false;
        if(!student.isOnContract()){
            this.budgetStCount++;
        }
    }
    public void print(){
        for (int i = 0; i < this.size; i++) {
            System.out.println(this.studentList.get(i).getName());
        }
    }
    public int getScholarshipStCount(){
        return this.budgetStCount * this.scholarshipPercent;
    }

    // getters and setters
    public int getSize() {
        return size;
    }

    public int getBudgetStCount() {
        return budgetStCount;
    }

    public int getScholarshipPercent() {
        return scholarshipPercent;
    }

    public void setScholarshipPercent(int scholarshipPercent) {
        this.scholarshipPercent = scholarshipPercent;
    }

    public boolean isSorted() {
        return isSorted;
    }


    public static void main(String[] args) {
        ArrayList<Integer> grades = new ArrayList<Integer>();
        grades.add(1); grades.add(2); grades.add(3);
        Student st1 = new Student("angie", grades, false);
        Student st2 = new Student(st1);
        ArrayList<Integer> grades1 = new ArrayList<Integer>();
        grades1.add(4); grades1.add(5); grades.add(6);
        Student st3 = new Student("dani", grades1, false);
        Student st4 = new Student("lee", grades1, true);

        StudentList list = new StudentList();
        list.add(st1); list.add(st2);  list.add(st3);  list.add(st4);

        System.out.println(list.getSize());
        System.out.println(list.getStudentList().get(2).getName());
    }
}
