package com.soondevjomer.schoolmanagementsystem.service.impl;

import com.soondevjomer.schoolmanagementsystem.dto.*;
import com.soondevjomer.schoolmanagementsystem.entity.*;
import com.soondevjomer.schoolmanagementsystem.exception.NoRecordFoundException;
import com.soondevjomer.schoolmanagementsystem.repository.ClassRepository;
import com.soondevjomer.schoolmanagementsystem.repository.PersonRepository;
import com.soondevjomer.schoolmanagementsystem.repository.SectionRepository;
import com.soondevjomer.schoolmanagementsystem.repository.StudentRepository;
import com.soondevjomer.schoolmanagementsystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        // Now before saving check if there is a class and section provided.
        if (studentDto.getClassDto().getId()!=0) {
            Class_ class_ = classRepository.findById(studentDto.getClassDto().getId())
                    .orElseThrow(()->new NoRecordFoundException("Class", "id", studentDto.getClassDto().getId().toString()));
            student.setClass_(class_);
        }

        if (studentDto.getSectionDto().getId()!=0) {
            Section section = sectionRepository.findById(studentDto.getSectionDto().getId())
                    .orElseThrow(()->new NoRecordFoundException("Section", "id", studentDto.getSectionDto().getId().toString()));
            student.setSection(section);
        }

        Student savedStudent = studentRepository.save(student);

        PersonDto personDto = new PersonDto();
        personDto.setId(savedStudent.getPerson().getId());
        personDto.setNameDto(modelMapper.map(savedStudent.getPerson().getName(), NameDto.class));
        personDto.setAddressDto(modelMapper.map(savedStudent.getPerson().getAddress(), AddressDto.class));
        personDto.setContactDto(modelMapper.map(savedStudent.getPerson().getContact(), ContactDto.class));
        StudentDto studentDtoResponse = new StudentDto();
        studentDtoResponse.setId(savedStudent.getId());
        studentDtoResponse.setPersonDto(personDto);

        if (savedStudent.getClass_()!=null)
            studentDtoResponse.setClassDto(modelMapper.map(savedStudent.getClass_(), ClassDto.class));
        if (savedStudent.getSection()!=null)
            studentDtoResponse.setSectionDto(modelMapper.map(savedStudent.getSection(), SectionDto.class));

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
        StudentDto studentDtoResponse = new StudentDto();
        studentDtoResponse.setId(student.getId());
        studentDtoResponse.setPersonDto(personDto);

        if (student.getClass()!=null)
            studentDtoResponse.setClassDto(modelMapper.map(student.getClass_(), ClassDto.class));

        if (student.getSection()!=null)
            studentDtoResponse.setSectionDto(modelMapper.map(student.getSection(), SectionDto.class));

        return studentDtoResponse;
    }

    @Override
    public Page<StudentDto> getStudents(Integer page, Integer size, String sortField, String sortOrder) {

        Sort.Direction direction = Sort.Direction.ASC;
        if (sortOrder.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        return studentRepository.findAll(pageable)
                .map(student -> {
                    PersonDto personDto = new PersonDto();
                    personDto.setId(student.getPerson().getId());
                    personDto.setNameDto(modelMapper.map(student.getPerson().getName(), NameDto.class));
                    personDto.setAddressDto(modelMapper.map(student.getPerson().getAddress(), AddressDto.class));
                    personDto.setContactDto(modelMapper.map(student.getPerson().getContact(), ContactDto.class));
                    StudentDto studentDtoResponse = new StudentDto();
                    studentDtoResponse.setId(student.getId());
                    studentDtoResponse.setPersonDto(personDto);

                    if (student.getClass()!=null)
                        studentDtoResponse.setClassDto(modelMapper.map(student.getClass_(), ClassDto.class));

                    if (student.getSection()!=null)
                        studentDtoResponse.setSectionDto(modelMapper.map(student.getSection(), SectionDto.class));

                    return studentDtoResponse;
                });
    }

    @Override
    public StudentDto updateStudent(Integer studentId, StudentDto studentDto) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(()->new NoRecordFoundException("student", "id", studentId.toString()));

        student.getPerson().setName(modelMapper.map(studentDto.getPersonDto().getNameDto(), Name.class));
        student.getPerson().setAddress(modelMapper.map(studentDto.getPersonDto().getAddressDto(), Address.class));
        student.getPerson().setContact(modelMapper.map(studentDto.getPersonDto().getContactDto(), Contact.class));

        // Now before updating check if there is class and section provided.
        if (studentDto.getClassDto().getId()!=0) {
            Class_ class_ = classRepository.findById(studentDto.getClassDto().getId())
                    .orElseThrow(()->new NoRecordFoundException("Class", "id", studentDto.getClassDto().getId().toString()));
            student.setClass_(class_);
        }

        if (studentDto.getSectionDto().getId()!=0) {
            Section section = sectionRepository.findById(studentDto.getSectionDto().getId())
                    .orElseThrow(()->new NoRecordFoundException("Section", "id", studentDto.getSectionDto().getId().toString()));
            student.setSection(section);
        }

        Student updatedStudent = studentRepository.save(student);

        PersonDto personDto = new PersonDto();
        personDto.setId(updatedStudent.getPerson().getId());
        personDto.setNameDto(modelMapper.map(updatedStudent.getPerson().getName(), NameDto.class));
        personDto.setAddressDto(modelMapper.map(updatedStudent.getPerson().getAddress(), AddressDto.class));
        personDto.setContactDto(modelMapper.map(updatedStudent.getPerson().getContact(), ContactDto.class));

        StudentDto studentDtoResponse = new StudentDto();
        studentDtoResponse.setId(updatedStudent.getId());
        studentDtoResponse.setPersonDto(personDto);

        if (updatedStudent.getClass_()!=null)
            studentDtoResponse.setClassDto(modelMapper.map(updatedStudent.getClass_(), ClassDto.class));
        if (updatedStudent.getSection()!=null)
            studentDtoResponse.setSectionDto(modelMapper.map(updatedStudent.getSection(), SectionDto.class));

        return studentDtoResponse;
    }

    @Override
    public String deleteStudent(Integer studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(()->new NoRecordFoundException("student", "id", studentId.toString()));

        if (student.getClass_()!=null)
            student.setClass_(null);

        if (student.getSection()!=null)
            student.setSection(null);

        studentRepository.delete(student);

        return "Student deleted successfully.";
    }
}
