package com.rbc.nexgen.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.rbc.nexgen.batch.model.StudentJdbc;
import com.rbc.nexgen.batch.model.StudentResponse;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class StudentFromRestAPIProcessor implements ItemProcessor<StudentResponse, StudentJdbc> {

	@Override
	public StudentJdbc process(StudentResponse item) throws Exception {
		
		log.info("NexGen we are processing item #: " + item.getId());
		
		StudentJdbc student = new StudentJdbc();
		
		student.setId(item.getId());
		student.setFirstName(item.getFirstName());
		student.setLastName(item.getLastName());
		student.setEmail(item.getEmail());
		
		return student;
		
	}

}
