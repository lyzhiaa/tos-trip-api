package co.istad.tostripv1.feature.Place;

import co.istad.tostripv1.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, String> {
    boolean existsByName(String name);
    Optional<Place> findByName(String name);
    Optional<Place> findByUuid(String uuid);
    List<Place> findByCategoryUuid(String categoryUuid);
}
