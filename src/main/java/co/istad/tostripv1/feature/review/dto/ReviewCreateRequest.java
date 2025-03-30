package co.istad.tostripv1.feature.review.dto;

import jakarta.persistence.Column;

public record ReviewCreateRequest(
        @Column(nullable = false)
        String placeUuid,
        @Column(nullable = false)
        String userUuid,

//        @Column(name = "user_id", nullable = false)
//        Integer userId,
//        @Column(name = "place_id", nullable = false)
//        Integer placeId,
        @Column(nullable = false)

        Integer rating,
        String review

) {
}
