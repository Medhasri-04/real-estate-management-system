package com.realestatemanagement.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.realestatemanagement.dto.response.AvailabilityResponse;
import com.realestatemanagement.dto.response.BlockedRangeResponse;
import com.realestatemanagement.entity.AvailabilityBlock;

@Component
public class AvailabilityMapper {
	public AvailabilityResponse toResponse(Long propertyId, boolean available, List<AvailabilityBlock> blocks) {
		AvailabilityResponse res = new AvailabilityResponse();
		res.setPropertyId(propertyId);
		res.setAvailable(available);
		List<BlockedRangeResponse> blocked = new ArrayList<>();
		if (blocks != null) {
			for (AvailabilityBlock b : blocks) {
				blocked.add(toBlockedRange(b));
			}
		}
		res.setBlockedRanges(blocked);
		return res;
	}

	public BlockedRangeResponse toBlockedRange(AvailabilityBlock b) {
		BlockedRangeResponse r = new BlockedRangeResponse();
		r.setBlockId(b.getId());
		r.setStartDate(b.getStartDate());
		r.setEndDate(b.getEndDate());
		r.setReason(b.getReason());
		return r;
	}
}