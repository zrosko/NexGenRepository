package com.rbc.nexgen.batch.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
/*import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;*/
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rbc.nexgen.batch.model.IIPMApplicationResponse;

import lombok.extern.log4j.Log4j2;

//@Service
@Log4j2
public class IIPMService {

	@Autowired
	RestTemplate restTemplate;
	
	//@Autowired
	//OAuth2AuthorizedClientService oauth2ClientService;
	
	List<IIPMApplicationResponse> list;
	
	/**
	 * @return the list
	 */
	public List<IIPMApplicationResponse> getList() {
		return list;
	}

	/**
	 * When Scheduler calls the REST API to get data, the variable "list" has to be set to NULL.
	 * 
	 * @param list the list to set
	 */
	public void setList(List<IIPMApplicationResponse> list) {
		this.list = list;
	}

	public List<IIPMApplicationResponse> restCallToGetApplications() {
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
		ResponseEntity<List<IIPMApplicationResponse>> responseEntity =
				restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<IIPMApplicationResponse>>() {});
		//IIPMApplicationResponse[] IIPMApplicationResponseArray = restTemplate.getForObject(end_point,
				//IIPMApplicationResponse[].class);
		
		list = new ArrayList<>();
		List<IIPMApplicationResponse> responseList = responseEntity.getBody();

		for (IIPMApplicationResponse sr : responseList) {
			list.add(sr);
		}

		return list;
	}

	public IIPMApplicationResponse getOneApplication(long id, String name) {
		log.info("We are reading 1 by 1 here - id = " + id + " and name = " + name);
		if (list == null) {
			restCallToGetApplications();
		}

		if (list != null && !list.isEmpty()) {
			return list.remove(0);
		}
		return null;
	}
}
