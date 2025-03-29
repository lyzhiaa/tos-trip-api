package co.istad.tostripv1.feature.Place.dto;


import java.util.List;

public record PlaceResponse(
        Integer id,
        String uuid,
        String name,
        String description,
        String openHours,
        Double entryFee,
        List<String> imageUrls,
        String location,
        String categoryUuid,
        String createdAt,
        String updatedAt,
        Boolean disabled
) {
}
