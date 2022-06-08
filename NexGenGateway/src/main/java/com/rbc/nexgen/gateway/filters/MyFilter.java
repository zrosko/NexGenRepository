package com.rbc.nexgen.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
@Order(2)
@Component
@Slf4j
public class MyFilter implements GlobalFilter {
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("Preprocessing goes here"+exchange.getRequest());
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			log.info("Post processing goes here"+exchange.getResponse());
		}));
	}

}
