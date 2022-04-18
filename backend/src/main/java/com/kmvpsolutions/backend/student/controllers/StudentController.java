package com.kmvpsolutions.backend.student.controllers;

import com.kmvpsolutions.backend.student.model.Gender;
import com.kmvpsolutions.backend.student.model.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author kalil.peixoto
 * @date 3/31/22 7:06 PM
 * @email kalilmvp@gmail.com
 */
@RestController
@RequestMapping(path = "api/v1/students")
public class StudentController {

    @GetMapping
    public List<Student> getAllStudents() {
        return Arrays.asList(
                new Student(1L,
                    "Kalil Peixoto",
                    "kalilmvp@gmail.com",
                    Gender.MALE),
                new Student(2L,
                        "Kalila Peixoto",
                        "kalilasmvp@gmail.com",
                        Gender.FEMALE),
                new Student(3L,
                        "Kalilaa Peixoto",
                        "kalilasmvp@gmail.com",
                        Gender.FEMALE)
        );
    }
}
