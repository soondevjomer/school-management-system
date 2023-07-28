package com.soondevjomer.schoolmanagementsystem.controller;

import com.soondevjomer.schoolmanagementsystem.dto.SubjectDto;
import com.soondevjomer.schoolmanagementsystem.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;
    @PostMapping
    public ResponseEntity<SubjectDto> addSubject(@RequestBody SubjectDto subjectDto) {

        return new ResponseEntity<>(subjectService.addSubject(subjectDto), HttpStatus.CREATED);
    }

    @GetMapping("/{subjectId}")
    public ResponseEntity<SubjectDto> getSubject(@PathVariable Integer subjectId) {

        return ResponseEntity.ok(subjectService.getSubject(subjectId));
    }

    @GetMapping
    public ResponseEntity<Page<SubjectDto>> getSubjects(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {

        return ResponseEntity.ok(subjectService.getSubjects(page, size, sortField, sortOrder));
    }

    @PutMapping("/{subjectId}")
    public ResponseEntity<SubjectDto> updateSubject(
        @PathVariable Integer subjectId,
        @RequestBody SubjectDto subjectDto) {

        return ResponseEntity.ok(subjectService.updateSubject(subjectId, subjectDto));
    }

    @DeleteMapping("{subjectId}")
    public ResponseEntity<String> deleteSubject(@PathVariable Integer subjectId) {

        return ResponseEntity.ok(subjectService.deleteSubject(subjectId));
    }
}
