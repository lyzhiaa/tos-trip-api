package co.istad.tostripv1.feature.Place.dto;

import co.istad.tostripv1.domain.Category;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

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
        @NotBlank(message = "Category name is required")
        String categoryName// User inputs the category name instead of ID

//        Integer categoryId
) {
}
