package com.soondevjomer.schoolmanagementsystem.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.soondevjomer.schoolmanagementsystem.entity.Class_;
import com.soondevjomer.schoolmanagementsystem.entity.Section;
import com.soondevjomer.schoolmanagementsystem.entity.Student;
import com.soondevjomer.schoolmanagementsystem.entity.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassSectionDto {

    private Integer id;
    private ClassDto classDto;
    private SectionDto sectionDto;

    @JsonIgnoreProperties("classSectionDto")
    private List<StudentDto> studentDtos;

    @JsonIgnoreProperties("classSectionDto")
    private List<ScheduleDto> scheduleDtos;
}
