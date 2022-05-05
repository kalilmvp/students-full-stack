package com.kmvpsolutions.backend.student.repositories;

import com.kmvpsolutions.backend.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author kalil.peixoto
 * @date 4/20/22 8:58 AM
 * @email kalilmvp@gmail.com
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsStudentByEmail(String email);
    Student findStudentByEmail(String mail);
}
