package com.soondevjomer.schoolmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "assessments")
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assessment_id", unique = true, nullable = false)
    private Integer id;

    private String name;

    // RELATIONSHIPS
    @ManyToOne
    @JoinColumn(name = "assessment_type_id", referencedColumnName = "assessment_type_id")
    private AssessmentType assessmentType;

    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL)
    private List<AssessmentSet> assessmentSets;

    @CreationTimestamp
    private Date dateCreated;
}
