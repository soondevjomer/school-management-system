package com.soondevjomer.schoolmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NameDto {

    private Integer id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String extensionName;

    private PersonDto personDto;
}
