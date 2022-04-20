package com.kmvpsolutions.backend.student.model;

import lombok.*;

import javax.persistence.*;

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
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public Student(Long id, String name, Gender gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }
}
