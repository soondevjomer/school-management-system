package com.soondevjomer.schoolmanagementsystem.dto;

import com.soondevjomer.schoolmanagementsystem.entity.Person;
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

    private Person person;
}
