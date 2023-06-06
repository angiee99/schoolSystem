package cz.uhk.system.gui;

import cz.uhk.system.data.Student;
import cz.uhk.system.data.StudentList;
import cz.uhk.system.fileModule.CSVManager;
import cz.uhk.system.fileModule.FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FileManager fileManager = new CSVManager();

        try {
            // Specify the file name or path to read from
            String fileName = "students/1.csv";

            // Call the read method to read student data from the CSV file
            List<Student> students = fileManager.read(fileName);

            // Print the student data
            for (Student student : students) {
                System.out.println(student.getName());
            }
        } catch (IOException e) {
            // Handle any IO exceptions that may occur
            e.printStackTrace();
        }
    }
}
