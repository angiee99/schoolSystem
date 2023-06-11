package cz.uhk.system.data;
import java.util.ArrayList;
import java.util.List;
public class StudentList {
    private List<Student> studentList  = new ArrayList<>();
    private int size;
    private boolean isSorted;
    private int englishStCount;
    private int scholarshipStCount;

    public StudentList(){
        this.size = 0;
        this.isSorted = false;
        this.englishStCount = 0;
        this.scholarshipStCount = 0;
    }
    public StudentList(List<Student> students){
        this.studentList = students;
        for (Student student: students
             ) {
            if(!student.isInEnglish()){
                this.englishStCount++;
            }
        }

        this.size = students.size();
        this.isSorted = false;
        this.scholarshipStCount = 0;
    }

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
        return this.size* scholarshipPercent /100;
    }

    public float getMinGrade(){
        if(scholarshipStCount < 1){
            createRating(40);
        }
        return studentList.get(scholarshipStCount - 1).getAvarage();
    }
    public void add(Student student){
        this.studentList.add(student);
        this.size++;
        this.isSorted = false;
        if(!student.isInEnglish()){
            this.englishStCount++;
        }
    }
    public void remove(Student student){
        //remove by student object
        this.studentList.remove(student);
        this.size--;
        if(!student.isInEnglish()){
            this.englishStCount--;
        }
    }
    public void remove(int index){
        //remove student by index
        if(!studentList.get(index).isInEnglish()){
            this.englishStCount--;
        }
        this.studentList.remove(index);
        this.size--;
    }
    public Student getStudent(int index){
        return  this.studentList.get(index);
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

    public int getEnglishStCount() {
        return englishStCount;
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

    public List<Student> search(String searchText) {
        List<Student> searchResults = new ArrayList<>();

        for (Student student : studentList) {
            if (studentContainsText(student, searchText)) {
                searchResults.add(student);
            }
        }

        return searchResults;
    }

    private boolean studentContainsText(Student student, String searchText) {
        String lowercaseSearchText = searchText.toLowerCase();

        if (student.getName().toLowerCase().contains(lowercaseSearchText)) {
            return true;
        }

        for (Integer grade : student.getGrades()) {
            if (grade.toString().contains(searchText)) {
                return true;
            }
        }

        if (Boolean.toString(student.isInEnglish()).contains(searchText)) {
            return true;
        }

        if (Double.toString(student.getAvarage()).contains(searchText)) {
            return true;
        }

        return false;
    }



//    public static void main(String[] args) {
//        ArrayList<Integer> grades = new ArrayList<Integer>();
//        grades.add(1); grades.add(1); grades.add(1);
//        Student st1 = new Student("angie", grades, false);
//        Student st2 = new Student(st1);
//        ArrayList<Integer> grades1 = new ArrayList<Integer>();
//        grades1.add(4); grades1.add(5); grades.add(6);
//        Student st3 = new Student("dani", grades1, false);
//        Student st4 = new Student("lee", grades1, true);
//
//        StudentList list = new StudentList();
//        list.add(st1); list.add(st2);  list.add(st3);  list.add(st4);
//
//        list.print();
//        list.sort();
//        list.createRating(50);
//        System.out.println(list.getBudgetStCount());
//        System.out.println(list.getScholarshipStCount());
//    }
}
