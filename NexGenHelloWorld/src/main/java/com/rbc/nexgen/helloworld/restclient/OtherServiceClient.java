package com.rbc.nexgen.helloworld.restclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rbc.nexgen.helloworld.model.User;
//TODO Change this direct Microservice call using Eureka to call Gateway
//@FeignClient("gateway-service")
@FeignClient("otherservice")
public interface OtherServiceClient {
	
	@RequestMapping(method = RequestMethod.POST, value = "users", consumes = "application/json")
	List<User> getUsers();
}
