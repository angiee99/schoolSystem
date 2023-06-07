package cz.uhk.system.gui;

import cz.uhk.system.data.Student;
import cz.uhk.system.data.StudentList;
import cz.uhk.system.fileModule.CSVManager;
import cz.uhk.system.fileModule.FileManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends JFrame {
    private StudentList students = new StudentList();
    private StudentList originalStudents = new StudentList();
    private StudentTableModel model = new StudentTableModel();
    private JTable table;
    private JButton btDelete = new JButton();
    private JButton btAdd = new JButton("Add");
    private JButton btSort = new JButton("Sort");
    private JButton btSearch = new JButton("Search");
    private JButton btShowTable = new JButton("Show Table");
    private JButton btReadFromFile = new JButton("Read from File");
    private JButton btWriteToFile = new JButton("Write to File");
    private JTextField tfName;
    private JTextField tfGrades;
    private JCheckBox onContractCheckBox;
    private JTextField tfSearch;



    public MainWindow() {
        super("School System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initData();

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        createRightPanel();

        setSize(860, 640);
        setVisible(true);
    }

    private void createRightPanel() {
        JPanel rightPanel = new JPanel();
//        rightPanel.setMaximumSize(new Dimension(50, 640));
        rightPanel.setLayout(new BorderLayout());


// Insert student fields
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 3, 1));
        formPanel.setBorder(BorderFactory.createTitledBorder("New student"));
        formPanel.add(new JLabel("Name"));
        tfName = new JTextField(15);
        formPanel.add(tfName);

        formPanel.add(new JLabel("Grades"));
        tfGrades = new JTextField("1, 2, 3, ..", 10);
        formPanel.add(tfGrades);

        onContractCheckBox = new JCheckBox("On Contract");
        formPanel.add(onContractCheckBox);

        formPanel.add(btAdd);
        btAdd.addActionListener((e) -> addStudent());

        rightPanel.add(formPanel, BorderLayout.NORTH);

// Search field
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search"));
        tfSearch = new JTextField(15);

    // Add DocumentListener to the search field
        tfSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (tfSearch.getText().isEmpty()) {
                    showTable(); // Call showTable when the search field is cleared
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

    // search panel
        searchPanel.add(tfSearch);
        searchPanel.add(btSearch);
        btSearch.addActionListener((e) -> {
            String searchText = tfSearch.getText();
            searchStudents(searchText);
        });

        rightPanel.add(searchPanel, FlowLayout.CENTER);
// Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));

// sort
        buttonPanel.add(btSort);
        btSort.addActionListener((e) -> {
            students.sort();
            model.fireTableDataChanged();
        });

// read from file
        buttonPanel.add(btReadFromFile);

// write to file
        buttonPanel.add(btWriteToFile);

        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(rightPanel, BorderLayout.EAST);
    }
    private void searchStudents(String searchText) {
        List<Student> searchResults = students.search(searchText);

        if (searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No students found.",
                    "Search Results", JOptionPane.INFORMATION_MESSAGE);
        } else {
            model.setStudents(new StudentList(searchResults));
            JOptionPane.showMessageDialog(this,
                    "Found " + searchResults.size() + " student(s).", "Search Results",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void showTable() {
        model.setStudents(originalStudents); // Reset the table to display all students
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
            originalStudents = fileManager.read("students/1.csv");
            students = originalStudents;
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




