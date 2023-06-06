package cz.uhk.system;
import java.util.List;
public class StudentList {
    private List<Student> studentList;
    private int size;
    private int capacity;
    private boolean isSorted;
    private int budgetStCount;
    private int scholarshipPercent;

    public StudentList(){}

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public int getBudgetStCount() {
        return budgetStCount;
    }

    public void setBudgetStCount(int budgetStCount) {
        this.budgetStCount = budgetStCount;
    }

    public int getScholarshipPercent() {
        return scholarshipPercent;
    }

    public void setScholarshipPercent(int scholarshipPercent) {
        this.scholarshipPercent = scholarshipPercent;
    }
    public int getScholarshipStCount(){
        return this.budgetStCount * this.scholarshipPercent;
    }
}
