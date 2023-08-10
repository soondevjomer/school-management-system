package com.soondevjomer.schoolmanagementsystem.service;

import com.soondevjomer.schoolmanagementsystem.dto.AssessmentDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AssessmentService {

    AssessmentDto addAssessment(AssessmentDto assessmentDto);

    AssessmentDto getAssessment(Integer assessmentId);

    Page<AssessmentDto> getAssessments(Integer page, Integer size, String sortField, String sortOrder);

    AssessmentDto updateAssessment(Integer assessmentId, AssessmentDto assessmentDto);

    String deleteAssessment(Integer assessmentId);
}
