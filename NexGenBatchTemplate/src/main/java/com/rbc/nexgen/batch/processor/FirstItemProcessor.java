package com.rbc.nexgen.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.rbc.nexgen.batch.mysql.entity.StudentJpa;
import com.rbc.nexgen.batch.postgresql.entity.Student;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class FirstItemProcessor implements ItemProcessor<Student, StudentJpa> {

	@Override
	public StudentJpa process(Student item) throws Exception {
		
		log.info("NexGen we are processing item #: " + item.getId());
		
		StudentJpa student = new StudentJpa();
		
		student.setId(item.getId());
		student.setFirstName(item.getFirstName());
		student.setLastName(item.getLastName());
		student.setEmail(item.getEmail());
		student.setDeptId(item.getDeptId());
		student.setIsActive(item.getIsActive() != null ? 
				Boolean.valueOf(item.getIsActive()) : false);
		
		return student;
		
	}

}
