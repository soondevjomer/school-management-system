package com.soondevjomer.schoolmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @JsonIgnoreProperties(value = {"studentDto", "teacherDto", "adminDto"})
    private PersonDto personDto;

    @JsonIgnoreProperties("studentDtos")
    private ClassSectionDto classSectionDto;
}
