package com.kmvpsolutions.backend.student.controllers;

import com.kmvpsolutions.backend.student.model.Student;
import com.kmvpsolutions.backend.student.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author kalil.peixoto
 * @date 3/31/22 7:06 PM
 * @email kalilmvp@gmail.com
 */
@RestController
@RequestMapping(path = "api/v1/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<Student> getAllStudents() {
        return this.studentService.getAllStudents();
    }

    @PostMapping
    public void addStudent(@RequestBody Student student) {
        this.studentService.addStudent(student);
    }
}
