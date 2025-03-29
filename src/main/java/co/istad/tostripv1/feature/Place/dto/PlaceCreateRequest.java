package co.istad.tostripv1.feature.Place.dto;

import co.istad.tostripv1.domain.Category;

import java.util.List;

public record PlaceCreateRequest(
        String name,
        String description,
        String openHours,
        Double entryFee,
        String location,
        Double latitude,
        Double longitude,
        List<String> imageUrls,
        String categoryUuid

//        Integer categoryId
) {
}
