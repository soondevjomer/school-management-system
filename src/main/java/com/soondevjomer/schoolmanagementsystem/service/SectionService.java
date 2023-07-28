package com.soondevjomer.schoolmanagementsystem.service;

import com.soondevjomer.schoolmanagementsystem.dto.SectionDto;

import java.util.List;

public interface SectionService {

    SectionDto addSection(SectionDto sectionDto);

    SectionDto getSection(Integer sectionId);

    List<SectionDto> getSections();

    SectionDto updateSection(Integer sectionId, SectionDto sectionDto);

    String deleteSection(Integer sectionId);
}
