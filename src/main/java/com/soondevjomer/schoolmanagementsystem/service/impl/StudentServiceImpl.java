package com.soondevjomer.schoolmanagementsystem.service.impl;

import com.soondevjomer.schoolmanagementsystem.dto.StudentDto;
import com.soondevjomer.schoolmanagementsystem.entity.*;
import com.soondevjomer.schoolmanagementsystem.exception.NoRecordFoundException;
import com.soondevjomer.schoolmanagementsystem.repository.PersonRepository;
import com.soondevjomer.schoolmanagementsystem.repository.StudentRepository;
import com.soondevjomer.schoolmanagementsystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    @Override
    public StudentDto addStudent(StudentDto studentDto) {

        Person person = new Person();
        person.setName(studentDto.getPerson().getName());
        person.setAddress(studentDto.getPerson().getAddress());
        person.setContact(studentDto.getPerson().getContact());
        Person savedPerson = personRepository.save(person);

        Student student = new Student();
        student.setPerson(savedPerson);
        Student savedStudent = studentRepository.save(student);

        return modelMapper.map(savedStudent, StudentDto.class);
    }

    @Override
    public StudentDto getStudent(Integer studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(()->new NoRecordFoundException("student", "id", studentId.toString()));

        return modelMapper.map(student, StudentDto.class);
    }

    @Override
    public Page<StudentDto> getStudents(Integer page, Integer size, String sortField, String sortOrder) {

        Sort.Direction direction = Sort.Direction.ASC;
        if (sortOrder.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        return studentRepository.findAll(pageable)
                .map(student -> modelMapper.map(student, StudentDto.class));
    }

    @Override
    public StudentDto updateStudent(Integer studentId, StudentDto studentDto) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(()->new NoRecordFoundException("student", "id", studentId.toString()));

        student.getPerson().setName(modelMapper.map(studentDto.getPerson().getName(), Name.class));
        student.getPerson().setAddress(modelMapper.map(studentDto.getPerson().getAddress(), Address.class));
        student.getPerson().setContact(modelMapper.map(studentDto.getPerson().getContact(), Contact.class));
        Student updatedStudent = studentRepository.save(student);

        return modelMapper.map(updatedStudent, StudentDto.class);
    }

    @Override
    public String deleteStudent(Integer studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(()->new NoRecordFoundException("student", "id", studentId.toString()));

        studentRepository.delete(student);

        return "Student deleted successfully.";
    }
}
