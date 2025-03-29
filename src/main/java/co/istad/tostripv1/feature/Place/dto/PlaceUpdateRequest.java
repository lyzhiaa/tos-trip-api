package co.istad.tostripv1.feature.Place.dto;

import co.istad.tostripv1.domain.Category;

import java.util.List;

public record PlaceUpdateRequest(
        String name,
        String description,
        String address,
        String openingHours,
        Double entryFee,
        List<String> imageUrls,
        String location,
        String categoryUuid
) {
}
