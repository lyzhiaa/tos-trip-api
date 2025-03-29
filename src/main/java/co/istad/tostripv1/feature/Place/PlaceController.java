package co.istad.tostripv1.feature.Place;

import co.istad.tostripv1.feature.Place.dto.PlaceCreateRequest;
import co.istad.tostripv1.feature.Place.dto.PlaceResponse;
import co.istad.tostripv1.feature.Place.dto.PlaceUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/places")
public class PlaceController {
    private final PlaceService placeService;

    // create place
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    PlaceResponse createPlace(@Valid @RequestBody PlaceCreateRequest placeCreateRequest) {
        return placeService.createPlace(placeCreateRequest);
    }

    // get all places
    @GetMapping
    List<PlaceResponse> getAllPlaces() {
        return placeService.getAllPlaces();
    }

    // get place by uuid
    @GetMapping("/{uuid}")
    PlaceResponse getPlaceByUuid(@Valid @PathVariable("uuid") String uuid) {
        return placeService.getPlaceByUuid(uuid);
    }

    // update place
    @PatchMapping("/{uuid}")
    PlaceResponse updatePlace(@Valid @PathVariable("uuid") String uuid, PlaceUpdateRequest placeUpdateRequest) {
        return placeService.updatePlace(uuid, placeUpdateRequest);
    }

    // disable place by uuid
    @PatchMapping("/{uuid}/disable")
    void disablePlace(@Valid @PathVariable("uuid") String uuid) {
        placeService.disablePlaceByUuid(uuid);
    }

    // enable place by uuid
    @PatchMapping("/{uuid}/enable")
    void enablePlace(@Valid @PathVariable("uuid") String uuid) {
        placeService.enablePlaceByUuid(uuid);
    }

    // delete place by uuid
    @DeleteMapping("/{uuid}")
    void deletePlace(@Valid @PathVariable("uuid") String uuid) {
        placeService.deletePlace(uuid);
    }
}
