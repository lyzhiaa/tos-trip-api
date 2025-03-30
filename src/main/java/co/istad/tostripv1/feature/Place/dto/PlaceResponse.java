package co.istad.tostripv1.feature.Place.dto;


import co.istad.tostripv1.feature.category.dto.CategoryNamePlaceResponse;

import java.util.List;

public record PlaceResponse(
        Integer id,
        String uuid,
        String name,
        CategoryNamePlaceResponse category,
        String description,
        String openHours,
        Double entryFee,
        List<String> imageUrls,
        String location,
        String createdAt,
        String updatedAt,
        Boolean disabled
) {
}
