package com.soondevjomer.schoolmanagementsystem.data;

import com.soondevjomer.schoolmanagementsystem.entity.Class_;
import com.soondevjomer.schoolmanagementsystem.entity.Section;
import com.soondevjomer.schoolmanagementsystem.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SectionData implements CommandLineRunner {

    private final SectionRepository sectionRepository;

    @Override
    public void run(String... args) throws Exception {

        if (sectionRepository.count()!=0) {
            return;
        }

        Section section1 = new Section();
        section1.setName("Apricot");

        Section section2 = new Section();
        section2.setName("Banana");

        Section section3 = new Section();
        section3.setName("Cashew");

        Section section4 = new Section();
        section4.setName("Dragon Fruit");

        Section section5 = new Section();
        section5.setName("Egg Plant");

        Section section6 = new Section();
        section6.setName("Fox Grape");

        List<Section> sections = new ArrayList<>();
        sections.add(section1);
        sections.add(section2);
        sections.add(section3);
        sections.add(section4);
        sections.add(section5);
        sections.add(section6);
        sectionRepository.saveAll(sections);
    }
}
