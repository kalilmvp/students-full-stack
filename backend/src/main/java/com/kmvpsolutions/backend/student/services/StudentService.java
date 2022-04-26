package com.kmvpsolutions.backend.student.services;

import com.kmvpsolutions.backend.exceptions.BadRequestException;
import com.kmvpsolutions.backend.exceptions.StudentNotFoundException;
import com.kmvpsolutions.backend.student.model.Student;
import com.kmvpsolutions.backend.student.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import static org.springframework.data.domain.Sort.*;
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
        return this.studentRepository.findAll(by(Direction.DESC, "id"));
    }

    public void addStudent(Student student) {
        boolean existsStudentByEmail =
                this.studentRepository.existsStudentByEmail(student.getEmail());

        if (existsStudentByEmail) {
            throw new BadRequestException("Student with this email already exists");
        }

        this.studentRepository.save(student);
    }

    public void removeStudent(Long id) {
        this.studentRepository.findById(id).orElseThrow(() ->
                new StudentNotFoundException("Student Not Found"));

        this.studentRepository.deleteById(id);
    }
}
