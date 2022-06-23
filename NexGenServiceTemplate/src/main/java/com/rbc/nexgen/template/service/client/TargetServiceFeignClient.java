package com.rbc.nexgen.template.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("template")
//@FeignClient(value = "template", path = "api/template")
public interface TargetServiceFeignClient {
	@RequestMapping(method = RequestMethod.GET, value = "hello2", consumes = "application/json")
	String getHello2();
}
