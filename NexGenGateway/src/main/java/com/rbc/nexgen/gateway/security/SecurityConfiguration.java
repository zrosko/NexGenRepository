package com.rbc.nexgen.gateway.security;

//https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
//MySql sample https://www.youtube.com/watch?v=tDZPdovCH4I
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
//TODO UserDetailService, AuthenticationManager
@Configuration
public class SecurityConfiguration {
	
	//TODO
	//@Autowired
	//private NexGenAuthenticationProvider authenticationProvider;
	//protected void configure(AuthenticationManagerBuilder auth){
	//auth.authenticationProvider(authenticationProvider);
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//http.authorizeHttpRequests((authz) -> authz.anyRequest().authenticated());
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.POST,"/iipmapi").permitAll()
		.antMatchers("/iipmapi").permitAll()
		.antMatchers("/workdayapi").permitAll()
		.antMatchers("/fibrsapi").permitAll()
		.antMatchers("/archermapi").permitAll()
		.and().formLogin().and().httpBasic();
		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/ignore1", "/ignore2");
	}
	
	@Bean
	public AuthenticationManager filterChain(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		.withUser("user").password("user").roles("USER")
		.and()
		.withUser("admin").password("admin").roles("USER", "ADMIN")
		.and()
		.passwordEncoder(null);
		return auth.build();
	}

}