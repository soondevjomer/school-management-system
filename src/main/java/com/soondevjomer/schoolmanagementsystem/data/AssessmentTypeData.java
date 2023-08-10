package com.soondevjomer.schoolmanagementsystem.data;

import com.soondevjomer.schoolmanagementsystem.constant.AssessmentTypeEnum;
import com.soondevjomer.schoolmanagementsystem.entity.AssessmentType;
import com.soondevjomer.schoolmanagementsystem.repository.AssessmentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
@RequiredArgsConstructor
public class AssessmentTypeData implements CommandLineRunner {

    private final AssessmentTypeRepository assessmentTypeRepository;

    @Override
    public void run(String... args) {

        if (assessmentTypeRepository.count()!=0) {
            return;
        }

        AssessmentType assessmentType = new AssessmentType();

        assessmentType.setName(AssessmentTypeEnum.EXAM.getFullName());
        assessmentType.setDescription(AssessmentTypeEnum.EXAM.getDescription());
        assessmentTypeRepository.save(assessmentType);

        assessmentType.setName(AssessmentTypeEnum.LONG_QUIZ.getFullName());
        assessmentType.setDescription(AssessmentTypeEnum.LONG_QUIZ.getDescription());
        assessmentTypeRepository.save(assessmentType);

        assessmentType.setName(AssessmentTypeEnum.SHORT_QUIZ.getFullName());
        assessmentType.setDescription(AssessmentTypeEnum.SHORT_QUIZ.getDescription());
        assessmentTypeRepository.save(assessmentType);

        assessmentType.setName(AssessmentTypeEnum.FINAL_EXAM.getFullName());
        assessmentType.setDescription(AssessmentTypeEnum.FINAL_EXAM.getDescription());
        assessmentTypeRepository.save(assessmentType);
    }
}
