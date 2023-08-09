package com.soondevjomer.schoolmanagementsystem.repository;

import com.soondevjomer.schoolmanagementsystem.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query("SELECT s FROM Student s " +
            "WHERE lower(s.person.name.firstName) LIKE %:name% " +
            "OR lower(s.person.name.middleName) LIKE %:name% " +
            "OR lower(s.person.name.lastName) LIKE %:name%")
    Page<Student> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT s FROM Student s " +
            "WHERE lower(s.classSection.class_.name) LIKE %:classSection% " +
            "OR lower(s.classSection.section.name) LIKE %:classSection%")
    Page<Student> findByClassSectionContainingIgnoreCase(String classSection, Pageable pageable);
}
