package com.example.studentapi;

import com.example.studentapi.entity.Student;
import com.example.studentapi.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StudentManagementApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementApiApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(StudentRepository repository) {
		return args -> {
			System.out.println("---------------------------------------------");
			System.out.println("Seeding database with sample student records...");
			
			if (repository.count() == 0) {
				repository.save(new Student("Amit Patel", "CS101", "Computer Science", 85.0, 92.5));
				repository.save(new Student("Priya Sharma", "EC204", "Electronics & Comm.", 94.0, 97.0));
				repository.save(new Student("Rohan Das", "ME301", "Mechanical Eng.", 48.0, 72.0));
				repository.save(new Student("Sneha Roy", "IT102", "Information Tech.", 78.5, 84.0));
				repository.save(new Student("Vikram Singh", "CS102", "Computer Science", 63.0, 80.0));
				
				System.out.println("Database seeding completed. " + repository.count() + " students added.");
			} else {
				System.out.println("Database already seeded. Skipping.");
			}
			System.out.println("---------------------------------------------");
		};
	}
}
