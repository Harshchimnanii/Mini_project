package com.example.studentapi.dto;

public class StudentResponseDTO {
    
    private Long id;
    private String name;
    private String rollNumber;
    private String course;
    private Double marks;
    private Double attendance;
    private String grade;

    // Constructors
    public StudentResponseDTO() {}

    public StudentResponseDTO(Long id, String name, String rollNumber, String course, Double marks, Double attendance, String grade) {
        this.id = id;
        this.name = name;
        this.rollNumber = rollNumber;
        this.course = course;
        this.marks = marks;
        this.attendance = attendance;
        this.grade = grade;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
