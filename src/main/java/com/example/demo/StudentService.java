package com.example.demo;

import exception.RessourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StudentService {

    private final StudentDAO studentDAO;

    @Autowired
    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    public Student getStudent(Long id) {
        return studentDAO.getStudentById(id)
                .orElseThrow(() -> new RessourceNotFound("Student with id " + id + " not found"));
    }



}
