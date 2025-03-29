package co.istad.tostripv1.feature.review;

import co.istad.tostripv1.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, String> {
    Boolean existsByUserUuidAndPlaceUuid(String userUuid, String placeUuid);
    Boolean existsByUserIdAndPlaceId(Integer userId, Integer placeId);
    Optional<Review> findByUserUuidAndPlaceUuid(String userUuid, String placeUuid);
    Optional<Review> findByUuid(String uuid);
    List<Review> findAllByPlaceId(Integer PlaceId);
    List<Review> findAllByPlaceUuid(String placeUuid);
}
