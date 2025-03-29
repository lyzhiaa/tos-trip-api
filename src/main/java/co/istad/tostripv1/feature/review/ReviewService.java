package co.istad.tostripv1.feature.review;

import co.istad.tostripv1.feature.review.dto.ReviewCreateRequest;
import co.istad.tostripv1.feature.review.dto.ReviewResponse;

import java.util.List;

public interface ReviewService {
    // create review
    ReviewResponse createReview(ReviewCreateRequest reviewCreateRequest);
    // get review by uuid
    ReviewResponse getReviewByUuid(String uuid);
    // get review by place uuid
    List<ReviewResponse> getReviewByPlaceUuid(String placeUuid);
    // get all reviews
    List<ReviewResponse> getAllReviews();
    // delete review by uuid
    void deleteReview(String uuid);
}
