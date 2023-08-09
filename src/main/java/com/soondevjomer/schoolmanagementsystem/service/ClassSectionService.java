package com.soondevjomer.schoolmanagementsystem.service;

import com.soondevjomer.schoolmanagementsystem.dto.ClassSectionDto;
import com.soondevjomer.schoolmanagementsystem.dto.TeacherDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClassSectionService {
    ClassSectionDto addClassSection(ClassSectionDto classSectionDto);

    ClassSectionDto getClassSection(Integer classSectionId);

    Page<ClassSectionDto> getClassSections(Integer page, Integer size, String sortField, String sortOrder);

    String deleteClassSection(Integer classSectionId);
}
