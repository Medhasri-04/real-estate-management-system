package com.realestatemanagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
	private final JwtAuthFilter jwtAuthFilter;

	public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
		this.jwtAuthFilter = jwtAuthFilter;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						// public
						.requestMatchers("/health").permitAll()
						// auth public
						.requestMatchers("/auth/register", "/auth/login", "/auth/logout", "/auth/forgot-password",
								"/auth/reset-password")
						.permitAll()
						// auth protected
						.requestMatchers("/auth/profile").authenticated().requestMatchers("/auth/register/agent")
						.hasRole("ADMIN")
						// Admin endpoints
						.requestMatchers("/admin/**").hasRole("ADMIN")
						// Amenity admin
						.requestMatchers(HttpMethod.POST, "/amenities").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/amenities/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/amenities/**").hasRole("ADMIN")
						// Property admin
						.requestMatchers(HttpMethod.POST, "/properties/*/assign-agent").hasRole("ADMIN")
						// Property agent
						.requestMatchers(HttpMethod.POST, "/properties").hasRole("AGENT")
						.requestMatchers(HttpMethod.PUT, "/properties/**").hasRole("AGENT")
						.requestMatchers(HttpMethod.DELETE, "/properties/**").hasRole("AGENT")
						// Booking confirm (AGENT or ADMIN)
						.requestMatchers(HttpMethod.PUT, "/bookings/*/confirm").hasAnyRole("AGENT", "ADMIN")
						// Everything else requires login
						.anyRequest().authenticated())
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
    PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}