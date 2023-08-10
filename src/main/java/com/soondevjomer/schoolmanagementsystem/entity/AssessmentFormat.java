package com.soondevjomer.schoolmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "assessment_formats")
public class AssessmentFormat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assessment_format_id", unique = true, nullable = false)
    private Integer id;

    private String code;

    private String name;

    private String description;

    // RELATIONSHIPS
    @OneToMany(mappedBy = "assessmentFormat")
    private List<AssessmentSet> assessmentSets;
}
