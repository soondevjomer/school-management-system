package com.soondevjomer.schoolmanagementsystem.repository;

import com.soondevjomer.schoolmanagementsystem.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
}
