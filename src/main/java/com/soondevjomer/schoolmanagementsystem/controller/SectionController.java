package com.soondevjomer.schoolmanagementsystem.controller;

import com.soondevjomer.schoolmanagementsystem.dto.SectionDto;
import com.soondevjomer.schoolmanagementsystem.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/sections")
@RequiredArgsConstructor
public class SectionController {

    private final SectionService sectionService;

    @PostMapping
    public ResponseEntity<SectionDto> addSection(@RequestBody SectionDto sectionDto) {

        return new ResponseEntity<>(sectionService.addSection(sectionDto), HttpStatus.CREATED);
    }

    @GetMapping("/{sectionId}")
    public ResponseEntity<SectionDto> getSection(@PathVariable Integer sectionId) {

        return ResponseEntity.ok(sectionService.getSection(sectionId));
    }

    @GetMapping
    public ResponseEntity<List<SectionDto>> getSections() {

        return ResponseEntity.ok(sectionService.getSections());
    }

    @PutMapping("/{sectionId}")
    public ResponseEntity<SectionDto> updateSection(
            @PathVariable Integer sectionId,
            @RequestBody SectionDto sectionDto) {

        return ResponseEntity.ok(sectionService.updateSection(sectionId, sectionDto));
    }

    @DeleteMapping("/{sectionId}")
    public ResponseEntity<String> deleteSection(@PathVariable Integer sectionId) {

        return ResponseEntity.ok(sectionService.deleteSection(sectionId));
    }
}
