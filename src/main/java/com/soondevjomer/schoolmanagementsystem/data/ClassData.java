package com.soondevjomer.schoolmanagementsystem.data;

import com.soondevjomer.schoolmanagementsystem.entity.Class_;
import com.soondevjomer.schoolmanagementsystem.repository.ClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClassData implements CommandLineRunner {

    private final ClassRepository classRepository;

    @Override
    public void run(String... args) throws Exception {

        if (classRepository.count()!=0) {
            return;
        }

        Class_ class_1 = new Class_();
        class_1.setName("One");

        Class_ class_2 = new Class_();
        class_2.setName("Two");

        Class_ class_3 = new Class_();
        class_3.setName("Three");

        Class_ class_4 = new Class_();
        class_4.setName("Four");

        Class_ class_5 = new Class_();
        class_5.setName("Five");

        Class_ class_6 = new Class_();
        class_6.setName("Six");

        List<Class_> classes = new ArrayList<>();
        classes.add(class_1);
        classes.add(class_2);
        classes.add(class_3);
        classes.add(class_4);
        classes.add(class_5);
        classes.add(class_6);
        classRepository.saveAll(classes);
    }
}
