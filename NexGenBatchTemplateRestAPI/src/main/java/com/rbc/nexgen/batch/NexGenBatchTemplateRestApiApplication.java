package com.rbc.nexgen.batch;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/api/v1")
public class NexGenBatchTemplateRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NexGenBatchTemplateRestApiApplication.class, args);
	}
	@GetMapping("/students")
	public List<StudentResponse> students() {
		return Arrays.asList(
				new StudentResponse(1L, "John", "Smith", "john@gmail.com"),
				new StudentResponse(2L, "Sachin", "Dave", "sachin@gmail.com"),
				new StudentResponse(3L, "Peter", "Mark", "peter@gmail.com"),
				new StudentResponse(4L, "Martin", "Smith", "martin@gmail.com"),
				new StudentResponse(5L, "Raj", "Patel", "raj@gmail.com"),
				new StudentResponse(6L, "Virat", "Yadav", "virat@gmail.com"),
				new StudentResponse(7L, "Prabhas", "Shirke", "prabhas@gmail.com"),
				new StudentResponse(8L, "Tina", "Kapoor", "tina@gmail.com"),
				new StudentResponse(9L, "Mona", "Sharma", "mona@gmail.com"),
				new StudentResponse(10L, "Rahul", "Varma", "rahul@gmail.com"));
	}
	
	@PostMapping("/createStudent")
	public StudentResponse createStudent(@RequestBody StudentRequest studentRequest) {
		System.out.println("Student Created "+ studentRequest.getId());
		return new StudentResponse(studentRequest.getId(), 
				studentRequest.getFirstName(), 
				studentRequest.getLastName(), 
				studentRequest.getEmail());
	}

}
