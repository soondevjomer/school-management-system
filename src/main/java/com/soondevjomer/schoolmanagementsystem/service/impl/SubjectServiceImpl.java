package com.soondevjomer.schoolmanagementsystem.service.impl;

import com.soondevjomer.schoolmanagementsystem.dto.SubjectDto;
import com.soondevjomer.schoolmanagementsystem.entity.Subject;
import com.soondevjomer.schoolmanagementsystem.exception.AlreadyExistsException;
import com.soondevjomer.schoolmanagementsystem.exception.NoRecordFoundException;
import com.soondevjomer.schoolmanagementsystem.repository.SubjectRepository;
import com.soondevjomer.schoolmanagementsystem.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final ModelMapper modelMapper;
    @Override
    public SubjectDto addSubject(SubjectDto subjectDto) {

        // First find if subject code is already exists
        if (subjectRepository.findByCode(subjectDto.getCode()).isPresent())
            throw new AlreadyExistsException("Subject", "code", subjectDto.getCode());

        Subject savedSubject = subjectRepository.save(modelMapper.map(subjectDto, Subject.class));

        return modelMapper.map(savedSubject, SubjectDto.class);
    }

    @Override
    public SubjectDto getSubject(Integer subjectId) {

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(()->new NoRecordFoundException("Subject", "id", subjectId.toString()));

        return modelMapper.map(subject, SubjectDto.class);
    }

    @Override
    public Page<SubjectDto> getSubjects(Integer page, Integer size, String sortField, String sortOrder) {

        Sort.Direction direction = Sort.Direction.ASC;
        if (sortOrder.equalsIgnoreCase("desc"))
            direction = Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        return subjectRepository.findAll(pageable)
                .map(subject -> modelMapper.map(subject, SubjectDto.class));
    }

    @Override
    public SubjectDto updateSubject(Integer subjectId, SubjectDto subjectDto) {

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(()->new NoRecordFoundException("Subject", "id", subjectId.toString()));

        if (!subjectDto.getCode().equals(subject.getCode()))
            if (subjectRepository.findByCode(subjectDto.getCode()).isPresent())
                throw new AlreadyExistsException("Subject", "code", subjectDto.getCode());

        subject.setCode(subjectDto.getCode());
        subject.setName(subjectDto.getName());
        subject.setDescription(subjectDto.getDescription());
        Subject savedSubject = subjectRepository.save(subject);

        return modelMapper.map(savedSubject, SubjectDto.class);
    }

    @Override
    public String deleteSubject(Integer subjectId) {

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(()->new NoRecordFoundException("Subject", "id", subjectId.toString()));

        subjectRepository.delete(subject);

        return "Subject deleted successfully.";
    }
}
