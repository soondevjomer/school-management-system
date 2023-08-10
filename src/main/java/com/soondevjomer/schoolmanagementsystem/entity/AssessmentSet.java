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
@Table(name = "assessment_sets")
public class AssessmentSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assessment_set_id", unique = true, nullable = false)
    private Integer id;

    private String name;
    private String instruction;

    // RELATIONSHIPS
    @OneToMany(mappedBy = "assessmentSet", cascade = CascadeType.ALL)
    private List<Question> questions;

    @ManyToOne
    @JoinColumn(name = "assessment_format_id", referencedColumnName = "assessment_format_id")
    private AssessmentFormat assessmentFormat;

    @ManyToOne
    @JoinColumn(name = "assessment_id", referencedColumnName = "assessment_id")
    private Assessment assessment;
}
