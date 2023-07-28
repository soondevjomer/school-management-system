package com.soondevjomer.schoolmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.soondevjomer.schoolmanagementsystem.entity.Address;
import com.soondevjomer.schoolmanagementsystem.entity.Contact;
import com.soondevjomer.schoolmanagementsystem.entity.Name;
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
    private NameDto nameDto;
    private AddressDto addressDto;
    private ContactDto contactDto;
}
