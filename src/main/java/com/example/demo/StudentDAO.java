package com.example.demo;

import java.util.List;
import java.util.Optional;

public interface StudentDAO {

    List<Student> getAllStudents();
    Optional<Student> getStudentById(Long id);
    void insertStudent(Student student);
}
