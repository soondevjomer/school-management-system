package com.soondevjomer.schoolmanagementsystem.controller;

import com.soondevjomer.schoolmanagementsystem.dto.TeacherDto;
import com.soondevjomer.schoolmanagementsystem.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping
    public ResponseEntity<TeacherDto> addTeacher(@RequestBody TeacherDto teacherDto) {

        return new ResponseEntity<>(teacherService.addTeacher(teacherDto), HttpStatus.CREATED);
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<TeacherDto> getTeacher(@PathVariable Integer teacherId) {

        return ResponseEntity.ok(teacherService.getTeacher(teacherId));
    }

    @GetMapping
    public ResponseEntity<Page<TeacherDto>> getTeachers(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {

        return ResponseEntity.ok(teacherService.getTeachers(page, size, sortField, sortOrder));
    }

    @PutMapping("/{teacherId}")
    public ResponseEntity<TeacherDto> updateTeacher(
        @PathVariable Integer teacherId,
        @RequestBody TeacherDto teacherDto) {

        return ResponseEntity.ok(teacherService.updateTeacher(teacherId, teacherDto));
    }

    @DeleteMapping("/{teacherId}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Integer teacherId) {

        return ResponseEntity.ok(teacherService.deleteTeacher(teacherId));
    }

}
