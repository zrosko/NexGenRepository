package com.rbc.nexgen.iipm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbc.nexgen.iipm.model.User;
import com.rbc.nexgen.iipm.service.dao.UserJPA;
import com.rbc.nexgen.iipm.service.dao.UserKafka;
import com.rbc.nexgen.iipm.service.dao.entity.UserEntity;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserProducerService {
	@Autowired
	private UserKafka userKafka;
	
	@Autowired
	private UserJPA userJPA;

	public void processUser(User user) {
		log.info("1. Inside UserProducerService.processUser" + user.toString());
		/* ************************************************
		 * Ignore input user, temp code to read it from DB
		 * ************************************************/
		UserEntity user_entity = userJPA.getById(user.getId());
		user = (User) objectMapper(user_entity);		
		/* ********************************************** */
		/**
		 * Raw data is saved to the Source Topic (Kafka).
		 */
		user.setUpdateTime("User here we go it looks NOT");
		
		userKafka.produceUserToSourceTopic(user);
		/**
		 * Here goes the transformation logic. User data from the Iput System
		 * need to be transformed, enriched, etc., and saved to the Sink Topic (Kafka).
		 * 
		 **/
		user.setUpdateTime("User here we go it looks OK");
		/**
		 * Transformed data is saved to the Source Topic (Kafka).
		 */
		userKafka.produceUserToSinkTopic(user);
	}
	
	public static Object objectMapper(Object object){
    	ObjectMapper mapper = new ObjectMapper();
    	mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    	User user = mapper.convertValue(object, User.class);
        return user;
    }
}
