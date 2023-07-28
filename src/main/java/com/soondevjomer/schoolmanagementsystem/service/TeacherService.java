package com.soondevjomer.schoolmanagementsystem.service;

import com.soondevjomer.schoolmanagementsystem.dto.TeacherDto;
import org.springframework.data.domain.Page;

public interface TeacherService {

    TeacherDto addTeacher(TeacherDto teacherDto);

    TeacherDto getTeacher(Integer teacherId);

    Page<TeacherDto> getTeachers(Integer page, Integer size, String sortField, String sortOrder);

    TeacherDto updateTeacher(Integer teacherId, TeacherDto teacherDto);

    String deleteTeacher(Integer teacherId);
}
