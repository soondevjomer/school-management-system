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
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", unique = true, nullable = false)
    private Integer id;

    private String question;

    // RELATIONSHIPS
    @ManyToOne
    @JoinColumn(name = "question_type_id", referencedColumnName = "question_type_id")
    private QuestionType questionType;

    @ManyToOne
    @JoinColumn(name = "assessment_set_id", referencedColumnName = "assessment_set_id")
    private AssessmentSet assessmentSet;

    private Integer point;

    @OneToOne
    @JoinColumn(name = "answer_id", referencedColumnName = "answer_id")
    private Answer answer;
}
