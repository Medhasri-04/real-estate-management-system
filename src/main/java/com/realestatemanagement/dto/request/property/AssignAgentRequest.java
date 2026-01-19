package com.realestatemanagement.dto.request.property;

import jakarta.validation.constraints.NotNull;

public class AssignAgentRequest {
	@NotNull
	private Long agentId;

	public AssignAgentRequest() {
	}

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}
}