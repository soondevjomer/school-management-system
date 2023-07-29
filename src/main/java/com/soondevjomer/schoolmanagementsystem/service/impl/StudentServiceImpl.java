package com.soondevjomer.schoolmanagementsystem.service.impl;

import com.soondevjomer.schoolmanagementsystem.dto.*;
import com.soondevjomer.schoolmanagementsystem.entity.*;
import com.soondevjomer.schoolmanagementsystem.exception.NoRecordFoundException;
import com.soondevjomer.schoolmanagementsystem.repository.*;
import com.soondevjomer.schoolmanagementsystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final PersonRepository personRepository;
    private final ClassRepository classRepository;
    private final SectionRepository sectionRepository;
    private final ClassSectionRepository classSectionRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public StudentDto addStudent(StudentDto studentDto) {

        Person person = new Person();
        person.setName(modelMapper.map(studentDto.getPersonDto().getNameDto(), Name.class));
        person.setAddress(modelMapper.map(studentDto.getPersonDto().getAddressDto(), Address.class));
        person.setContact(modelMapper.map(studentDto.getPersonDto().getContactDto(), Contact.class));
        Person savedPerson = personRepository.save(person);

        Student student = new Student();
        student.setPerson(savedPerson);

        // Now before saving the student check if there is class and section provided
        ClassSection classSection = new ClassSection();
        Class_ class_;
        Section section;

        if (studentDto.getClassSectionDto().getClassDto()!=null) {
            // Now before saving the student check class and section provided is exists.
            class_ = classRepository.findById(studentDto.getClassSectionDto().getClassDto().getId())
                    .orElseThrow(()->new NoRecordFoundException
                            ("Class", "id", studentDto.getClassSectionDto().getClassDto().getId().toString()));
            section = sectionRepository.findById(studentDto.getClassSectionDto().getSectionDto().getId())
                    .orElseThrow(()->new NoRecordFoundException
                            ("Section", "id", studentDto.getClassSectionDto().getSectionDto().getId().toString()));

            classSection = classSectionRepository.findAll().stream()
                    .findFirst()
                    .filter(classSection1 ->
                            classSection1.getClass_().getId().equals(class_.getId()) &&
                            classSection1.getSection().getId().equals(section.getId()))
                    .orElseGet(()->{
                        ClassSection elseClassSection = new ClassSection();
                        elseClassSection.setClass_(class_);
                        elseClassSection.setSection(section);
                        return classSectionRepository.save(elseClassSection);
                    });
        }

        student.setClassSection(classSection);

        Student savedStudent = studentRepository.save(student);

        PersonDto personDto = new PersonDto();
        personDto.setId(savedStudent.getPerson().getId());
        personDto.setNameDto(modelMapper.map(savedStudent.getPerson().getName(), NameDto.class));
        personDto.setAddressDto(modelMapper.map(savedStudent.getPerson().getAddress(), AddressDto.class));
        personDto.setContactDto(modelMapper.map(savedStudent.getPerson().getContact(), ContactDto.class));
        ClassSectionDto classSectionDto = new ClassSectionDto();
        classSectionDto.setId(savedStudent.getClassSection().getId());
        classSectionDto.setClassDto(modelMapper.map(savedStudent.getClassSection().getClass_(), ClassDto.class));
        classSectionDto.setSectionDto(modelMapper.map(savedStudent.getClassSection().getSection(), SectionDto.class));
        StudentDto studentDtoResponse = new StudentDto();
        studentDtoResponse.setId(savedStudent.getId());
        studentDtoResponse.setPersonDto(personDto);
        studentDtoResponse.setClassSectionDto(classSectionDto);

        return studentDtoResponse;
    }

    @Override
    public StudentDto getStudent(Integer studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(()->new NoRecordFoundException("student", "id", studentId.toString()));

        PersonDto personDto = new PersonDto();
        personDto.setId(student.getPerson().getId());
        personDto.setNameDto(modelMapper.map(student.getPerson().getName(), NameDto.class));
        personDto.setAddressDto(modelMapper.map(student.getPerson().getAddress(), AddressDto.class));
        personDto.setContactDto(modelMapper.map(student.getPerson().getContact(), ContactDto.class));
        ClassSectionDto classSectionDto = new ClassSectionDto();
        classSectionDto.setId(student.getClassSection().getId());
        classSectionDto.setClassDto(modelMapper.map(student.getClassSection().getClass_(), ClassDto.class));
        classSectionDto.setSectionDto(modelMapper.map(student.getClassSection().getSection(), SectionDto.class));
        StudentDto studentDtoResponse = new StudentDto();
        studentDtoResponse.setId(student.getId());
        studentDtoResponse.setPersonDto(personDto);
        studentDtoResponse.setClassSectionDto(classSectionDto);

        return studentDtoResponse;
    }

    @Override
    public Page<StudentDto> getStudents(Integer page, Integer size, String sortField, String sortOrder) {

        Sort.Direction direction = Sort.Direction.ASC;
        if (sortOrder.equalsIgnoreCase("desc"))
            direction = Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        return studentRepository.findAll(pageable)
                .map(student -> {
                    PersonDto personDto = new PersonDto();
                    personDto.setId(student.getPerson().getId());
                    personDto.setNameDto(modelMapper.map(student.getPerson().getName(), NameDto.class));
                    personDto.setAddressDto(modelMapper.map(student.getPerson().getAddress(), AddressDto.class));
                    personDto.setContactDto(modelMapper.map(student.getPerson().getContact(), ContactDto.class));
                    ClassSectionDto classSectionDto = new ClassSectionDto();
                    classSectionDto.setId(student.getClassSection().getId());
                    classSectionDto.setClassDto(modelMapper.map(student.getClassSection().getClass_(), ClassDto.class));
                    classSectionDto.setSectionDto(modelMapper.map(student.getClassSection().getSection(), SectionDto.class));
                    StudentDto studentDtoResponse = new StudentDto();
                    studentDtoResponse.setId(student.getId());
                    studentDtoResponse.setPersonDto(personDto);
                    studentDtoResponse.setClassSectionDto(classSectionDto);

                    return studentDtoResponse;
                });
    }

    @Transactional
    @Override
    public StudentDto updateStudent(Integer studentId, StudentDto studentDto) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(()->new NoRecordFoundException("student", "id", studentId.toString()));

        student.getPerson().setName(modelMapper.map(studentDto.getPersonDto().getNameDto(), Name.class));
        student.getPerson().setAddress(modelMapper.map(studentDto.getPersonDto().getAddressDto(), Address.class));
        student.getPerson().setContact(modelMapper.map(studentDto.getPersonDto().getContactDto(), Contact.class));

        // Now before updating the student check if student class and section change
        if (
                !student.getClassSection().getClass_().getId()
                        .equals(studentDto.getClassSectionDto().getClassDto().getId()) ||
                !student.getClassSection().getSection().getId()
                        .equals(studentDto.getClassSectionDto().getClassDto().getId())
        ) {
            // Now before updating the student check if the class and section provided is exists.
            ClassSection classSection;

            Class_ class_ = classRepository.findById(studentDto.getClassSectionDto().getClassDto().getId())
                    .orElseThrow(()->new NoRecordFoundException(
                            "Class", "id", studentDto.getClassSectionDto().getClassDto().getId().toString()));

            Section section = sectionRepository.findById(studentDto.getClassSectionDto().getSectionDto().getId())
                    .orElseThrow(()->new NoRecordFoundException(
                            "Section", "id", studentDto.getClassSectionDto().getSectionDto().getId().toString()));

            classSection = classSectionRepository.findAll().stream()
                    .findFirst()
                    .filter(classSection1 ->
                            classSection1.getClass_().getId().equals(class_.getId()) &&
                                    classSection1.getSection().getId().equals(section.getId()))
                    .orElseGet(()->{
                        ClassSection elseClassSection = new ClassSection();
                        elseClassSection.setClass_(class_);
                        elseClassSection.setSection(section);
                        return classSectionRepository.save(elseClassSection);
                    });
            student.setClassSection(classSection);
        }

        Student updatedStudent = studentRepository.save(student);

        PersonDto personDto = new PersonDto();
        personDto.setId(updatedStudent.getPerson().getId());
        personDto.setNameDto(modelMapper.map(updatedStudent.getPerson().getName(), NameDto.class));
        personDto.setAddressDto(modelMapper.map(updatedStudent.getPerson().getAddress(), AddressDto.class));
        personDto.setContactDto(modelMapper.map(updatedStudent.getPerson().getContact(), ContactDto.class));
        ClassSectionDto classSectionDto = new ClassSectionDto();
        classSectionDto.setId(updatedStudent.getClassSection().getId());
        classSectionDto.setClassDto(modelMapper.map(updatedStudent.getClassSection().getClass_(), ClassDto.class));
        classSectionDto.setSectionDto(modelMapper.map(updatedStudent.getClassSection().getSection(), SectionDto.class));
        StudentDto studentDtoResponse = new StudentDto();
        studentDtoResponse.setId(updatedStudent.getId());
        studentDtoResponse.setPersonDto(personDto);
        studentDtoResponse.setClassSectionDto(classSectionDto);

        return studentDtoResponse;
    }

    @Transactional
    @Override
    public String deleteStudent(Integer studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(()->new NoRecordFoundException("student", "id", studentId.toString()));

        studentRepository.delete(student);

        return "Student deleted successfully.";
    }
}
