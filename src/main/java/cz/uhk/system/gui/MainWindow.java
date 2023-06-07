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
    private JTable table  = new JTable(model);
    private JButton deleteButton = new JButton();

    public MainWindow() {
        super("School Sysytem");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initData();

//
//        vytvorNabidka(); -> top bar menu
//        createLeftPanel();
//        add(new JScrollPane(tabulka), BorderLayout.CENTER);
//        tabulka.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

//        model = new StudentTableModel();
//        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);


        deleteButton = new JButton("Delete Student");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    model.deleteStudent(selectedRow);
                }
            }
        });
        add(deleteButton, BorderLayout.SOUTH);
        setSize(860, 640);
        //pack();
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
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                MainWindow mainWindow = new MainWindow();
//                mainWindow.initData();
//                mainWindow.setVisible(true);
//            }
//        });
    }




}
