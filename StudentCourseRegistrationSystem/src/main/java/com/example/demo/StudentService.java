package com.example.demo;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private List<Student> students = new ArrayList<>();

    // Register student
    public String registerStudent(Student student) {

        for (Student s : students) {

            if (s.getStudentId() == student.getStudentId()) {
                return "Student ID already exists";
            }
        }

        students.add(student);

        return "Student registered successfully";
    }

    // Search student by ID
    public Student searchStudent(int id) {

        for (Student s : students) {

            if (s.getStudentId() == id) {
                return s;
            }
        }

        return null;
    }

    // View all students
    public List<Student> viewAllStudents() {

        return students;
    }

    // Update course
    public String updateCourse(int id, String courseName) {

        for (Student s : students) {

            if (s.getStudentId() == id) {

                s.setCourseName(courseName);

                return "Course updated successfully";
            }
        }

        return "Student not found";
    }

    // Delete student
    public String deleteStudent(int id) {

        for (Student s : students) {

            if (s.getStudentId() == id) {

                students.remove(s);

                return "Student deleted successfully";
            }
        }

        return "Student not found";
    }
}