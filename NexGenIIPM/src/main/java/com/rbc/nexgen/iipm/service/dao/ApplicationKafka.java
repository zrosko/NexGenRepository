package com.rbc.nexgen.iipm.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

import com.rbc.nexgen.iipm.service.dao.entity.ApplicationEntity;

import lombok.extern.log4j.Log4j2;

/**
Indicates that an annotated class is a "Repository", originally defined byDomain-Driven Design (Evans, 2003) 
as "a mechanism for encapsulating storage,retrieval, and search behavior which emulates a collection of objects". 
Teams implementing traditional Java EE patterns such as "Data Access Object"may also apply this stereotype 
to DAO classes, though care should be taken to understand the distinction between Data Access Object and 
DDD-style repositories before doing so. This annotation is a general-purpose stereotype and individual 
teams may narrow their semantics and use as appropriate. 
**/

@Repository
@Log4j2
public class ApplicationKafka {
	
	@Autowired
	private KafkaTemplate<String, ApplicationEntity> kafkaTemplate;
	
	public void produceApplicationToSourceTopic(ApplicationEntity application){
		log.info("1.1 - ApplicationKafka.produceApplicationToSourceTopic = " + application);
		kafkaTemplate.send("iipm_applicaton_source_topic", application.getAppName(), application);
	}
	
	public void produceApplicationToSinkTopic(ApplicationEntity application){
		log.info("1.1 - ApplicationKafka.produceApplicationToSinkTopic = " + application);
		kafkaTemplate.send("iipm_applicaton_sink_topic", application.getAppName(), application);
	}
}
