package com.kmvpsolutions.backend.student.services;

import com.kmvpsolutions.backend.student.model.Student;
import com.kmvpsolutions.backend.student.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kalil.peixoto
 * @date 4/20/22 8:59 AM
 * @email kalilmvp@gmail.com
 */
@AllArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return this.studentRepository.findAll();
    }
}
