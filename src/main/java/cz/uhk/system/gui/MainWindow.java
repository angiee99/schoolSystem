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
    private StudentList students = new StudentList();
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
            students = fileManager.read("students/1.csv");
            model.setStudents(students);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to read student data from file.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class StudentTableModel extends AbstractTableModel {
//        private List<Student> students;
        private String[] columnNames = {"Name", "Grades", "On Contract", "Average"};

//        public StudentTableModel() {
//            students = new ArrayList<>();
//        }

//        public void setStudents(List<Student> students) {
//            this.students = students;
//            fireTableDataChanged();
//        }
        public void setStudents(StudentList studentList) {
           students = studentList;
           fireTableDataChanged();
        }
        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            Student s = students.getStudent(rowIndex);
            if (columnIndex == 0) { //checkbox
                s.setName((String) aValue);
            } else if (columnIndex == 1) {
                s.setGrades((ArrayList<Integer>) aValue);
                fireTableCellUpdated(rowIndex,3);
            } else if (columnIndex == 2) {
                s.setOnContract((Boolean) aValue);
            }
        }

        public void deleteStudent(int rowIndex) {
            students.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }

        @Override
        public int getRowCount() {
            return students.getSize();
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
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            // change indexes
            return columnIndex == 0 || columnIndex == 2;
        }
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Student student = students.getStudent(rowIndex);
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
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0: return String.class;
                case 1: return ArrayList.class;
                case 2: return Boolean.class;
                case 3: return Double.class;
            }
            return super.getColumnClass(columnIndex);
        }


    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
    }
}




