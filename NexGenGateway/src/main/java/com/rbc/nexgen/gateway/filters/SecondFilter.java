package com.rbc.nexgen.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
@Order(2)
@Component
@Slf4j
public class SecondFilter implements GlobalFilter {
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("Preprocessing goes here"+exchange.getRequest());
		ServerHttpRequest request = exchange.getRequest();
		if (request.getURI().toString().contains("/api/template/")) {
			log.info("Role based access controll goes here");
		}
		log.info("Authoriation = " + request.getHeaders().getFirst("Authoriation"));
		//if(autheniticated)
		//then
		//return chain.filter(exchange);
		//else
		//throw error (return status)
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			log.info("Post processing response = "+exchange.getResponse());
			ServerHttpResponse response = exchange.getResponse();
			log.info("Post processing status = "+response.getStatusCode());
		}));
	}

}
