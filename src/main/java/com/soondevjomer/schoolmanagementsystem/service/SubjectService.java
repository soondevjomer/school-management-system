package com.soondevjomer.schoolmanagementsystem.service;

import com.soondevjomer.schoolmanagementsystem.dto.SubjectDto;
import org.springframework.data.domain.Page;

public interface SubjectService {

    SubjectDto addSubject(SubjectDto subjectDto);

    SubjectDto getSubject(Integer subjectId);

    Page<SubjectDto> getSubjects(Integer page, Integer size, String sortField, String sortOrder);

    SubjectDto updateSubject(Integer subjectId, SubjectDto subjectDto);

    String deleteSubject(Integer subjectId);
}
