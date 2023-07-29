package com.soondevjomer.schoolmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.soondevjomer.schoolmanagementsystem.entity.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {

    private Integer id;

    @JsonIgnoreProperties("personDto")
    private NameDto nameDto;

    @JsonIgnoreProperties("personDto")
    private AddressDto addressDto;

    @JsonIgnoreProperties("personDto")
    private ContactDto contactDto;

    private StudentDto studentDto;

    private TeacherDto teacherDto;

    private AdminDto adminDto;
}
