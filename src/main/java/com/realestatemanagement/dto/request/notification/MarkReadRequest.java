package com.realestatemanagement.dto.request.notification;

import jakarta.validation.constraints.NotNull;

public class MarkReadRequest {
	@NotNull
	private Boolean read;

	public MarkReadRequest() {
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}
}