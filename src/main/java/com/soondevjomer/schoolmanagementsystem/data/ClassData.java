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
        class_1.setName("UNDEFINED");

        Class_ class_2 = new Class_();
        class_2.setName("1");
        class_2.setDescription("One");

        Class_ class_3 = new Class_();
        class_3.setName("2");
        class_3.setDescription("Two");

        Class_ class_4 = new Class_();
        class_4.setName("3");
        class_4.setDescription("Three");

        Class_ class_5 = new Class_();
        class_5.setName("4");
        class_5.setDescription("Four");

        Class_ class_6 = new Class_();
        class_6.setName("5");
        class_6.setDescription("Five");

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
