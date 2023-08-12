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
@Table(name = "book_availability_statuses")
public class BookAvailabilityStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_availability_status_id", unique = true, nullable = false)
    private Integer id;

    private String code;
    private String name;
    private String description;

    // RELATIONSHIPS
    @OneToMany(mappedBy = "bookAvailabilityStatus")
    private List<BookCopy> bookCopies;
}
