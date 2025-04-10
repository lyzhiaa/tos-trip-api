package co.istad.tostripv1.mapper;

import co.istad.tostripv1.domain.Category;
import co.istad.tostripv1.domain.Place;
import co.istad.tostripv1.feature.Place.dto.PlaceCreateRequest;
import co.istad.tostripv1.feature.Place.dto.PlaceResponse;
import co.istad.tostripv1.feature.Place.dto.PlaceUpdateRequest;
import jakarta.persistence.MappedSuperclass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlaceMapper {
    // create place request to place entity
    @Mapping(ignore = true, target = "id")
    Place fromPlaceCreateRequest(PlaceCreateRequest placeCreateRequest);
    // search place by name
    PlaceResponse toPlaceResponse(Place place);
    // all places to place response
    List<PlaceResponse> toPlaceResponseList(List<Place> places);
    // update place request to place entity
    void fromPlaceUpdateRequest(PlaceUpdateRequest placeUpdateRequest, @MappingTarget Place place);
}
