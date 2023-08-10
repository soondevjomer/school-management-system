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
@Table(name = "assessment_types")
public class AssessmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assessment_type_id", unique = true, nullable = false)
    private Integer id;

    private String name;

    private String description;

    // RELATIONSHIPS
    @OneToMany(mappedBy = "assessmentType")
    private List<Assessment> assessments;
}
