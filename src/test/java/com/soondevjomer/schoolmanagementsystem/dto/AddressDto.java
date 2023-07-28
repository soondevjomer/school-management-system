package com.soondevjomer.schoolmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private Integer id;
    private String line;
    private String street;
    private String city;
    private String state;
    private String zipcode;
}
