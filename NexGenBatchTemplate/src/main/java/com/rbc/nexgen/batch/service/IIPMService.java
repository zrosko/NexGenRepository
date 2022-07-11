package com.rbc.nexgen.batch.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.rbc.nexgen.batch.model.IIPMApplicationResponseJson;

import lombok.extern.log4j.Log4j2;

//@Service
@Log4j2
public class IIPMService {

	@Autowired
	RestTemplate restTemplate;
	
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
	 * When Scheduler calls the REST API to get data, the variable "list" has to be set to NULL.
	 * 
	 * @param list the list to set
	 */
	public void setList(List<IIPMApplicationResponseJson> list) {
		this.list = list;
	}

	public List<IIPMApplicationResponseJson> restCallToGetApplications() {
		/* RestTemplate restTemplate = new RestTemplate(); */
		//TODO externalize
		String url = "https://apigw-int.devfg.rbc.com/PL00/Application/v1/App/0/100";
		/*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken)authentication; 
		OAuth2AuthorizedClient oauth2Client = oauth2ClientService.loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(), 
				authToken.getName());
		String jwtAccessToken = oauth2Client.getAccessToken().getTokenValue();
		OidcUser principal;*/
		HttpHeaders headers = new HttpHeaders();
		headers.add("Service-Account", "Basic "+"TODO user:password Base64");
		HttpEntity<Object> entity = new HttpEntity<>(headers);
		ResponseEntity<List<IIPMApplicationResponseJson>> responseEntity =
				restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<IIPMApplicationResponseJson>>() {});
		//IIPMApplicationResponseJson[] IIPMApplicationResponseJsonArray = restTemplate.getForObject(end_point,
				//IIPMApplicationResponseJson[].class);
		
		list = new ArrayList<>();
		List<IIPMApplicationResponseJson> responseList = responseEntity.getBody();

		for (IIPMApplicationResponseJson sr : responseList) {
			list.add(sr);
		}

		return list;
	}

	public IIPMApplicationResponseJson getOneApplication(long id, String name) {
		log.info("We are reading 1 by 1 here - id = " + id + " and name = " + name);
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
