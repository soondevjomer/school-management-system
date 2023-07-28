package com.soondevjomer.schoolmanagementsystem.controller;

import com.soondevjomer.schoolmanagementsystem.dto.StudentDto;
import com.soondevjomer.schoolmanagementsystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    @PostMapping
    public ResponseEntity<StudentDto> addStudent(@RequestBody StudentDto studentDto) {

        return new ResponseEntity<>(studentService.addStudent(studentDto), HttpStatus.CREATED);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable Integer studentId) {

        return ResponseEntity.ok(studentService.getStudent(studentId));
    }

    @GetMapping
    public ResponseEntity<Page<StudentDto>> getStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {

        return ResponseEntity.ok(studentService.getStudents(page, size, sortField, sortOrder));
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentDto> updateStudent(
        @PathVariable Integer studentId,
        @RequestBody StudentDto studentDto) {

        return ResponseEntity.ok(studentService.updateStudent(studentId, studentDto));
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable Integer studentId) {

        return ResponseEntity.ok(studentService.deleteStudent(studentId));
    }

}
