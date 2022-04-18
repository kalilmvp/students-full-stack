package com.kmvpsolutions.backend.student.model;

import lombok.*;

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
public class Student {
    private Long id;
    private String name;
    private String email;
    private Gender gender;
}
