package com.soondevjomer.schoolmanagementsystem.service.impl;

import com.soondevjomer.schoolmanagementsystem.dto.*;
import com.soondevjomer.schoolmanagementsystem.entity.*;
import com.soondevjomer.schoolmanagementsystem.exception.NoRecordFoundException;
import com.soondevjomer.schoolmanagementsystem.repository.*;
import com.soondevjomer.schoolmanagementsystem.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AssessmentServiceImpl implements AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final AssessmentTypeRepository assessmentTypeRepository;
    private final AssessmentSetRepository assessmentSetRepository;
    private final AssessmentFormatRepository assessmentFormatRepository;
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public AssessmentDto addAssessment(AssessmentDto assessmentDto) {

        Assessment assessment = new Assessment();
        assessment.setName(assessmentDto.getName());

        if (assessmentDto.getAssessmentTypeDto()!=null) {
            AssessmentType assessmentType = assessmentTypeRepository.findById(assessmentDto.getAssessmentTypeDto().getId())
                    .orElseThrow(()->new NoRecordFoundException("Assessment Type", "id", assessmentDto.getAssessmentTypeDto().getId().toString()));
            assessment.setAssessmentType(assessmentType);
        }

        if (assessmentDto.getAssessmentSetDtos()!=null) {
            List<AssessmentSet> assessmentSets = assessmentDto.getAssessmentSetDtos().stream()
                    .map(assessmentSetDto -> {
                        AssessmentSet assessmentSet = new AssessmentSet();
                        assessmentSet.setName(assessmentSetDto.getName());
                        assessmentSet.setInstruction(assessmentSetDto.getInstruction());

                        AssessmentFormat assessmentFormat = assessmentFormatRepository.findById(assessmentSetDto.getAssessmentFormatDto().getId())
                                .orElseThrow(()->new NoRecordFoundException("Assessment Format", "id", assessmentSetDto.getAssessmentFormatDto().getId().toString()));
                        assessmentSet.setAssessmentFormat(assessmentFormat);

                        if (assessmentSetDto.getQuestionDtos()!=null) {

                            List<Question> questions = assessmentSetDto.getQuestionDtos().stream()
                                    .map(this::createQuestion)
                                    .toList();
                            assessmentSet.setQuestions(questions);
                        }

                        return assessmentSetRepository.save(assessmentSet);
                    })
                    .toList();
            assessment.setAssessmentSets(assessmentSets);
        }

        Assessment savedAssessment = assessmentRepository.save(assessment);

        return createAssessmentDtoResponse(savedAssessment);
    }

    @Override
    public AssessmentDto getAssessment(Integer assessmentId) {

        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(()->new NoRecordFoundException("Assessment", "id", assessmentId.toString()));

        return createAssessmentDtoResponse(assessment);
    }

    @Override
    public Page<AssessmentDto> getAssessments(Integer page, Integer size, String sortField, String sortOrder) {

        Sort.Direction direction = Sort.Direction.ASC;
        if (sortOrder.equalsIgnoreCase("desc"))
            direction = Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        return assessmentRepository.findAll(pageable)
                .map(this::createAssessmentDtoResponse);
    }

    @Transactional
    @Override
    public AssessmentDto updateAssessment(Integer assessmentId, AssessmentDto assessmentDto) {

        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(()->new NoRecordFoundException("Assessment", "id", assessmentId.toString()));

        assessment.setName(assessmentDto.getName());

        if (!Objects.equals(assessment.getAssessmentType().getId(), assessmentDto.getAssessmentTypeDto().getId())) {
            AssessmentType assessmentType = assessmentTypeRepository.findById(assessmentDto.getAssessmentTypeDto().getId())
                    .orElseThrow(()->new NoRecordFoundException("Assessment Type", "id", assessmentDto.getAssessmentTypeDto().getId().toString()));
            assessment.setAssessmentType(assessmentType);
        }

        if (assessmentDto.getAssessmentSetDtos()!=null) {

            List<AssessmentSet> assessmentSets = assessmentDto.getAssessmentSetDtos().stream()
                    .map(assessmentSetDto -> {

                        AssessmentSet assessmentSet = assessmentSetRepository.findById(assessmentSetDto.getId())
                                .orElseThrow(()->new NoRecordFoundException("Assessment Set", "id", assessmentSetDto.getId().toString()));
                        assessmentSet.setName(assessmentSetDto.getName());
                        assessmentSet.setInstruction(assessmentSet.getInstruction());

                        if (assessmentSetDto.getAssessmentFormatDto().getId()!=null) {
                            AssessmentFormat assessmentFormat = assessmentFormatRepository.findById(assessmentSetDto.getAssessmentFormatDto().getId())
                                    .orElseThrow(()->new NoRecordFoundException("Assessment Format", "id", assessmentSetDto.getAssessmentFormatDto().getId().toString()));
                            assessmentSet.setAssessmentFormat(assessmentFormat);
                        }

                        if (assessmentSetDto.getQuestionDtos()!=null) {

                            List<Question> questions = assessmentSetDto.getQuestionDtos().stream()
                                    .map(questionDto -> questionRepository.findById(questionDto.getId())
                                            .orElseGet(()-> {
                                                Question question = new Question();
                                                question.setQuestion(questionDto.getQuestion());
                                                question.setPoint(questionDto.getPoint());

                                                if (questionDto.getAnswerDtos()!=null) {
                                                    List<Answer> answers = questionDto.getAnswerDtos().stream()
                                                            .map(answerDto -> modelMapper.map(answerDto, Answer.class))
                                                            .toList();
                                                    question.setAnswers(answers);
                                                }
                                                return question;
                                            })).toList();
                            assessmentSet.setQuestions(questions);
                        }
                        return assessmentSet;
                    }).toList();
            assessment.setAssessmentSets(assessmentSets);
        } else {
            assessment.setAssessmentSets(null);
        }

        Assessment updatedAssessment = assessmentRepository.save(assessment);

        return createAssessmentDtoResponse(updatedAssessment);
    }

    @Transactional
    @Override
    public String deleteAssessment(Integer assessmentId) {

        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(()->new NoRecordFoundException("Assessment", "id", assessmentId.toString()));

        AssessmentType assessmentType = assessmentTypeRepository.findById(assessment.getAssessmentType().getId())
                .orElseThrow(()->new NoRecordFoundException("Assessment Type", "id", assessment.getAssessmentType().getId().toString()));

        assessmentType.getAssessments().remove(assessment);
        assessmentTypeRepository.save(assessmentType);

        assessmentRepository.delete(assessment);

        return "Assessment delete successfully.";
    }

    private Question createQuestion(QuestionDto questionDto) {

        Question question = new Question();
        question.setQuestion(questionDto.getQuestion());
        question.setPoint(questionDto.getPoint());

        List<Answer> answers = questionDto.getAnswerDtos().stream()
                .map(this::createAnswer)
                .toList();
        question.setAnswers(answers);

        return question;
    }

    private Answer createAnswer(AnswerDto answerDto) {

        Answer answer = new Answer();
        answer.setAnswer(answerDto.getAnswer());
        answer.setCorrect(answerDto.isCorrect());

        return answer;
    }

    private AssessmentDto createAssessmentDtoResponse(Assessment assessment) {

        AssessmentDto assessmentDto = new AssessmentDto();
        assessmentDto.setId(assessment.getId());
        assessmentDto.setName(assessment.getName());
        assessmentDto.setDateCreated(assessment.getDateCreated());

        if (assessment.getAssessmentType()!=null)
            assessmentDto.setAssessmentTypeDto(modelMapper.map(assessment.getAssessmentType(), AssessmentTypeDto.class));

        if (assessment.getAssessmentSets()!=null) {
            List<AssessmentSetDto> assessmentSetDtos = assessment.getAssessmentSets().stream()
                    .map(assessmentSet -> {
                        AssessmentSetDto assessmentSetDto = new AssessmentSetDto();
                        assessmentSetDto.setId(assessmentSet.getId());
                        assessmentSetDto.setName(assessmentSet.getName());
                        assessmentSetDto.setInstruction(assessmentSet.getInstruction());

                        if (assessmentSet.getQuestions()!=null) {
                            List<QuestionDto> questionDtos = assessmentSet.getQuestions().stream()
                                    .map(question -> {
                                        QuestionDto questionDto = new QuestionDto();
                                        questionDto.setId(question.getId());
                                        questionDto.setQuestion(question.getQuestion());
                                        questionDto.setPoint(question.getPoint());

                                        List<AnswerDto> answerDtos = question.getAnswers().stream()
                                                .map(answer -> modelMapper.map(answer, AnswerDto.class))
                                                .toList();
                                        questionDto.setAnswerDtos(answerDtos);
                                        return questionDto;
                                    })
                                    .toList();
                            assessmentSetDto.setQuestionDtos(questionDtos);
                        }

                        return assessmentSetDto;
                    })
                    .toList();
        }

        return assessmentDto;
    }
}
