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
@Table(name = "book_copies")
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_copy_id", unique = true, nullable = false)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String uuid;

    // RELATIONSHIPS
    @ManyToOne
    @JoinColumn(name = "book_physical_status_id", referencedColumnName = "book_physical_status_id")
    private BookPhysicalStatus bookPhysicalStatus;

    @ManyToOne
    @JoinColumn(name = "book_availability_status_id", referencedColumnName = "book_availability_status_id")
    private BookAvailabilityStatus bookAvailabilityStatus;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "book_id")
    private Book book;
}
