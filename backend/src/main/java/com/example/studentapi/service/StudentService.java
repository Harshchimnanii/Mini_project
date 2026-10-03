package com.example.studentapi.service;

import com.example.studentapi.dto.StudentRequestDTO;
import com.example.studentapi.dto.StudentResponseDTO;

import java.util.List;

public interface StudentService {
    
    List<StudentResponseDTO> getAllStudents();
    
    StudentResponseDTO getStudentById(Long id);
    
    StudentResponseDTO getStudentByRollNumber(String rollNumber);
    
    StudentResponseDTO createStudent(StudentRequestDTO request);
    
    StudentResponseDTO updateStudent(Long id, StudentRequestDTO request);
    
    void deleteStudent(Long id);
}
