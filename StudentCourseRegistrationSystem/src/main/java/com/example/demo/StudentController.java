package com.example.demo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService service;

    // Register student
    @PostMapping
    public ResponseEntity<String> registerStudent(
            @Valid @RequestBody Student student) {

        String result = service.registerStudent(student);

        if (result.equals("Student ID already exists")) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }

    // Search student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> searchStudent(
            @PathVariable int id) {

        Student student = service.searchStudent(id);

        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(student);
    }

    // View all students
    @GetMapping
    public ResponseEntity<List<Student>> viewAllStudents() {

        return ResponseEntity.ok(service.viewAllStudents());
    }

    // Update course
    @PutMapping("/{id}/course")
    public ResponseEntity<String> updateCourse(
            @PathVariable int id,
            @RequestParam String courseName) {

        String result = service.updateCourse(id, courseName);

        if (result.equals("Student not found")) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

    // Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(
            @PathVariable int id) {

        String result = service.deleteStudent(id);

        if (result.equals("Student not found")) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }
}