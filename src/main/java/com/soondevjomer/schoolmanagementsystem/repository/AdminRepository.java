package com.soondevjomer.schoolmanagementsystem.repository;

import com.soondevjomer.schoolmanagementsystem.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
