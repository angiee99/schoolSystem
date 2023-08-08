package cz.uhk.system.gui;

import cz.uhk.system.data.Student;
import cz.uhk.system.data.StudentList;
import cz.uhk.system.fileModule.CSVManager;
import cz.uhk.system.fileModule.FileManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends JFrame {
    private StudentList students = new StudentList();
    private StudentList originalStudents = new StudentList();
    private StudentTableModel model = new StudentTableModel();
    private JTable table;
    private JPanel rightPanel;

    private JTextField tfName;
    private JTextField tfGrades;
    private JCheckBox inEnglishCheckBox;
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
        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(300, rightPanel.getPreferredSize().height));

        createAddStudent();
        createSearchPanel();
        createActionPanel();

        add(rightPanel, BorderLayout.EAST);
    }

    private void createActionPanel() {
        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 20, 30));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        buttonPanel.setPreferredSize(new Dimension(rightPanel.getWidth(), 100));

        JButton btDelete = new JButton("Delete");
        JButton btSort = new JButton("Sort");
        JButton btReadFromFile = new JButton("Read from File");
        JButton btWriteToFile = new JButton("Write to File");
        JButton btWriteRating = new JButton("Write rating");
// sort
        buttonPanel.add(btDelete);
        btDelete.addActionListener((e)->
                model.deleteStudent(table.getSelectedRow()));

        buttonPanel.add(btSort);
        btSort.addActionListener((e) -> {
            students.sort();
            model.fireTableDataChanged();
        });

// read from file button
        buttonPanel.add(btReadFromFile);
        btReadFromFile.addActionListener((e) -> {
            String fileName = JOptionPane.showInputDialog(this, "Enter the file name:");
            if (fileName != null && !fileName.isEmpty()) {
                try {
                    readFile(fileName);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Failed to read file: " + fileName,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

// write to file
        buttonPanel.add(btWriteToFile);
        btWriteToFile.addActionListener((e) -> {
            String fileName = JOptionPane.showInputDialog(this, "Enter the file name:");
            if (fileName != null && !fileName.isEmpty()) {
                try {
                    writeFile(fileName);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Failed to write to file: " + fileName,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // write to file
        buttonPanel.add(btWriteRating);
        btWriteRating.addActionListener((e) -> {
            String fileName = JOptionPane.showInputDialog(this,
                                        "Enter the file name to write rating:");
            if (fileName != null && !fileName.isEmpty()) {
                try {
                    writeRating(fileName);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Failed to write to file: " + fileName,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        rightPanel.add(buttonPanel, BorderLayout.CENTER);
    }

    private void createSearchPanel() {
        //Search field
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search"));
        JButton btSearch = new JButton("Search");
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

        rightPanel.add(searchPanel, BorderLayout.SOUTH);
    }

    private void createAddStudent() {
        // Insert student fields -> mb this to popup window
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 3, 1));
        formPanel.setBorder(BorderFactory.createTitledBorder("New student"));
        JButton btAdd = new JButton("Add");
        formPanel.add(new JLabel("Name"));
        tfName = new JTextField(15);
        formPanel.add(tfName);

        formPanel.add(new JLabel("Grades"));
        tfGrades = new JTextField("1, 2, 3", 10);
        formPanel.add(tfGrades);

        inEnglishCheckBox = new JCheckBox("In English");
        formPanel.add(inEnglishCheckBox);

        formPanel.add(btAdd);
        btAdd.addActionListener((e) -> addStudent()); //mb here CATCH

        rightPanel.add(formPanel, BorderLayout.NORTH);

    }

    private void writeFile(String fileName) throws IOException {
        FileManager fileManager = new CSVManager();
        fileManager.write(fileName, students);
        JOptionPane.showMessageDialog(this, "File successfully written: " + fileName,
                "Write File", JOptionPane.INFORMATION_MESSAGE);
    }
    private void writeRating(String fileName) throws IOException{
        students.createRating(40);
        FileManager fileManager = new CSVManager();
        ((CSVManager) fileManager).writeRating(fileName, students);
        JOptionPane.showMessageDialog(this, "File successfully written: " + fileName,
                "Write File", JOptionPane.INFORMATION_MESSAGE);
    }
    private void readFile(String fileName) throws IOException {
        FileManager fileManager = new CSVManager();
        StudentList newStudents = fileManager.read(fileName);
        if (newStudents != null) {
            students = newStudents;
            model.setStudents(students);
            JOptionPane.showMessageDialog(this, "File successfully read: " + fileName,
                    "Read File", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to read file: " + fileName,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
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
        model.setStudents(originalStudents); // resets the table to display all students
    }

    private void addStudent() {

        String gradesText = tfGrades.getText();
        boolean inEnglish = inEnglishCheckBox.isSelected();
        // gradesText to ArrayList<Integer>
        ArrayList<Integer> grades = new ArrayList<>();
        String[] gradesArray = gradesText.split(",");
        for (String grade : gradesArray) {
            grades.add(Integer.parseInt(grade.trim()));
        }

        try {
            Student st = new Student(tfName.getText(), grades, inEnglish);

            students.add(st);
            model.fireTableDataChanged();

            // clear text fields
            tfName.setText("");
            tfGrades.setText("");
            inEnglishCheckBox.setSelected(false);
        }
        //???
        catch (IllegalArgumentException ex){
            JOptionPane.showMessageDialog(this, "Failed to create a student" + ex,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch (RuntimeException ex){
            JOptionPane.showMessageDialog(this, "Failed to create a student" + ex,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initData() {
        FileManager fileManager = new CSVManager();
        try {
            originalStudents = fileManager.read("students/1.csv");
            students = originalStudents;
            model.setStudents(students);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to read student from file.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class StudentTableModel extends AbstractTableModel {
        private String[] columnNames = {"Name", "Grades", "In English", "Average"};

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
                s.setInEnglish((Boolean) aValue);
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
                    return student.isInEnglish();
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




