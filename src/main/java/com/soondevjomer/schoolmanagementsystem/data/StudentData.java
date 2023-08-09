package com.soondevjomer.schoolmanagementsystem.data;

import com.github.javafaker.Faker;
import com.soondevjomer.schoolmanagementsystem.entity.*;
import com.soondevjomer.schoolmanagementsystem.exception.NoRecordFoundException;
import com.soondevjomer.schoolmanagementsystem.repository.ClassRepository;
import com.soondevjomer.schoolmanagementsystem.repository.SectionRepository;
import com.soondevjomer.schoolmanagementsystem.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
@RequiredArgsConstructor
public class StudentData implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;
    private final SectionRepository sectionRepository;
    private final Faker faker;

    @Override
    public void run(String... args) {

        if (studentRepository.count()!=0) {
            return;
        }

        for (int i = 1; i <= 6; i++) {

            Person person = createPerson();

            Student student = new Student();
            student.setPerson(person);

            int finalI = i;
            Class_ class_ = classRepository.findById(i)
                    .orElseThrow(()->new NoRecordFoundException("Class", "id", String.valueOf(finalI)));

            Section section = sectionRepository.findById(i)
                    .orElseThrow(()->new NoRecordFoundException("Section", "id", String.valueOf(finalI)));

            ClassSection classSection = new ClassSection();
            classSection.setClass_(class_);
            classSection.setSection(section);

            student.setClassSection(classSection);

            studentRepository.save(student);
        }

    }

    private Person createPerson() {
        Name name = new Name();
        name.setFirstName(faker.name().firstName());
        name.setLastName(faker.name().lastName());
        Address address = new Address();
        address.setLine(faker.address().buildingNumber());
        address.setStreet(faker.address().streetName());
        address.setCity(faker.address().city());
        address.setState(faker.address().state());
        address.setZipcode(faker.address().zipCode());
        Contact contact = new Contact();
        contact.setMobileNumber(faker.phoneNumber().cellPhone());
        contact.setEmail(faker.internet().emailAddress());
        Person person = new Person();
        person.setName(name);
        person.setAddress(address);
        person.setContact(contact);
        return person;
    }
}
