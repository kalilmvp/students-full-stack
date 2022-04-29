package com.kmvpsolutions.backend.student.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author kalil.peixoto
 * @date 3/31/22 7:02 PM
 * @email kalilmvp@gmail.com
 */
@ToString
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@SequenceGenerator(name = "student_id_seq", sequenceName = "student_id_seq", allocationSize = 1)
public class Student {

    @Id
    @GeneratedValue(
            generator = "student_id_seq",
            strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String name;
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private Gender gender;

    public Student(Long id, String name, Gender gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }
}
