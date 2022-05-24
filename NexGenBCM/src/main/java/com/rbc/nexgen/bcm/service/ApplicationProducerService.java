package com.rbc.nexgen.bcm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rbc.nexgen.bcm.service.dao.ApplicationJPA;
import com.rbc.nexgen.bcm.service.dao.ApplicationKafka;
import com.rbc.nexgen.bcm.service.dao.entity.ApplicationEntity;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ApplicationProducerService {
	@Autowired
	private ApplicationKafka applicationKafka;
	
	@Autowired
	private ApplicationJPA applicationJPA;

	public void processApplication(ApplicationEntity application) {
		log.info("2. Inside ApplicationProducerService.processApplication" + application.toString());
		/* ************************************************
		 * Ignore input user, temp code to read it from DB
		 * ************************************************/
		application = applicationJPA.getById(application.getId());
		/* ********************************************** */
		/**
		 * Raw data is saved to the Source Topic (Kafka).
		 */
		applicationKafka.produceApplicationToSourceTopic(application);
		/**
		 * Here goes the transformation logic. Application data from the Iput System
		 * need to be transformed, enriched, etc., and saved to the Sink Topic (Kafka).
		 * 
		 **/
		application.setUpdateTime("Application here we go it looks OK");
		/**
		 * Transformed data is saved to the Source Topic (Kafka).
		 */
		applicationKafka.produceApplicationToSinkTopic(application);
	}
}
