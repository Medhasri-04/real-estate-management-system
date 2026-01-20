package com.realestatemanagement.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.realestatemanagement.dto.request.review.ReviewCreateRequest;
import com.realestatemanagement.dto.response.MessageResponse;
import com.realestatemanagement.dto.response.ReviewSummaryResponse;
import com.realestatemanagement.entity.Property;
import com.realestatemanagement.entity.Review;
import com.realestatemanagement.entity.User;
import com.realestatemanagement.repository.PropertyRepository;
import com.realestatemanagement.repository.ReviewRepository;
import com.realestatemanagement.repository.UserRepository;
import com.realestatemanagement.service.ReviewService;

class ReviewServiceTest {
	@Mock
	private ReviewRepository reviewRepo;
	@Mock
	private PropertyRepository propertyRepo;
	@Mock
	private UserRepository userRepo;
	private ReviewService reviewService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		reviewService = new ReviewService(reviewRepo, propertyRepo, userRepo, null); // adjust if your constructor has mapper
																				// too
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("cust@example.com", null));
	}

	@Test
	void addReview_success_savesReview_returnsMessage() {
		Long propertyId = 1L;
		ReviewCreateRequest req = new ReviewCreateRequest();
		req.setRating(5);
		req.setComment("Nice property");
		Property p = new Property();
		p.setId(1L);
		User cust = new User();
		cust.setEmail("cust@example.com");
		cust.setEnabled(true);
		when(propertyRepo.findById(1L)).thenReturn(Optional.of(p));
		when(userRepo.findByEmail("cust@example.com")).thenReturn(Optional.of(cust));
		when(reviewRepo.save(any(Review.class))).thenAnswer(inv -> inv.getArgument(0));
		MessageResponse res = reviewService.add(propertyId, req);
		assertEquals("Review submitted", res.getMessage());
		verify(reviewRepo).save(any(Review.class));
	}

	@Test
	void summary_calculatesTotalAverageAndBreakdown() {
		Long propertyId = 1L;
		Review r1 = new Review();
		r1.setRating(5);
		Review r2 = new Review();
		r2.setRating(4);
		Review r3 = new Review();
		r3.setRating(5);
		when(reviewRepo.findByPropertyId(propertyId)).thenReturn(List.of(r1, r2, r3));
		ReviewSummaryResponse resp = reviewService.summary(propertyId);
		assertEquals(propertyId, resp.getPropertyId());
		assertEquals(3, resp.getTotalReviews());
		assertEquals((5 + 4 + 5) / 3.0, resp.getAverageRating());
		assertEquals(2, resp.getBreakdown().get("5"));
		assertEquals(1, resp.getBreakdown().get("4"));
		assertEquals(0, resp.getBreakdown().get("1"));
	}
}
