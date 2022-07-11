package com.rbc.nexgen.batch.processor;

import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.stereotype.Component;

import com.rbc.nexgen.batch.model.IIPMApplication;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class IIPMApplicationValidator implements Validator<IIPMApplication>{

	@Override
	public void validate(IIPMApplication item) throws ValidationException {
		log.info("NexGen we are validating item #: " + item.getAppCode());
		
		if (item.getAppCode() == null) {
			throw new ValidationException("appCode must not be null");
		}

		if (item.getAppCustodian() == null) {
			throw new ValidationException("appCustodian must not be null");
		}
	}
}
