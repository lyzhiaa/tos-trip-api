package co.istad.tostripv1.feature.Place;

import co.istad.tostripv1.domain.Category;
import co.istad.tostripv1.domain.Place;
import co.istad.tostripv1.feature.Place.dto.PlaceCreateRequest;
import co.istad.tostripv1.feature.Place.dto.PlaceResponse;
import co.istad.tostripv1.feature.Place.dto.PlaceUpdateRequest;
import co.istad.tostripv1.feature.category.CategoryRepository;
import co.istad.tostripv1.mapper.PlaceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceMapper placeMapper;
    private final PlaceRepository placeRepository;
    private final CategoryRepository categoryRepository;
    //create place
    @Override
    public PlaceResponse createPlace(PlaceCreateRequest placeCreateRequest) {
        if (placeRepository.existsByName(placeCreateRequest.name())) {
            throw new RuntimeException("Place with name " + placeCreateRequest.name() + " already exists");
        }

        // Check if category exists, otherwise create a new one
        Category category = categoryRepository.findByName(placeCreateRequest.categoryName())
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(placeCreateRequest.categoryName());
                    return categoryRepository.save(newCategory);
                });

        // Convert DTO to Entity using Mapper
        Place place = placeMapper.fromPlaceCreateRequest(placeCreateRequest);
        place.setUuid(java.util.UUID.randomUUID().toString());
        place.setCreatedAt(String.valueOf(LocalDateTime.now()));
        place.setUpdatedAt(String.valueOf(LocalDateTime.now())); // Fix the duplicate `setCreatedAt`
        place.setDisabled(false);
        place.setCategory(category); // Assign the correct category

        placeRepository.save(place);
        return placeMapper.toPlaceResponse(place);
    }

    // get all places
    @Override
    public List<PlaceResponse> getAllPlaces() {
        List<Place> places = placeRepository.findAll();

        return placeMapper.toPlaceResponseList(places);
    }

    // get place by uuid
    @Override
    public PlaceResponse getPlaceByUuid(String uuid) {
        Place place = placeRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Place with uuid " + uuid + " not found"));
        return placeMapper.toPlaceResponse(place);
    }

    // update place
    @Override
    public PlaceResponse updatePlace(String uuid, PlaceUpdateRequest placeUpdateRequest) {
        Place place = placeRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Place with uuid " + uuid + " not found"));
        placeMapper.fromPlaceUpdateRequest(placeUpdateRequest, place);
        place.setUpdatedAt(String.valueOf(LocalDateTime.now()));
        place = placeRepository.save(place);
        return placeMapper.toPlaceResponse(place);
    }

    // delete place
    @Override
    public void deletePlace(String uuid) {
        Place place = placeRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Place with uuid " + uuid + " not found"));

        placeRepository.delete(place);

    }

    // disable place by uuid
    @Override
    public void disablePlaceByUuid(String uuid) {
        Place place = placeRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Place with uuid " + uuid + " not found"));

        place.setDisabled(true);
        placeRepository.save(place);

    }

    // enable place by uuid
    @Override
    public void enablePlaceByUuid(String uuid) {
        Place place = placeRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Place with uuid " + uuid + " not found"));

        place.setDisabled(false);
        placeRepository.save(place);
    }
}
