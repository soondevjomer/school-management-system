package com.soondevjomer.schoolmanagementsystem.repository;

import com.soondevjomer.schoolmanagementsystem.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
