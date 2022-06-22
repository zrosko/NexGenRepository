package com.rbc.nexgen.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.rbc.nexgen.batch.postgresql.entity.Student;

@Component
public class FirstItemProcessor implements ItemProcessor<Student, com.rbc.nexgen.batch.mysql.entity.Student> {

	@Override
	public com.rbc.nexgen.batch.mysql.entity.Student process(Student item) throws Exception {
		
		System.out.println(item.getId());
		
		com.rbc.nexgen.batch.mysql.entity.Student student = new 
				com.rbc.nexgen.batch.mysql.entity.Student();
		
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
