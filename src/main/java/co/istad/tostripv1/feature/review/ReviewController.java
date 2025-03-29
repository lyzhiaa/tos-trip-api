package co.istad.tostripv1.feature.review;

import co.istad.tostripv1.feature.review.dto.ReviewCreateRequest;
import co.istad.tostripv1.feature.review.dto.ReviewResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    // create review
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    ReviewResponse createReview(@Valid @RequestBody ReviewCreateRequest reviewCreateRequest) {
        return reviewService.createReview(reviewCreateRequest);
    }
    // get all reviews
    @GetMapping
    List<ReviewResponse> getAllReviews() {
        return reviewService.getAllReviews();
    }
    // get review by uuid
    @GetMapping("/{uuid}")
    ReviewResponse getReviewByUuid(@Valid @PathVariable("uuid") String uuid) {
        return reviewService.getReviewByUuid(uuid);
    }
    // get review by place uuid
    @GetMapping("/places/{uuid}")
    List<ReviewResponse> getReviewByPlaceUuid(@Valid @PathVariable("uuid") String uuid) {
        return reviewService.getReviewByPlaceUuid(uuid);
    }
    // delete review by uuid
    @DeleteMapping("/{uuid}")
    void deleteReview(@Valid @PathVariable("uuid") String uuid) {
        reviewService.deleteReview(uuid);
    }
}
