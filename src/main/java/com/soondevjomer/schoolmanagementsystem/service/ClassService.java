package com.soondevjomer.schoolmanagementsystem.service;

import com.soondevjomer.schoolmanagementsystem.dto.ClassDto;
import com.soondevjomer.schoolmanagementsystem.entity.Class_;

import java.util.List;

public interface ClassService {

    ClassDto addClass(ClassDto classDto);

    ClassDto getClass(Integer classId);

    List<ClassDto> getClasses();

    ClassDto updateClass(Integer classId, ClassDto classDto);

    String deleteClass(Integer classId);
}
