package com.soondevjomer.schoolmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", unique = true, nullable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "name_id", referencedColumnName = "name_id")
    private Name name;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address;

    @OneToOne
    @JoinColumn(name = "contact_id",referencedColumnName = "contact_id")
    private Contact contact;
}
