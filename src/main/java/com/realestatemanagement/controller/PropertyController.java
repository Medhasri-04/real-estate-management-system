package com.realestatemanagement.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.realestatemanagement.dto.request.PropertyRequestDTO;
import com.realestatemanagement.dto.response.PropertyResponseDTO;
import com.realestatemanagement.entity.User;
import com.realestatemanagement.service.PropertyService;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    // CREATE PROPERTY (AGENT)
    @PostMapping
    public ResponseEntity<PropertyResponseDTO> createProperty(
            @RequestBody PropertyRequestDTO dto,
            @AuthenticationPrincipal User agent) {

        return new ResponseEntity<>(
                propertyService.createProperty(dto, agent.getId()),
                HttpStatus.CREATED
        );
    }

    // GET ALL PROPERTIES
    @GetMapping
    public List<PropertyResponseDTO> getAllProperties() {
        return propertyService.getAllProperties();
    }

    // GET PROPERTY BY ID
    @GetMapping("/{propertyId}")
    public PropertyResponseDTO getProperty(@PathVariable Long propertyId) {
        return propertyService.getPropertyById(propertyId);
    }

    // UPDATE PROPERTY
    @PutMapping("/{propertyId}")
    public PropertyResponseDTO updateProperty(
            @PathVariable Long propertyId,
            @RequestBody PropertyRequestDTO dto) {

        return propertyService.updateProperty(propertyId, dto);
    }

    // DELETE PROPERTY
    @DeleteMapping("/{propertyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProperty(@PathVariable Long propertyId) {
        propertyService.deleteProperty(propertyId);
    }

    // ASSIGN AGENT (ADMIN)
    @PostMapping("/{propertyId}/assign-agent")
    public ResponseEntity<String> assignAgent(
            @PathVariable Long propertyId,
            @RequestParam Long agentId) {

        propertyService.assignAgent(propertyId, agentId);
        return ResponseEntity.ok("Agent assigned successfully");
    }

    // ADD AMENITIES
    @PostMapping("/{propertyId}/amenities")
    public ResponseEntity<String> addAmenities(
            @PathVariable Long propertyId,
            @RequestBody Set<Long> amenityIds) {

        propertyService.addAmenities(propertyId, amenityIds);
        return ResponseEntity.ok("Amenities linked to property");
    }
}
