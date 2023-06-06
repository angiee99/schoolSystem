package cz.uhk.system.fileModule;

import cz.uhk.system.data.Student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVManager implements FileManager{

    private Student createStudent(List<String> data) {
        if (data.get(0).isEmpty() || data.get(0).trim().isEmpty()) {
            String msg = "The first record in a table row (here: " + data.get(0) +
                    ") should be the name of a student, not an empty string.";
            throw new IllegalArgumentException(msg);
        }

        int contractValueInd = data.size() - 1;
        int gradesCount = contractValueInd - 1;

        ArrayList<Integer> gradesList = new ArrayList<>();
        boolean isOnContract;

        for (int i = 1; i < gradesCount + 1; i++) {
            try {
                int grade = Integer.parseInt(data.get(i));
                gradesList.add(grade);
            } catch (NumberFormatException e) {
                String msg = "Error when transforming file content to float number at column " + i;
                throw new RuntimeException(msg, e);
            }
        }

        String contractValue = data.get(contractValueInd);

        if (contractValue.equalsIgnoreCase("FALSE")) {
            isOnContract = false;
        } else if (contractValue.equalsIgnoreCase("TRUE")) {
            isOnContract = true;
        } else {
            throw new IllegalArgumentException("The last value in a file row should be either TRUE or FALSE");
        }

        return new Student(data.get(0), gradesList, isOnContract);
    }

    @Override
    public List<Student> read(String fname) throws IOException {
        List<Student> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fname))) {
            String line = br.readLine();
            int initialCount = Integer.parseInt(line);
            int count = 0;

            List<String> record = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                count++;
                record.clear();
                String[] words = line.split(",");

                for (String word : words) {
                    record.add(word);
                }

                result.add(createStudent(record));
            }

            if (count != initialCount) {
                String msg = "The count of students written at the beginning of file " + initialCount +
                        " does not match the real count of records in the file " + count;
                throw new IllegalArgumentException(msg);
            }
        } catch (IOException e) {
            String msg = "Failed to open input file: " + fname;
            throw new RuntimeException(msg, e);
        }

        return result;
    }

    @Override
    public void write(String fname, List<Student> seznam) throws IOException {

    }
}
