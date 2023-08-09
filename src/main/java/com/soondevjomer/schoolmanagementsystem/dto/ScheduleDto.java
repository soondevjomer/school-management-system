package com.soondevjomer.schoolmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {

    private Integer id;

    @JsonIgnoreProperties("scheduleDtos")
    private ClassSectionDto classSectionDto;

    @JsonIgnoreProperties("scheduleDtos")
    private TeacherDto teacherDto;

    private DayOfWeek dayOfWeek;

    private LocalTime timeIn;

    private LocalTime timeOut;
}
