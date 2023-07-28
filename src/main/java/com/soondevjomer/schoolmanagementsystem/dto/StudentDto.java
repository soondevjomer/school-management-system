package com.soondevjomer.schoolmanagementsystem.dto;

import com.soondevjomer.schoolmanagementsystem.entity.Class_;
import com.soondevjomer.schoolmanagementsystem.entity.Person;
import com.soondevjomer.schoolmanagementsystem.entity.Section;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {


    private Integer id;
    private PersonDto personDto;
    private ClassDto classDto;
    private SectionDto sectionDto;
}
