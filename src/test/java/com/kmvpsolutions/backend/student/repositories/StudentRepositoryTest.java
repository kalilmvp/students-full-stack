package com.kmvpsolutions.backend.student.repositories;

import com.kmvpsolutions.backend.student.model.Gender;
import com.kmvpsolutions.backend.student.model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository repository;

    @AfterEach
    void tearDown() {
        this.repository.deleteAll();
    }

    @Test
    void itShouldCheckIfStudentExistsByEmail() {
        //given
        String email = "kalilmvp@gmail.com";
        Student student = new Student("Kalil Peixoto", email, Gender.MALE);

        this.repository.save(student);

        //when
        boolean exists = this.repository.existsStudentByEmail(email);

        //then
        assertThat(exists).isTrue();
    }

    @Test
    void itShouldCheckIfStudentEmailDoesNotExist() {
        //given
        String email = "kalilmvp@gmail.com";

        //when
        boolean exists = this.repository.existsStudentByEmail(email);

        //then
        assertThat(exists).isFalse();
    }
}
