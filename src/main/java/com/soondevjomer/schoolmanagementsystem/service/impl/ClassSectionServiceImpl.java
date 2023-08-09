package com.soondevjomer.schoolmanagementsystem.service.impl;

import com.soondevjomer.schoolmanagementsystem.dto.ClassDto;
import com.soondevjomer.schoolmanagementsystem.dto.ClassSectionDto;
import com.soondevjomer.schoolmanagementsystem.dto.SectionDto;
import com.soondevjomer.schoolmanagementsystem.dto.StudentDto;
import com.soondevjomer.schoolmanagementsystem.entity.ClassSection;
import com.soondevjomer.schoolmanagementsystem.entity.Class_;
import com.soondevjomer.schoolmanagementsystem.entity.Section;
import com.soondevjomer.schoolmanagementsystem.exception.AlreadyExistsException;
import com.soondevjomer.schoolmanagementsystem.exception.NoRecordFoundException;
import com.soondevjomer.schoolmanagementsystem.repository.ClassRepository;
import com.soondevjomer.schoolmanagementsystem.repository.ClassSectionRepository;
import com.soondevjomer.schoolmanagementsystem.repository.SectionRepository;
import com.soondevjomer.schoolmanagementsystem.service.ClassSectionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassSectionServiceImpl implements ClassSectionService {

    private final ClassSectionRepository classSectionRepository;
    private final ClassRepository classRepository;
    private final SectionRepository sectionRepository;
    private final ModelMapper modelMapper;

    @Override
    public ClassSectionDto addClassSection(ClassSectionDto classSectionDto) {

        Optional<ClassSection> classSection = classSectionRepository.findAll().stream()
                .filter(tempClassSection ->
                        tempClassSection.getClass_().getId().equals(classSectionDto.getClassDto().getId()) &&
                        tempClassSection.getSection().getId().equals(classSectionDto.getSectionDto().getId())
                        )
                .findFirst();

        if (classSection.isPresent()) {
            throw new AlreadyExistsException(
                    "Class-Section", "name", classSection.get().getClass_().getName() + " - " + classSection.get().getSection().getName());
        }

        ClassSection saveClassSection = createClassSection(classSectionDto.getClassDto().getId(), classSectionDto.getSectionDto().getId());

        return createClassSectionDtoResponse(saveClassSection);
    }

    @Override
    public ClassSectionDto getClassSection(Integer classSectionId) {

        ClassSection classSection = classSectionRepository.findById(classSectionId)
                .orElseThrow(()->new NoRecordFoundException("Class-Section", "id", classSectionId.toString()));

        return createClassSectionDtoResponse(classSection);
    }

    @Override
    public Page<ClassSectionDto> getClassSections(Integer page, Integer size, String sortField, String sortOrder) {

        Sort.Direction direction = Sort.Direction.ASC;
        if (sortOrder.equalsIgnoreCase("desc"))
            direction = Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        return classSectionRepository.findAll(pageable)
                .map(this::createClassSectionDtoResponse);
    }

    @Transactional
    @Override
    public String deleteClassSection(Integer classSectionId) {

        ClassSection classSection = classSectionRepository.findById(classSectionId)
                .orElseThrow(()->new NoRecordFoundException("Class-Section", "id", classSectionId.toString()));

        classSection.setClass_(null);
        classSection.setSection(null);
        classSection.getStudents().forEach(student -> {
            if (student.getClassSection().getId().equals(classSectionId))
                student.setClassSection(null);
        });
        classSectionRepository.delete(classSectionRepository.save(classSection));

        return "Class-Section delete successfully.";
    }

    private ClassSection createClassSection(Integer classId, Integer sectionId) {

        Class_ class_ = classRepository.findById(classId)
                .orElseThrow(()->new NoRecordFoundException("Class", "id", classId.toString()));

        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(()->new NoRecordFoundException("Section", "id", sectionId.toString()));

        ClassSection classSection = new ClassSection();
        classSection.setClass_(class_);
        classSection.setSection(section);

        return classSectionRepository.save(classSection);
    }

    private ClassSectionDto createClassSectionDtoResponse(ClassSection classSection) {

        ClassSectionDto classSectionDto = new ClassSectionDto();
        classSectionDto.setId(classSection.getId());
        classSectionDto.setClassDto(modelMapper.map(classSection.getClass_(), ClassDto.class));
        classSectionDto.setSectionDto(modelMapper.map(classSection.getSection(), SectionDto.class));

        if (classSection.getStudents()!=null)
            classSectionDto.setStudentDtos(classSection.getStudents().stream()
                    .map(student -> modelMapper.map(student, StudentDto.class))
                    .toList());

        return classSectionDto;
    }
}
