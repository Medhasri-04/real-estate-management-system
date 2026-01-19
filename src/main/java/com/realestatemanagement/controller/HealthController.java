package com.realestatemanagement.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realestatemanagement.dto.response.HealthResponse;

@RestController
public class HealthController {
	// 51. GET /health
	@GetMapping("/health")
	public HealthResponse health() {
		HealthResponse res = new HealthResponse();
		res.setStatus("OK");
		res.setTimestamp(LocalDateTime.now());
		return res;
	}
}