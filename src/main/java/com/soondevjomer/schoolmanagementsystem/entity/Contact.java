package com.soondevjomer.schoolmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id", unique = true, nullable = false)
    private Integer id;

    private String mobileNumber;

    @Column(name = "email_address")
    private String email;

    // RELATIONSHIPS
    @OneToOne(mappedBy = "contact")
    private Person person;
}
