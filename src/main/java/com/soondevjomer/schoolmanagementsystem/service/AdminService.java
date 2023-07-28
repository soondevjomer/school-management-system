package com.soondevjomer.schoolmanagementsystem.service;

import com.soondevjomer.schoolmanagementsystem.dto.AdminDto;
import org.springframework.data.domain.Page;

public interface AdminService {

    AdminDto addAdmin(AdminDto adminDto);

    AdminDto getAdmin(Integer adminId);

    Page<AdminDto> getAdmins(Integer page, Integer size, String sortField, String sortOrder);

    AdminDto updateAdmin(Integer adminId, AdminDto adminDto);

    String deleteAdmin(Integer adminId);
}
