package com.soondevjomer.schoolmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {

    private Integer id;

    private String question;

    private QuestionTypeDto questionTypeDto;

    private Integer point;

    private AnswerDto answerDto;
}
