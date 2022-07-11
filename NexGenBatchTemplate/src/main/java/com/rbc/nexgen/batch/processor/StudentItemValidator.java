package com.rbc.nexgen.batch.processor;

import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.stereotype.Component;

import com.rbc.nexgen.batch.model.StudentResponse;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class StudentItemValidator implements Validator<StudentResponse>{

	@Override
	public void validate(StudentResponse item) throws ValidationException {
		log.info("NexGen we are validating item #: " + item.getId());
		
		if (item.getFirstName() == null) {
			throw new ValidationException("firstName must not be null");
		}

		if (item.getLastName() == null) {
			throw new ValidationException("lastName must not be null");
		}
	}
}
