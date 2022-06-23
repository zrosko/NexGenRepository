package com.rbc.nexgen.template.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rbc.nexgen.template.model.HelloMessage;
import com.rbc.nexgen.template.service.client.TargetServiceFeignClient;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TemplateService {

	@Autowired
	TargetServiceFeignClient targetFeignClient; // we call ourself for the test (hello2)
	
	public void processHelloMessage(HelloMessage message) {
		//TODO to test circuit braker comment the if statement below
		if (message != null)
			log.info("1. Inside TemplateService.processHelloMessage" + message.toString());
		/*
		 * ************************************************ Here we code business logic
		 * and call to Data Access Layer (SQL Server, Kafka) repositories, call other
		 * srvices
		 ************************************************/

		String received_message = targetFeignClient.getHello2();
		log.info("2. Inside TemplateService.processHelloMessage" + received_message.toString());

	}
}
