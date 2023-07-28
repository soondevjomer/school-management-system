package com.soondevjomer.schoolmanagementsystem.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {

    private Integer id;
    private String mobileNumber;
    private String email;
}
