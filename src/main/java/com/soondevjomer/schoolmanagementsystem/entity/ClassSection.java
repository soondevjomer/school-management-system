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
@Table(name = "class_sections")
public class ClassSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_section_id", unique = true, nullable = false)
    private Integer id;

    // RELATIONSHIPS
    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "class_id")
    private Class_ class_;
    @ManyToOne
    @JoinColumn(name = "section_id", referencedColumnName = "section_id")
    private Section section;
    @OneToMany(mappedBy = "classSection")
    private List<Student> students;
}
