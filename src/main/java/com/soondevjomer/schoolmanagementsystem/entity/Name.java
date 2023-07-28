package com.soondevjomer.schoolmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "names")
public class Name {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "name_id", unique = true, nullable = false)
    private Integer id;

    private String firstName;
    private String middleName;
    private String lastName;
    private String extensionName;

    // RELATIONSHIPS
    @OneToOne(mappedBy = "name")
    private Person person;
}
