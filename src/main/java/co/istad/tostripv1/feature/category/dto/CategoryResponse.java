package co.istad.tostripv1.feature.category.dto;

import co.istad.tostripv1.feature.Place.dto.PlaceResponse;

import java.util.List;

public record CategoryResponse(
        Integer id,
        String uuid,
        String name,
        String description,
        List<PlaceResponse> places,
        String createdAt
) {
}
