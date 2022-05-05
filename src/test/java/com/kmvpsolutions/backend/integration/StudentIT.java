package com.kmvpsolutions.backend.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.kmvpsolutions.backend.student.model.Gender;
import com.kmvpsolutions.backend.student.model.Student;
import com.kmvpsolutions.backend.student.repositories.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author kalil.peixoto
 * @date 5/5/22 8:54 PM
 * @email kalilmvp@gmail.com
 */
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
@AutoConfigureMockMvc
public class StudentIT {

    private static final String URL_STUDENT_POST = "/api/v1/students";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentRepository studentRepository;

    private Faker faker = new Faker();

    @Test
    void canRegisterNewStudent() throws Exception {
        //given
        Student student = new Student(
                String.format("%s %s", faker.name().firstName(), faker.name().lastName()),
                faker.internet().emailAddress(),
                Gender.MALE);

        //when
        ResultActions resultActions = this.mockMvc.perform(post(URL_STUDENT_POST)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(student)));

        //then
        resultActions.andExpect(status().isOk());

        boolean exists = this.studentRepository.existsStudentByEmail(student.getEmail());
        assertThat(exists).isTrue();
    }

    @Test
    void canDeleteStudent() throws Exception {
        //given
        Student student = new Student(
                String.format("%s %s", faker.name().firstName(), faker.name().lastName()),
                faker.internet().emailAddress(),
                Gender.MALE);

        //when
         // first insert the student
        ResultActions resultActions = this.mockMvc.perform(post(URL_STUDENT_POST)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(student)));
        resultActions.andExpect(status().isOk());

        //then
         // search for the student inserted by emai
        Student studentCreated = this.studentRepository.findStudentByEmail(student.getEmail());
        ResultActions resultActionsDelete =
                this.mockMvc.perform(delete(String.format("%s/%d", URL_STUDENT_POST, studentCreated.getId())));
        resultActionsDelete.andExpect(status().isOk());
    }
}
