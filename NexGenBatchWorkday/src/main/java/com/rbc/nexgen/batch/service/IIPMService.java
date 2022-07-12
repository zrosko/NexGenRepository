package com.rbc.nexgen.batch.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import com.rbc.nexgen.batch.model.IIPMApplicationResponseJson;
import com.rbc.nexgen.batch.reader.GenericJsonObjectReader;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class IIPMService {

	/*
	 * @Autowired RestTemplate restTemplate;
	 */
	
	//@Autowired
	//OAuth2AuthorizedClientService oauth2ClientService;
	
	List<IIPMApplicationResponseJson> list;
	
	/**
	 * @return the list
	 */
	public List<IIPMApplicationResponseJson> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<IIPMApplicationResponseJson> list) {
		this.list = list;
	}

	public List<IIPMApplicationResponseJson> restCallToGetApplications() {
		try {
			GenericJsonObjectReader<IIPMApplicationResponseJson> reader = 
					new GenericJsonObjectReader<IIPMApplicationResponseJson>(IIPMApplicationResponseJson.class, "AppDocumentRest");
			reader.open(new FileSystemResource("C:\\Users\\zrosk\\git\\NexGenRepository\\NexGenBatchIIPM\\InputFiles\\applications.json"));
			
			List<Object> ret_list = reader.getObjectsList(); 			
			list = new ArrayList<>();
			
			while (ret_list != null && !ret_list.isEmpty()) {
				list.add((IIPMApplicationResponseJson) ret_list.remove(0));
			}
		}catch(Exception e) {
			log.error("**** IIPM - failed calling REST: "+e);
		}finally {
			log.info("**** IIPM - finished call to REST API");
		}
		return list;
	}

	public IIPMApplicationResponseJson getNextApplication() {
		log.info("**** IIPM - we are reading 1 by 1 here");
		if (list == null) {
			restCallToGetApplications();
		}

		if (list != null && !list.isEmpty()) {
			return list.remove(0);
		}
		list = null;		
		return null;
	}
}
