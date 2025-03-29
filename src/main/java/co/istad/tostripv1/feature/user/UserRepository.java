package co.istad.tostripv1.feature.user;


import co.istad.tostripv1.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);
    boolean existsByUsernameOrEmail(String username, String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByUuid(String uuid);

//    Optional<User> findById(String id);

}
