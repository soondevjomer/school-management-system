package com.soondevjomer.schoolmanagementsystem.controller;

import com.soondevjomer.schoolmanagementsystem.dto.AssessmentDto;
import com.soondevjomer.schoolmanagementsystem.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/assessments")
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentService assessmentService;

    @PostMapping
    public ResponseEntity<AssessmentDto> addAssessment(@RequestBody AssessmentDto assessmentDto) {

        return new ResponseEntity<>(assessmentService.addAssessment(assessmentDto), HttpStatus.CREATED);
    }

    @GetMapping("/{assessmentId}")
    public ResponseEntity<AssessmentDto> getAssessment(@PathVariable Integer assessmentId) {

        return ResponseEntity.ok(assessmentService.getAssessment(assessmentId));
    }

    @GetMapping
    public ResponseEntity<Page<AssessmentDto>> getAssessments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {

        return ResponseEntity.ok(assessmentService.getAssessments(page, size, sortField, sortOrder));
    }

    @PutMapping("/{assessmentId}")
    public ResponseEntity<AssessmentDto> updateAssessment(
        @PathVariable Integer assessmentId,
        @RequestBody AssessmentDto assessmentDto) {

        return ResponseEntity.ok(assessmentService.updateAssessment(assessmentId, assessmentDto));
    }

    @DeleteMapping("/{assessmentId}")
    public ResponseEntity<String> deleteAssessment(@PathVariable Integer assessmentId) {

        return ResponseEntity.ok(assessmentService.deleteAssessment(assessmentId));
    }
}
