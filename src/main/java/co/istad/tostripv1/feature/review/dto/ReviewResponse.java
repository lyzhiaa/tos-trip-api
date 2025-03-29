package co.istad.tostripv1.feature.review.dto;

import co.istad.tostripv1.domain.Place;
import lombok.Getter;
import lombok.Setter;

public record ReviewResponse(
        Integer id,
        String uuid,
//        Integer userId,
//        Integer placeId,
        String placeUuid,
        String userUuid,
        Integer rating,
        String review,
        String createdAt
) {
}
