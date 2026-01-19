package com.realestatemanagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
		http.csrf(csrf -> csrf.disable()).sessionManagement(sm -> sm.sessionCreationPolicy(
				SessionCreationPolicy.STATELESS)).authorizeHttpRequests(auth -> auth
						// ✅ public
						.requestMatchers("/health").permitAll()
						// ✅ auth public
						.requestMatchers("/auth/register", "/auth/login", "/auth/forgot-password",
								"/auth/reset-password")
						.permitAll()
						// ✅ logout (JWT: just return message; client deletes token)
						.requestMatchers("/auth/logout").authenticated()
						// ✅ auth protected
						.requestMatchers("/auth/profile").authenticated().requestMatchers("/auth/register/agent")
						.hasRole("ADMIN")
						// ✅ user profile management (customer/agent/admin - whoever is logged in)
						.requestMatchers( "/users/update-phone").authenticated()
						.requestMatchers( "/users/update-email").authenticated()
						.requestMatchers( "/users/update-password").authenticated()
						.requestMatchers( "/users/**").authenticated()
						// ✅ admin endpoints
						.requestMatchers("/admin/**").authenticated()
						// ✅ amenity management (ADMIN)
						.requestMatchers( "/amenities").hasRole("ADMIN")
						.requestMatchers("/amenities/**").hasRole("ADMIN")
						.requestMatchers( "/amenities/**").hasRole("ADMIN")
						.requestMatchers( "/amenities/**").permitAll()
						// ✅ property management
						.requestMatchers( "/properties/*/assign-agent").hasRole("ADMIN")
						.requestMatchers( "/properties/**").hasRole("AGENT")
						.requestMatchers( "/properties/**").hasRole("AGENT")
						.requestMatchers( "/properties/**").permitAll()
						// ✅ availability (AGENT to block/unblock; view is public)
						.requestMatchers( "/properties/*/availability").permitAll()
						.requestMatchers( "/properties/*/availability/block").hasRole("AGENT")
						.requestMatchers( "/properties/*/availability/block/**").hasRole("AGENT")
						// ✅ bookings
						.requestMatchers( "/bookings").hasRole("CUSTOMER")
						.requestMatchers( "/bookings/site-visit").hasRole("CUSTOMER")
						.requestMatchers( "/bookings/customer").hasRole("CUSTOMER")
						.requestMatchers( "/bookings/agent").hasRole("AGENT")
						.requestMatchers("/bookings/**").authenticated()
						.requestMatchers( "/bookings/*/confirm").hasAnyRole("AGENT", "ADMIN")
						.requestMatchers( "/bookings/*/cancel").authenticated()
						// ✅ reviews
						.requestMatchers( "/properties/*/reviews").hasRole("CUSTOMER")
						.requestMatchers( "/properties/*/reviews/**").permitAll()
						.requestMatchers( "/reviews/**").hasRole("CUSTOMER")
						.requestMatchers( "/reviews/**").hasRole("CUSTOMER")
						// ✅ favorites
						.requestMatchers( "/properties/*/favorite").hasRole("CUSTOMER")
						.requestMatchers( "/properties/*/favorite").hasRole("CUSTOMER")
						.requestMatchers( "/me/favorites").hasRole("CUSTOMER")
						// ✅ notifications
						.requestMatchers ("/notifications").authenticated()
						.requestMatchers( "/notifications/*/read").authenticated()
						.requestMatchers( "/notifications/**").authenticated()
						// ✅ everything else requires login
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