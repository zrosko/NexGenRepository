package com.rbc.nexgen.bcm.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

import com.rbc.nexgen.bcm.model.ServiceModel;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class ServiceKafka {
	
	@Autowired
	private KafkaTemplate<Integer, ServiceModel> kafkaTemplate;
	
	public void produceServiceToSourceTopic(ServiceModel service){
		log.info("1.1 - ServiceKafka.produceServiceToSourceTopic = " + service);
		kafkaTemplate.send("bcm_service_source_topic", service.getId(), service);
	}
	
	public void produceServiceToSinkTopic(ServiceModel service){
		log.info("1.2 - ServiceKafka.produceServiceToSinkTopic = " + service);
		kafkaTemplate.send("bcm_service_sink_topic", service.getId(), service);
	}
}
