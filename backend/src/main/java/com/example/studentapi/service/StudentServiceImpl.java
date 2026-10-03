package com.example.studentapi.service;

import com.example.studentapi.dto.StudentRequestDTO;
import com.example.studentapi.dto.StudentResponseDTO;
import com.example.studentapi.entity.Student;
import com.example.studentapi.exception.DuplicateRollNumberException;
import com.example.studentapi.exception.ResourceNotFoundException;
import com.example.studentapi.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponseDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with ID " + id + " not found"));
        return mapToResponseDTO(student);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponseDTO getStudentByRollNumber(String rollNumber) {
        Student student = studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Student with Roll Number " + rollNumber + " not found"));
        return mapToResponseDTO(student);
    }

    @Override
    public StudentResponseDTO createStudent(StudentRequestDTO request) {
        // Validate unique roll number
        if (studentRepository.existsByRollNumber(request.getRollNumber())) {
            throw new DuplicateRollNumberException("Student with Roll Number '" + request.getRollNumber() + "' already exists");
        }

        Student student = new Student();
        student.setName(request.getName());
        student.setRollNumber(request.getRollNumber());
        student.setCourse(request.getCourse());
        student.setMarks(request.getMarks());
        student.setAttendance(request.getAttendance());

        Student savedStudent = studentRepository.save(student);
        return mapToResponseDTO(savedStudent);
    }

    @Override
    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO request) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with ID " + id + " not found"));

        // If the roll number is changing, make sure the new one is not already taken
        if (!existingStudent.getRollNumber().equals(request.getRollNumber())) {
            if (studentRepository.existsByRollNumber(request.getRollNumber())) {
                throw new DuplicateRollNumberException("Student with Roll Number '" + request.getRollNumber() + "' already exists");
            }
        }

        existingStudent.setName(request.getName());
        existingStudent.setRollNumber(request.getRollNumber());
        existingStudent.setCourse(request.getCourse());
        existingStudent.setMarks(request.getMarks());
        existingStudent.setAttendance(request.getAttendance());

        Student updatedStudent = studentRepository.save(existingStudent);
        return mapToResponseDTO(updatedStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student with ID " + id + " not found");
        }
        studentRepository.deleteById(id);
    }

    // Helper method to convert Student Entity to StudentResponseDTO
    private StudentResponseDTO mapToResponseDTO(Student student) {
        StudentResponseDTO response = new StudentResponseDTO();
        response.setId(student.getId());
        response.setName(student.getName());
        response.setRollNumber(student.getRollNumber());
        response.setCourse(student.getCourse());
        response.setMarks(student.getMarks());
        response.setAttendance(student.getAttendance());
        response.setGrade(calculateGrade(student.getMarks()));
        return response;
    }

    // Helper method to dynamically calculate grade based on marks
    private String calculateGrade(Double marks) {
        if (marks == null) {
            return "N/A";
        }
        if (marks >= 90.0) {
            return "A+";
        } else if (marks >= 80.0) {
            return "A";
        } else if (marks >= 70.0) {
            return "B";
        } else if (marks >= 60.0) {
            return "C";
        } else if (marks >= 50.0) {
            return "D";
        } else {
            return "Fail";
        }
    }
}
