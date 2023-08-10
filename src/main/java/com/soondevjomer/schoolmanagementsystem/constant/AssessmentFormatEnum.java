package com.soondevjomer.schoolmanagementsystem.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AssessmentFormatEnum {
    MC("Multiple Choice", "Select one correct option from multiple choices."),
    FIB("Fill in the Blanks", "Complete the sentence by filling in the missing word(s)."),
    MT("Matching Type", "Match items from other columns."),
    ESS("Essay", "Write a detailed response to a question."),
    CIB("Choose in the Box", "Choose an answer from the box.");

    private final String fullName;
    private final String description;
}
