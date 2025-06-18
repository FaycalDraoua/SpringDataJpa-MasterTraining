package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Repository
public class StudentJPADataAccessService implements StudentDAO{

    private final StudentRepository studentRepository;

    @Autowired
    public StudentJPADataAccessService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        // Implementation to retrieve all students from the database
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public void insertStudent(Student student) {
        studentRepository.save(student);
    }

}
