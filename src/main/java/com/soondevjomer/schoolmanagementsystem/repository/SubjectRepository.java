package com.soondevjomer.schoolmanagementsystem.repository;

import com.soondevjomer.schoolmanagementsystem.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    Optional<Subject> findByCode(String code);
}
