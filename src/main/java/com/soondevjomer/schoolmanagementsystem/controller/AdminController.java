package com.soondevjomer.schoolmanagementsystem.controller;

import com.soondevjomer.schoolmanagementsystem.dto.AdminDto;
import com.soondevjomer.schoolmanagementsystem.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admins")
@RequiredArgsConstructor
public class AdminController {
    
    private final AdminService adminService;
    
    @PostMapping
    public ResponseEntity<AdminDto> addAdmin(@RequestBody AdminDto adminDto) {
        
        return new ResponseEntity<>(adminService.addAdmin(adminDto), HttpStatus.CREATED);
    }
    
    @GetMapping("/{adminId}")
    public ResponseEntity<AdminDto> getAdmin(@PathVariable Integer adminId) {
        
        return ResponseEntity.ok(adminService.getAdmin(adminId));
    }

    @GetMapping
    public ResponseEntity<Page<AdminDto>> getAdmins(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {

        return ResponseEntity.ok(adminService.getAdmins(page, size, sortField, sortOrder));
    }

    @PutMapping("/{adminId}")
    public ResponseEntity<AdminDto> updateAdmin(
        @PathVariable Integer adminId,
        @RequestBody AdminDto adminDto) {

        return ResponseEntity.ok(adminService.updateAdmin(adminId, adminDto));
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Integer adminId) {

        return ResponseEntity.ok(adminService.deleteAdmin(adminId));
    }
}
