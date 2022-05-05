package com.kmvpsolutions.backend.student.services;

import com.kmvpsolutions.backend.exceptions.BadRequestException;
import com.kmvpsolutions.backend.exceptions.StudentNotFoundException;
import com.kmvpsolutions.backend.student.model.Gender;
import com.kmvpsolutions.backend.student.model.Student;
import com.kmvpsolutions.backend.student.repositories.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.data.domain.Sort.by;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    public static final String STUDENT_WITH_THIS_EMAIL_ALREADY_EXISTS = "Student with this email already exists";
    public static final String STUDENT_NOT_FOUND = "Student Not Found";
    @Mock
    private StudentRepository studentRepository;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        this.studentService = new StudentService(this.studentRepository);
    }

    @Test
    void canGetAllStudents() {
        //given

        //when
        this.studentService.getAllStudents();

        //then
        verify(this.studentRepository).findAll(by(Sort.Direction.DESC, "id"));
    }

    @Test
    void canAddStudent() {
        //given
        String email = "kalilmvp@gmail.com";
        Student student = new Student("Kalil Peixoto", email, Gender.MALE);

        //when
        this.studentService.addStudent(student);

        //then
        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(this.studentRepository).save(studentCaptor.capture());

        Student studentValue = studentCaptor.getValue();
        assertThat(studentValue).isEqualTo(student);
    }

    @Test
    void willThrowWhenEmailIsTaken() {
        //given
        String email = "kalilmvp@gmail.com";
        Student student = new Student("Kalil Peixoto", email, Gender.MALE);

        given(this.studentRepository.existsStudentByEmail(student.getEmail()))
                .willReturn(Boolean.TRUE);

        //when
        //then
        assertThatThrownBy(() -> this.studentService.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(STUDENT_WITH_THIS_EMAIL_ALREADY_EXISTS);

        // check that this method is never called if the above error happens
        verify(this.studentRepository, never()).save(any());
    }

    @Test
    void canRemoveStudent() {
        //given
        Long id = 1L;
        given(this.studentRepository.findById(id))
                .willReturn(Optional.of(new Student()));

        //when
        this.studentService.removeStudent(id);

        //then
        verify(this.studentRepository).deleteById(id);
    }

    @Test
    void willThrowWhenStudentNotFound() {
        //given
        Long id = 1L;
        given(this.studentRepository.findById(id))
                .willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> this.studentService.removeStudent(id))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining(STUDENT_NOT_FOUND);

        // check that this method is never called if the above error happens
        verify(this.studentRepository, never()).deleteById(id);
    }
}
