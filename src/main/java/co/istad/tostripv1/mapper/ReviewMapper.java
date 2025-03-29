package co.istad.tostripv1.mapper;

import co.istad.tostripv1.domain.Review;
import co.istad.tostripv1.feature.review.dto.ReviewCreateRequest;
import co.istad.tostripv1.feature.review.dto.ReviewResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    // create review
    Review fromReviewCreateRequest(ReviewCreateRequest reviewCreateRequest);
    // get review by uuid
    ReviewResponse toReviewResponse(Review review);
    // get all reviews
    List<ReviewResponse> toReviewResponseList(List<Review> reviews);

}
