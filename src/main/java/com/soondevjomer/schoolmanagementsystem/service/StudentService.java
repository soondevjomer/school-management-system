package com.soondevjomer.schoolmanagementsystem.service;


import com.soondevjomer.schoolmanagementsystem.dto.StudentDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {

    StudentDto addStudent(StudentDto studentDto);

    StudentDto getStudent(Integer studentId);

    Page<StudentDto> getStudents(Integer page, Integer size, String sortField, String sortOrder);

    StudentDto updateStudent(Integer studentId, StudentDto studentDto);
    String deleteStudent(Integer studentId);
}
