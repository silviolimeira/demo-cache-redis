package com.limeira.democacheredis.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.limeira.democacheredis.services.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authConfig -> {
			authConfig.requestMatchers(HttpMethod.GET, "/").permitAll();
			authConfig.requestMatchers(HttpMethod.GET, "/user").hasAnyAuthority("USER", "ROLE_USER", "OIDC_USER");
			authConfig.requestMatchers(HttpMethod.GET, "/admin").hasAnyAuthority("ROLE_ADMIN", "ROLE_DEVELOPER", "ROLE_USER");
			authConfig.anyRequest().authenticated();
		}).csrf().disable().formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	UserDetailsService myUserDetailsService() {
		return new MyUserDetailsService();
	}
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public ApplicationListener<AuthenticationSuccessEvent> successEvent() {
		return event -> {
			System.err.println("Sucess Login" + event.getAuthentication().getClass().getName() + " - " + event.getAuthentication().getName());
		};
	}

	@Bean
	public ApplicationListener<AuthenticationSuccessEvent> failureEvent() {
		return event -> {
			System.err.println("Bad Credentials Login" + event.getAuthentication().getClass().getName() + " - " + event.getAuthentication().getName());
		};
	}

}
