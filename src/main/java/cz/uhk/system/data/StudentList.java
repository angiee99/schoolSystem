package cz.uhk.system.data;
import java.util.ArrayList;
import java.util.List;
public class StudentList {
    private List<Student> studentList  = new ArrayList<>();
    private int size;
    private boolean isSorted;
    private int budgetStCount;
    private int scholarshipStCount;

    public StudentList(){
        this.size = 0;
        this.isSorted = false;
        this.budgetStCount = 0;
        this.scholarshipStCount = 0;
    }
    public StudentList(List<Student> students){
        this.studentList = students;
        for (Student student: students
             ) {
            if(!student.isOnContract()){
                this.budgetStCount++;
            }
        }

        this.size = students.size();
        this.isSorted = false;
        this.scholarshipStCount = 0;
    }


//    public void setStudentList(List<Student> studentList) {
//        this.studentList = studentList;
//    }

    public void sort(){
        if(!isSorted){
            for (int i = 0; i < size; i++){
                for (int j = 0; j < size; j++){
                    if(this.studentList.get(i).getAvarage() > this.studentList.get(j).getAvarage()){
                        // if !studentList.get(i).onContract -> only on budget will be moved
                        Student temp = studentList.get(i);
                        studentList.set(i, studentList.get(j));
                        studentList.set(j, temp);
                    }
                }
            }
            isSorted = true;
        }
    }

    public void createRating(int scholarshipPercent){
        this.scholarshipStCount = countScholarshipStCount(scholarshipPercent);
        if(!isSorted){
            sort();
        }

    }
    public int countScholarshipStCount(int scholarshipPercent){
        return this.budgetStCount * scholarshipPercent /100;
    }

    public float getMinGrade(){
        if(scholarshipStCount > 0){
            return studentList.get(scholarshipStCount - 1).getAvarage();
        }
        else {
            System.out.println("The rating is not created.\n" +
                    "Please first create rating to get minimum grade for scholarship");
            return 0;
        }
    }
    public void add(Student student){
        this.studentList.add(student);
        this.size++;
        this.isSorted = false;
        if(!student.isOnContract()){
            this.budgetStCount++;
        }
    }
    public void remove(Student student){
        //remove by student object
        this.studentList.remove(student);
        this.size--;
        if(!student.isOnContract()){
            this.budgetStCount--;
        }
    }
    public void remove(int index){
        //remove student by index
        if(!studentList.get(index).isOnContract()){
            this.budgetStCount--;
        }
        this.studentList.remove(index);
        this.size--;
    }
    public void print(){
        for (int i = 0; i < this.size; i++) {
            System.out.println(this.studentList.get(i).getName());
        }
    }


    // getters and setters
     public List<Student> getStudentList() {
            return studentList;
    }
    public int getSize() {
        return size;
    }

    public int getBudgetStCount() {
        return budgetStCount;
    }

    public int getScholarshipStCount() {
        return scholarshipStCount;
    }

    public void setScholarshipStCount(int scholarshipStCount) {
        this.scholarshipStCount = scholarshipStCount;
    }

    public boolean isSorted() {
        return isSorted;
    }


    public static void main(String[] args) {
        ArrayList<Integer> grades = new ArrayList<Integer>();
        grades.add(1); grades.add(1); grades.add(1);
        Student st1 = new Student("angie", grades, false);
        Student st2 = new Student(st1);
        ArrayList<Integer> grades1 = new ArrayList<Integer>();
        grades1.add(4); grades1.add(5); grades.add(6);
        Student st3 = new Student("dani", grades1, false);
        Student st4 = new Student("lee", grades1, true);

        StudentList list = new StudentList();
        list.add(st1); list.add(st2);  list.add(st3);  list.add(st4);

        list.print();
        list.sort();
        list.createRating(50);
        System.out.println(list.getBudgetStCount());
        System.out.println(list.getScholarshipStCount());
    }
}
