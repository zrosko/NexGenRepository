package com.rbc.nexgen.iipm.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

import com.rbc.nexgen.iipm.model.User;

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
public class UserKafka {
	
	@Autowired
	private KafkaTemplate<String, User> kafkaTemplate;
	
	public void produceUserToSourceTopic(User user){
		log.info("1.1 - UserKafka.produceUserToSourceTopic = " + user);
		kafkaTemplate.send("iipm_user_source_topic", user.getName(), user);
	}
	
	public void produceUserToSinkTopic(User user){
		log.info("1.2 - UserKafka.produceUserToSinkTopic = " + user);
		kafkaTemplate.send("iipm_user_sink_topic", user.getName(), user);
	}
}
