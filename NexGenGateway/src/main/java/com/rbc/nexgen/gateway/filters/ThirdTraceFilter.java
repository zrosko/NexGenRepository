package com.rbc.nexgen.gateway.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Order(3)
@Configuration
@Slf4j
public class ThirdTraceFilter {

	@Autowired
	FilterUtility filterUtility;
	
	@Autowired
	Tracer tracer;

	@Bean
	public GlobalFilter postGlobalFilter() {
		
		return (exchange, chain) -> {
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				ServerHttpResponse response = exchange.getResponse();
				log.info("Post Filter = " + response.getStatusCode());
				HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
				String correlationId = filterUtility.getTraceId(requestHeaders);
				log.debug("Updated the correlation id to the outbound headers. {}", correlationId);
				exchange.getResponse().getHeaders().add(FilterUtility.TRACE_ID, correlationId);
			}));
		};
	}
}