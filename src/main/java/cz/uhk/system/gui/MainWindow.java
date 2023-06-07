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
    JTextField tfName;
    JTextField tfGrades;
    JCheckBox onContractCheckBox;


    public MainWindow() {
        super("School System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initData();

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        createSouthPanel();

        setSize(860, 640);
        setVisible(true);
    }

    private void createSouthPanel() {
        JPanel southPanel = new JPanel();
//        southPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        southPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
//delete bt
        btDelete = new JButton("Delete Student");
        btDelete.addActionListener((e) ->
                model.deleteStudent(table.getSelectedRow()));

        southPanel.add(btDelete);
// delete bt
        JPanel formPanel = new JPanel();
        formPanel.setBorder(BorderFactory.createTitledBorder("New student"));
//        formPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        formPanel.add(new JLabel("Name"));
        tfName = new JTextField(15);
        formPanel.add(tfName);

        formPanel.add(new JLabel("Grades"));
        tfGrades = new JTextField("0", 10);
        formPanel.add(tfGrades);

        onContractCheckBox = new JCheckBox("On Contract");
        formPanel.add(onContractCheckBox);

//        southPanel.add(new JLabel("On Contract"));
//        tfCena = new JTextField("0", 10);
//        southPanel.add(tfCena);

        JButton btAdd = new JButton("Add");
        formPanel.add(btAdd);
        btAdd.addActionListener((e) -> {
            addStudent();
        });

        southPanel.add(formPanel, BorderLayout.WEST);
        add(southPanel, BorderLayout.SOUTH);

    }

    private void addStudent() {

        String gradesText = tfGrades.getText();
        boolean onContract = onContractCheckBox.isSelected();
        // Convert gradesText to ArrayList<Integer>
        ArrayList<Integer> grades = new ArrayList<>();
        String[] gradesArray = gradesText.split(",");
        for (String grade : gradesArray) {
            grades.add(Integer.parseInt(grade.trim()));
        }

        Student st = new Student(tfName.getText(), grades,onContract);
        students.add(st);
        model.fireTableDataChanged();

        // Clear text fields
        tfName.setText("");
        tfGrades.setText("");
        onContractCheckBox.setSelected(false);

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




