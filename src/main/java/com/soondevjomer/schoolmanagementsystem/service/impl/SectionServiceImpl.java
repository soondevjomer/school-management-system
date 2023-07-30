package com.soondevjomer.schoolmanagementsystem.service.impl;

import com.soondevjomer.schoolmanagementsystem.dto.SectionDto;
import com.soondevjomer.schoolmanagementsystem.entity.Section;
import com.soondevjomer.schoolmanagementsystem.exception.NoRecordFoundException;
import com.soondevjomer.schoolmanagementsystem.repository.SectionRepository;
import com.soondevjomer.schoolmanagementsystem.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;
    private final ModelMapper modelMapper;

    @Override
    public SectionDto addSection(SectionDto sectionDto) {

        Section savedSection = sectionRepository.save(modelMapper.map(sectionDto, Section.class));

        return modelMapper.map(savedSection, SectionDto.class);
    }

    @Override
    public SectionDto getSection(Integer sectionId) {

        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(()->new NoRecordFoundException("Section", "id", sectionId.toString()));

        return modelMapper.map(section, SectionDto.class);
    }

    @Override
    public List<SectionDto> getSections() {

        return sectionRepository.findAll().stream()
                .map(section -> modelMapper.map(section, SectionDto.class))
                .toList();
    }

    @Override
    public SectionDto updateSection(Integer sectionId, SectionDto sectionDto) {

        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(()->new NoRecordFoundException("Section", "id", sectionId.toString()));

        section.setName(sectionDto.getName());
        Section updatedSection = sectionRepository.save(section);

        return modelMapper.map(updatedSection, SectionDto.class);
    }

    @Transactional
    @Override
    public String deleteSection(Integer sectionId) {

        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(()->new NoRecordFoundException("Section", "id", sectionId.toString()));

        sectionRepository.delete(section);

        return "Section deleted successfully.";
    }
}
