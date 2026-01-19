package com.realestatemanagement.dto.request.availability;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class AvailabilityBlockRequest {
	@NotEmpty
	private List<BlockRangeRequest> ranges;

	public AvailabilityBlockRequest() {
	}

	public List<BlockRangeRequest> getRanges() {
		return ranges;
	}

	public void setRanges(List<BlockRangeRequest> ranges) {
		this.ranges = ranges;
	}
}