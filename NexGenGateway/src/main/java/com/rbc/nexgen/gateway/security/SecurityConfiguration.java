package com.rbc.nexgen.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		http.authorizeExchange(exchanges -> exchanges
				.pathMatchers("/nexgen/iipm/**").hasRole("ADMIN")
				.pathMatchers("/nexgen/ping/**").authenticated()
				.pathMatchers("/nexgen/workday/**").permitAll())
				.oauth2ResourceServer().jwt().jwtAuthenticationConverter(grantedAuthoritiesExtractor());
		//since no browser involed disable
		http.csrf().disable();
		return http.build();
	}

	Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new SecurityRoleConverter());
		return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
	}

}