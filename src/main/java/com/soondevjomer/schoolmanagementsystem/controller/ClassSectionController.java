package com.soondevjomer.schoolmanagementsystem.controller;

import com.soondevjomer.schoolmanagementsystem.dto.ClassSectionDto;
import com.soondevjomer.schoolmanagementsystem.dto.TeacherDto;
import com.soondevjomer.schoolmanagementsystem.service.ClassSectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/classSections")
@RequiredArgsConstructor
public class ClassSectionController {

    private final ClassSectionService classSectionService;

    @PostMapping
    public ResponseEntity<ClassSectionDto> addClassSection(@RequestBody ClassSectionDto classSectionDto) {

        return new ResponseEntity<>(classSectionService.addClassSection(classSectionDto), HttpStatus.CREATED);
    }

    @GetMapping("/{classSectionId}")
    public ResponseEntity<ClassSectionDto> getClassSection(@PathVariable Integer classSectionId) {

        return ResponseEntity.ok(classSectionService.getClassSection(classSectionId));
    }

    @GetMapping
    public ResponseEntity<Page<ClassSectionDto>> getClassSections(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {

        return ResponseEntity.ok(classSectionService.getClassSections(page, size, sortField, sortOrder));
    }

    @DeleteMapping("/{classSectionId}")
    public ResponseEntity<String> deleteClassSection(@PathVariable Integer classSectionId) {

        return ResponseEntity.ok(classSectionService.deleteClassSection(classSectionId));
    }
}
