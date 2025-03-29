package co.istad.tostripv1.feature.Place;

import co.istad.tostripv1.feature.Place.dto.PlaceCreateRequest;
import co.istad.tostripv1.feature.Place.dto.PlaceResponse;
import co.istad.tostripv1.feature.Place.dto.PlaceUpdateRequest;

import java.util.List;

public interface PlaceService {
    // create place
    PlaceResponse createPlace(PlaceCreateRequest placeCreateRequest);
    // get all places
    List<PlaceResponse> getAllPlaces();
    // get place by uuid
    PlaceResponse getPlaceByUuid(String uuid);
    // update place
    PlaceResponse updatePlace(String uuid, PlaceUpdateRequest placeUpdateRequest);
    // delete place
    void deletePlace(String uuid);
    // disable place by uuid
    void disablePlaceByUuid(String uuid);
    // enable place by uuid
    void enablePlaceByUuid(String uuid);
}
