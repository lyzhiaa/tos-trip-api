package co.istad.tostripv1.feature.category;

import co.istad.tostripv1.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Boolean existsByName(String name);
    Optional<Category> findByName(String name);
    Optional<Category> findByUuid(String uuid);
}
