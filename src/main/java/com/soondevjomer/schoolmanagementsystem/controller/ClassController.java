package com.soondevjomer.schoolmanagementsystem.controller;

import com.soondevjomer.schoolmanagementsystem.dto.ClassDto;
import com.soondevjomer.schoolmanagementsystem.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/classes")
@RequiredArgsConstructor
public class ClassController {
    
    private final ClassService classService;
    
    @PostMapping
    public ResponseEntity<ClassDto> addClass(@RequestBody ClassDto classDto) {
        
        return new ResponseEntity<>(classService.addClass(classDto), HttpStatus.CREATED);
    }
    
    @GetMapping("/{classId}")
    public ResponseEntity<ClassDto> getClass(@PathVariable Integer classId) {
        
        return ResponseEntity.ok(classService.getClass(classId));
    }
    
    @GetMapping
    public ResponseEntity<List<ClassDto>> getClasses() {
        
        return ResponseEntity.ok(classService.getClasses());
    }
    
    @PutMapping("/{classId}")
    public ResponseEntity<ClassDto> updateClass(
        @PathVariable Integer classId, 
        @RequestBody ClassDto classDto) {
        
        return ResponseEntity.ok(classService.updateClass(classId, classDto));
    }
    
    @DeleteMapping("/{classId}")
    public ResponseEntity<String> deleteClass(@PathVariable Integer classId) {
        
        return ResponseEntity.ok(classService.deleteClass(classId));
    }
}
