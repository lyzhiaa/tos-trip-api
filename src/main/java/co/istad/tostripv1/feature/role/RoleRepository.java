package co.istad.tostripv1.feature.role;

import co.istad.tostripv1.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Boolean existsByName(String name);
    Optional<Role> findByName(String name);
    Optional<Role> findByUuid(String uuid);
}
