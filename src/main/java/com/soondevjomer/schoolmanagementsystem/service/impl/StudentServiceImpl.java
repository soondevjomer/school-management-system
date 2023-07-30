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
        if (studentDto.getClassSectionDto()!=null) {
            ClassSection classSection = classSectionRepository.findAll().stream()
                    .filter(cs ->
                            cs.getClass_().getId().equals(studentDto.getClassSectionDto().getClassDto().getId()) &&
                                    cs.getSection().getId().equals(studentDto.getClassSectionDto().getSectionDto().getId()))
                    .findFirst()
                    .orElseGet(()-> createClassSection(studentDto.getClassSectionDto().getClassDto().getId(), studentDto.getClassSectionDto().getSectionDto().getId()));

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

        Person person = personRepository.findById(student.getPerson().getId())
                .orElseThrow(()->new NoRecordFoundException("Person", "id", student.getPerson().getId().toString()));

        person.getName().setId(person.getName().getId());
        person.getName().setFirstName(studentDto.getPersonDto().getNameDto().getFirstName());
        person.getName().setMiddleName(studentDto.getPersonDto().getNameDto().getMiddleName());
        person.getName().setLastName(studentDto.getPersonDto().getNameDto().getLastName());
        person.getName().setExtensionName(studentDto.getPersonDto().getNameDto().getExtensionName());
        person.getAddress().setId(person.getAddress().getId());
        person.getAddress().setLine(studentDto.getPersonDto().getAddressDto().getLine());
        person.getAddress().setStreet(studentDto.getPersonDto().getAddressDto().getStreet());
        person.getAddress().setCity(studentDto.getPersonDto().getAddressDto().getCity());
        person.getAddress().setState(studentDto.getPersonDto().getAddressDto().getState());
        person.getAddress().setZipcode(studentDto.getPersonDto().getAddressDto().getZipcode());
        person.getContact().setId(person.getContact().getId());
        person.getContact().setMobileNumber(studentDto.getPersonDto().getContactDto().getMobileNumber());
        person.getContact().setEmail(studentDto.getPersonDto().getContactDto().getEmail());
        student.setPerson(person);

        if (
                !studentDto.getClassSectionDto().getClassDto().getId()
                        .equals(student.getClassSection().getClass_().getId()) ||
                !studentDto.getClassSectionDto().getSectionDto().getId()
                        .equals(student.getClassSection().getSection().getId())
        ) {
            // There is changes in student class and section
            Integer previousClassSectionId = student.getClassSection().getId();

            ClassSection classSection = classSectionRepository.findAll().stream()
                    .filter(cs ->
                            cs.getClass_().getId().equals(studentDto.getClassSectionDto().getClassDto().getId()) &&
                                    cs.getSection().getId().equals(studentDto.getClassSectionDto().getSectionDto().getId()))
                    .findFirst()
                    .orElseGet(()->createClassSection(studentDto.getClassSectionDto().getClassDto().getId(), studentDto.getClassSectionDto().getSectionDto().getId()));

            student.setClassSection(classSection);

            Student updatedStudent = studentRepository.save(student);

            //Now delete if the previous one has already no existing student.
            ClassSection previousClassSection = classSectionRepository.findById(previousClassSectionId)
                    .orElseThrow(()->new NoRecordFoundException("Class", "id", previousClassSectionId.toString()));

            if (previousClassSection.getStudents().size()==1) {
                classSectionRepository.delete(previousClassSection);
            }

            return createStudentDtoResponse(updatedStudent);
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

    private ClassSection createClassSection(Integer classId, Integer sectionId) {

        Class_ class_ = classRepository.findById(classId)
                .orElseThrow(()->new NoRecordFoundException("Class", "id", classId.toString()));

        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(()->new NoRecordFoundException("Section", "id", sectionId.toString()));

        ClassSection classSection = new ClassSection();
        classSection.setClass_(class_);
        classSection.setSection(section);

        return classSection;
    }
}
