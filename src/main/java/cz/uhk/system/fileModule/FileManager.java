package cz.uhk.system.fileModule;

import cz.uhk.system.data.Student;
import cz.uhk.system.data.StudentList;

import java.io.IOException;
import java.util.List;

public interface FileManager {
    StudentList read(String fname) throws IOException;
    void write(String fname, StudentList seznam) throws IOException;
}
