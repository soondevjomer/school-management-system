package com.soondevjomer.schoolmanagementsystem.service.impl;

import com.soondevjomer.schoolmanagementsystem.dto.ClassDto;
import com.soondevjomer.schoolmanagementsystem.entity.Class_;
import com.soondevjomer.schoolmanagementsystem.exception.NoRecordFoundException;
import com.soondevjomer.schoolmanagementsystem.repository.ClassRepository;
import com.soondevjomer.schoolmanagementsystem.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;
    private final ModelMapper modelMapper;

    @Override
    public ClassDto addClass(ClassDto classDto) {

        Class_ savedClass = classRepository.save(modelMapper.map(classDto, Class_.class));

        return modelMapper.map(savedClass, ClassDto.class);
    }

    @Override
    public ClassDto getClass(Integer classId) {

        Class_ class_ = classRepository.findById(classId)
                .orElseThrow(()->new NoRecordFoundException("Class", "id", classId.toString()));

        return modelMapper.map(class_, ClassDto.class);
    }

    @Override
    public List<ClassDto> getClasses() {

        return classRepository.findAll().stream()
                .map(class_ -> modelMapper.map(class_, ClassDto.class))
                .toList();
    }

    @Override
    public ClassDto updateClass(Integer classId, ClassDto classDto) {

        Class_ class_ = classRepository.findById(classId)
                .orElseThrow(()->new NoRecordFoundException("Class", "id", classId.toString()));

        class_.setName(classDto.getName());
        class_.setDescription(classDto.getDescription());
        Class_ updatedClass = classRepository.save(class_);

        return modelMapper.map(updatedClass, ClassDto.class);
    }

    @Transactional
    @Override
    public String deleteClass(Integer classId) {

        return "Class deleted successfully.";
    }
}
