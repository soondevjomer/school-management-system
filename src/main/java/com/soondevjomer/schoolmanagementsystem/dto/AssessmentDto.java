package com.soondevjomer.schoolmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentDto {

    private Integer id;

    private String name;

    private Date dateCreated;

    private AssessmentTypeDto assessmentTypeDto;

    private List<AssessmentSetDto> assessmentSetDtos;
}
