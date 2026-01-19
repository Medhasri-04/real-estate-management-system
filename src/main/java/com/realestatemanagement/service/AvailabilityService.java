package com.realestatemanagement.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.realestatemanagement.dto.request.availability.AvailabilityBlockRequest;
import com.realestatemanagement.dto.response.AvailabilityResponse;
import com.realestatemanagement.dto.response.MessageResponse;
import com.realestatemanagement.entity.AvailabilityBlock;
import com.realestatemanagement.entity.Property;
import com.realestatemanagement.mapper.AvailabilityMapper;
import com.realestatemanagement.repository.AvailabilityBlockRepository;
import com.realestatemanagement.repository.PropertyRepository;

@Service
public class AvailabilityService {
	private final PropertyRepository propertyRepository;
	private final AvailabilityBlockRepository blockRepository;
	private final AvailabilityMapper availabilityMapper;

	public AvailabilityService(PropertyRepository propertyRepository, AvailabilityBlockRepository blockRepository,
			AvailabilityMapper availabilityMapper) {
		this.propertyRepository = propertyRepository;
		this.blockRepository = blockRepository;
		this.availabilityMapper = availabilityMapper;
	}

	// GET /properties/{propertyId}/availability?from=YYYY-MM-DD&to=YYYY-MM-DD
	// For beginner simplicity: we return all blocks for that property (you can
	// filter by date later if needed).
	public AvailabilityResponse getAvailability(Long propertyId) {
		Property property = propertyRepository.findById(propertyId)
				.orElseThrow(() -> new RuntimeException("Property not found"));
		List<AvailabilityBlock> blocks = blockRepository.findByPropertyId(propertyId);
		// "available" = property availability AND no blocks exist (simple rule)
		boolean available = Boolean.TRUE.equals(property.getAvailability()) && blocks.isEmpty();
		return availabilityMapper.toResponse(propertyId, available, blocks);
	}

	// PUT /properties/{propertyId}/availability/block
	public MessageResponse blockAvailability(Long propertyId, AvailabilityBlockRequest req) {
		Property property = propertyRepository.findById(propertyId)
				.orElseThrow(() -> new RuntimeException("Property not found"));
		if (req.getRanges() == null || req.getRanges().isEmpty()) {
			throw new RuntimeException("ranges is required");
		}
		for (var range : req.getRanges()) {
			AvailabilityBlock b = new AvailabilityBlock();
			b.setProperty(property);
			b.setStartDate(range.getStartDate());
			b.setEndDate(range.getEndDate());
			b.setReason(range.getReason());
			blockRepository.save(b);
		}
		return new MessageResponse("Availability blocked", LocalDateTime.now());
	}

	// DELETE /properties/{propertyId}/availability/block/{blockId}
	public MessageResponse removeBlock(Long propertyId, Long blockId) {
		// Ensure property exists
		propertyRepository.findById(propertyId).orElseThrow(() -> new RuntimeException("Property not found"));
		AvailabilityBlock block = blockRepository.findById(blockId)
				.orElseThrow(() -> new RuntimeException("Block not found"));
		// Safety: ensure block belongs to same property
		if (block.getProperty() == null || !block.getProperty().getId().equals(propertyId)) {
			throw new RuntimeException("Block does not belong to this property");
		}
		blockRepository.delete(block);
		return new MessageResponse("Block removed", LocalDateTime.now());
	}
}