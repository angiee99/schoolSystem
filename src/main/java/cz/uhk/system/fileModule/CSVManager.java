package cz.uhk.system.fileModule;

import cz.uhk.system.data.Student;
import cz.uhk.system.data.StudentList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVManager implements FileManager{
    //create student not in CSV Manager!!


    @Override
    public StudentList read(String fname) throws IOException {
        StudentList result = new StudentList();

        try (BufferedReader br = new BufferedReader(new FileReader(fname))) {
            Student st = new Student();
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

                result.add(new Student(record));
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
    public void write(String fname, StudentList list) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fname))) {
            writer.write(String.valueOf(list.getSize()));
            System.out.println(list.getSize());
            writer.newLine();

            for (int i = 0; i < list.getSize(); i++) {
                Student st = list.getStudent(i);

                // turn grades into a string
                String gradesString = new String();
                for (int j = 0; j < st.getGrades().size(); j++){
                    gradesString += st.getGrades().get(j) + ",";
                }

                writer.write(st.getName() + ","
                        + gradesString // GRADES are written in []
                        + st.isInEnglish());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IOException("Failed to open output file: " + fname, e);
        }
    }


    public void writeRating(String fname, StudentList list) throws IOException {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fname))) {
                for (int i = 0; i < list.getScholarshipStCount(); i++) {
                    writer.write(list.getStudentList().get(i).getName() + ","
                            + String.format("%.3f", list.getStudentList().get(i).getAvarage()));
                    writer.newLine();
                }
            } catch (IOException e) {
                throw new IOException("Failed to open output file: " + fname, e);
            }
        }
    }
