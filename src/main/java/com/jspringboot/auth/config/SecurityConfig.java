package com.jspringboot.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	/*
	 * @Bean public UserDetailsService userDetailsService(PasswordEncoder encoder) {
	 * UserDetails adminUserDetails =
	 * User.withUsername("name").password(encoder.encode("dev3")).roles(
	 * "INTERNALADMIN").build(); UserDetails operationUserDetails =
	 * User.withUsername("code").password(encoder.encode("dev2")).roles(
	 * "INTERNALOPERATIONS").build(); UserDetails bothUserDetails =
	 * User.withUsername("id").password(encoder.encode("dev1")).roles(
	 * "INTERNALADMIN","INTERNALOPERATIONS").build(); return new
	 * InMemoryUserDetailsManager(adminUserDetails, operationUserDetails,
	 * bothUserDetails); }
	 * 
	 */

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserService();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf().disable()
				.authorizeHttpRequests().requestMatchers("/user/**").permitAll().and()
				.authorizeHttpRequests().requestMatchers("/product/**").authenticated().and()
				.formLogin().and().build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();

	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
}
