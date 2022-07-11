package com.rbc.nexgen.batch.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class StepExecutionListenerImpl implements StepExecutionListener {

	@Override
	public void beforeStep(StepExecution stepExecution) {
		log.info("**** NexGen **** Before Step " + stepExecution.getStepName());		
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.info("**** NexGen **** After Step " + stepExecution.getStepName());		
		return null;
	}

}
