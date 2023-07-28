package com.soondevjomer.schoolmanagementsystem.repository;

import com.soondevjomer.schoolmanagementsystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
