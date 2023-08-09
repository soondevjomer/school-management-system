package com.soondevjomer.schoolmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDto {

    private Integer id;

    @JsonIgnoreProperties(value = {"studentDto", "teacherDto", "adminDto"})
    private PersonDto personDto;

    @JsonIgnoreProperties("teacherDto")
    private List<ScheduleDto> scheduleDtos;
}
