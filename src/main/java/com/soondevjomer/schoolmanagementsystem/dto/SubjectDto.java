package com.soondevjomer.schoolmanagementsystem.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDto {

    private Integer id;

    @NotNull
    private String code;

    @NotNull
    private String name;

    private String description;
}
