package co.istad.tostripv1.feature.review;

import co.istad.tostripv1.domain.Review;
import co.istad.tostripv1.feature.review.dto.ReviewCreateRequest;
import co.istad.tostripv1.feature.review.dto.ReviewResponse;
import co.istad.tostripv1.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    // create review
    @Override
    public ReviewResponse createReview(ReviewCreateRequest reviewCreateRequest) {
        if (reviewRepository.existsByUserUuidAndPlaceUuid(
                reviewCreateRequest.userUuid(),
                reviewCreateRequest.placeUuid())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This review already exists");
        }
        Review review = reviewMapper.fromReviewCreateRequest(reviewCreateRequest);
        review.setUuid(UUID.randomUUID().toString());
        review.setCreatedAt(String.valueOf(LocalDateTime.now()));

        reviewRepository.save(review);

        return reviewMapper.toReviewResponse(review);
    }
    // get review by place uuid
    @Override
    public List<ReviewResponse> getReviewByPlaceUuid(String placeUuid) {
        List<Review> reviews = reviewRepository.findAllByPlaceUuid(placeUuid);
        return reviewMapper.toReviewResponseList(reviews);
    }

    // get review by uuid
    @Override
    public ReviewResponse getReviewByUuid(String uuid) {
        Review review = reviewRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));
        return reviewMapper.toReviewResponse(review);
    }

    // get all reviews
    @Override
    public List<ReviewResponse> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviewMapper.toReviewResponseList(reviews);
    }

    // delete review by uuid
    @Override
    public void deleteReview(String uuid) {
        Review review = reviewRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));
        reviewRepository.delete(review);
    }
}
