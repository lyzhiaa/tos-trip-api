package co.istad.tostripv1.feature.Place.dto;

import jakarta.persistence.Column;

import java.util.List;

public record PlaceUpdateRequest(
        String name,
        String description,
        String openHours,
        Double entryFee,
        String location,
        Double latitude,
        Double longitude,
        List<String> imageUrls,
        @Column(updatable = false)
        String categoryUuid
) {
}
