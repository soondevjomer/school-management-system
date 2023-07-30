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
        if (studentDto.getClassSectionDto()!=null) {
            ClassSection classSection = new ClassSection();
            if (studentDto.getClassSectionDto().getClassDto()!=null) {
                Class_ class_ = classRepository.findById(studentDto.getClassSectionDto().getClassDto().getId())
                        .orElseThrow(()-> new NoRecordFoundException
                                ("Class", "id", studentDto.getClassSectionDto().getClassDto().getId().toString()));
                classSection.setClass_(class_);
            }
            if (studentDto.getClassSectionDto().getSectionDto()!=null) {
                Section section = sectionRepository.findById(studentDto.getClassSectionDto().getSectionDto().getId())
                        .orElseThrow(()-> new NoRecordFoundException
                                ("Section", "id", studentDto.getClassSectionDto().getSectionDto().getId().toString()));
                classSection.setSection(section);
            }
            student.setClassSection(classSection);
        }

        Student savedStudent = studentRepository.save(student);

        return createStudentDtoResponse(savedStudent);
    }

    @Override
    public StudentDto getStudent(Integer studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(()->new NoRecordFoundException("student", "id", studentId.toString()));

        return createStudentDtoResponse(student);
    }

    @Override
    public Page<StudentDto> getStudents(Integer page, Integer size, String sortField, String sortOrder) {

        Sort.Direction direction = Sort.Direction.ASC;
        if (sortOrder.equalsIgnoreCase("desc"))
            direction = Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        return studentRepository.findAll(pageable)
                .map(this::createStudentDtoResponse);
    }

    @Transactional
    @Override
    public StudentDto updateStudent(Integer studentId, StudentDto studentDto) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(()->new NoRecordFoundException("student", "id", studentId.toString()));

        student.getPerson().setName(modelMapper.map(studentDto.getPersonDto().getNameDto(), Name.class));
        student.getPerson().setAddress(modelMapper.map(studentDto.getPersonDto().getAddressDto(), Address.class));
        student.getPerson().setContact(modelMapper.map(studentDto.getPersonDto().getContactDto(), Contact.class));

        if (
                !studentDto.getClassSectionDto().getClassDto().getId()
                        .equals(student.getClassSection().getClass_().getId()) ||
                !studentDto.getClassSectionDto().getSectionDto().getId()
                        .equals(student.getClassSection().getSection().getId())
        ) {
            // There is changes in student class and section
            ClassSection classSection = new ClassSection();
            Class_ class_ = classRepository.findById(studentDto.getClassSectionDto().getClassDto().getId())
                    .orElseThrow(()->new NoRecordFoundException("Class", "id", studentDto.getClassSectionDto().getClassDto().getId().toString()));
            classSection.setClass_(class_);

            Section section = sectionRepository.findById(studentDto.getClassSectionDto().getSectionDto().getId())
                    .orElseThrow(()->new NoRecordFoundException("Section", "id", studentDto.getClassSectionDto().getSectionDto().getId().toString()));
            classSection.setSection(section);
            student.setClassSection(classSection);
        }

        Student updatedStudent = studentRepository.save(student);

        return createStudentDtoResponse(updatedStudent);
    }

    @Transactional
    @Override
    public String deleteStudent(Integer studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(()->new NoRecordFoundException("student", "id", studentId.toString()));

        studentRepository.delete(student);

        return "Student deleted successfully.";
    }

    private StudentDto createStudentDtoResponse(Student student) {
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
}
