package com.soondevjomer.schoolmanagementsystem.data;

import com.soondevjomer.schoolmanagementsystem.constant.AssessmentFormatEnum;
import com.soondevjomer.schoolmanagementsystem.entity.AssessmentFormat;
import com.soondevjomer.schoolmanagementsystem.repository.AssessmentFormatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
@RequiredArgsConstructor
public class AssessmentFormatData implements CommandLineRunner {

    private final AssessmentFormatRepository assessmentFormatRepository;

    @Override
    public void run(String... args) {

        if (assessmentFormatRepository.count()!=0) {
            return;
        }

        AssessmentFormat assessmentFormat = new AssessmentFormat();

        assessmentFormat.setCode(AssessmentFormatEnum.MC.name());
        assessmentFormat.setName(AssessmentFormatEnum.MC.getFullName());
        assessmentFormat.setDescription(AssessmentFormatEnum.MC.getDescription());
        assessmentFormatRepository.save(assessmentFormat);

        assessmentFormat.setCode(AssessmentFormatEnum.FIB.name());
        assessmentFormat.setName(AssessmentFormatEnum.FIB.getFullName());
        assessmentFormat.setDescription(AssessmentFormatEnum.FIB.getDescription());
        assessmentFormatRepository.save(assessmentFormat);

        assessmentFormat.setCode(AssessmentFormatEnum.MT.name());
        assessmentFormat.setName(AssessmentFormatEnum.MT.getFullName());
        assessmentFormat.setDescription(AssessmentFormatEnum.MT.getDescription());
        assessmentFormatRepository.save(assessmentFormat);

        assessmentFormat.setCode(AssessmentFormatEnum.ESS.name());
        assessmentFormat.setName(AssessmentFormatEnum.ESS.getFullName());
        assessmentFormat.setDescription(AssessmentFormatEnum.ESS.getDescription());
        assessmentFormatRepository.save(assessmentFormat);

        assessmentFormat.setCode(AssessmentFormatEnum.CIB.name());
        assessmentFormat.setName(AssessmentFormatEnum.CIB.getFullName());
        assessmentFormat.setDescription(AssessmentFormatEnum.CIB.getDescription());
        assessmentFormatRepository.save(assessmentFormat);
    }
}
