package com.rbc.nexgen.bcm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbc.nexgen.bcm.model.ServiceModel;
import com.rbc.nexgen.bcm.service.dao.ServiceJPA;
import com.rbc.nexgen.bcm.service.dao.ServiceKafka;
import com.rbc.nexgen.bcm.service.dao.entity.ServiceEntity;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ServiceProducerService {
	@Autowired
	private ServiceKafka serviceKafka;
	
	@Autowired
	private ServiceJPA serviceJPA;

	public void processService(ServiceModel service) {
		log.info("1. Inside ServiceProducerService.processService" + service.toString());
		/* ************************************************
		 * Ignore input service, temp code to read it from DB
		 * ************************************************/
		//TODO call BCM Solution API to get updates to the SERVICE entitty
		List<ServiceEntity> service_entities = serviceJPA.getAllServicesUpdated();
		for (ServiceEntity service_entity: service_entities) {
			System.out.println(service_entity);
			service = (ServiceModel) objectMapper(service_entity);		
			/* ********************************************** */
			/**
			 * Raw data is saved to the Source Topic (Kafka).
			 */
			service.setType("Update TYPE source");
			
			serviceKafka.produceServiceToSourceTopic(service);

			/*************************************************
			 * Here goes the transformation logic. User data from the Iput System
			 * need to be transformed, enriched, etc., and saved to the Sink Topic (Kafka).
			 *************************************************/

			service.setType("Update TYPE sink");
			/**
			 * Transformed data is saved to the Source Topic (Kafka).
			 */
			serviceKafka.produceServiceToSinkTopic(service);
		}
	}
	
	public static Object objectMapper(Object object){
    	ObjectMapper mapper = new ObjectMapper();
    	mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    	ServiceModel service = mapper.convertValue(object, ServiceModel.class);
        return service;
    }
}
