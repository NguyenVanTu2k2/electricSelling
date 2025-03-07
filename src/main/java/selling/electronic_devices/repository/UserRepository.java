package selling.electronic_devices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import selling.electronic_devices.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
