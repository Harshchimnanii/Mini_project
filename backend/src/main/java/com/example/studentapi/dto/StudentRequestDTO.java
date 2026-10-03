package com.example.studentapi.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class StudentRequestDTO {

    @NotBlank(message = "Name is required and cannot be blank")
    private String name;

    @NotBlank(message = "Roll number is required and cannot be blank")
    private String rollNumber;

    @NotBlank(message = "Course name is required and cannot be blank")
    private String course;

    @NotNull(message = "Marks are required")
    @Min(value = 0, message = "Marks must be at least 0")
    @Max(value = 100, message = "Marks cannot exceed 100")
    private Double marks;

    @NotNull(message = "Attendance percentage is required")
    @Min(value = 0, message = "Attendance must be at least 0%")
    @Max(value = 100, message = "Attendance cannot exceed 100%")
    private Double attendance;

    // Constructors
    public StudentRequestDTO() {}

    public StudentRequestDTO(String name, String rollNumber, String course, Double marks, Double attendance) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.course = course;
        this.marks = marks;
        this.attendance = attendance;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Double getMarks() {
        return marks;
    }

    public void setMarks(Double marks) {
        this.marks = marks;
    }

    public Double getAttendance() {
        return attendance;
    }

    public void setAttendance(Double attendance) {
        this.attendance = attendance;
    }
}
