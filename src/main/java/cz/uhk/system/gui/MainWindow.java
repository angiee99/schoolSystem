package cz.uhk.system.gui;

import cz.uhk.system.data.Student;
import cz.uhk.system.data.StudentList;
import cz.uhk.system.fileModule.CSVManager;
import cz.uhk.system.fileModule.FileManager;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends JFrame {
    private StudentList studentList = new StudentList();
    private StudentTableModel model = new StudentTableModel();
    private JTable table;
    private JButton btDelete = new JButton();

    public MainWindow() {
        super("School System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initData();

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
// create down panel
        btDelete = new JButton("Delete Student");
        btDelete.addActionListener((e)->
                model.deleteStudent(table.getSelectedRow()));

        add(btDelete, BorderLayout.SOUTH);
// create down panel
        setSize(860, 640);
        setVisible(true);
    }

    public void initData() {
        FileManager fileManager = new CSVManager();
        try {
            List<Student> students = fileManager.read("students/1.csv");
            model.setStudents(students);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to read student data from file.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class StudentTableModel extends AbstractTableModel {
        private List<Student> students;
        private String[] columnNames = {"Name", "Grades", "On Contract", "Average"};

        public StudentTableModel() {
            students = new ArrayList<>();
        }

        public void setStudents(List<Student> students) {
            this.students = students;
            fireTableDataChanged();
        }

        public void deleteStudent(int rowIndex) {
            students.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }

        @Override
        public int getRowCount() {
            return students.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Student student = students.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return student.getName();
                case 1:
                    return student.getGrades();
                case 2:
                    return student.isOnContract();
                case 3:
                    return student.getAvarage();
                default:
                    return null;
            }
        }
    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
    }
}




