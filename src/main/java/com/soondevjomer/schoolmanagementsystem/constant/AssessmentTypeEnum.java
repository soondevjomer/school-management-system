package com.soondevjomer.schoolmanagementsystem.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AssessmentTypeEnum {

    EXAM("Exam", "Regular examination"),
    LONG_QUIZ("Long Quiz", "Extended quiz with detailed questions"),
    SHORT_QUIZ("Short Quiz", "Brief quiz with concise questions"),
    FINAL_EXAM("Final Exam", "End-of-term or end-of-course examination");

    private final String fullName;
    private final String description;
}
