package selling.electronic_devices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import selling.electronic_devices.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {}

