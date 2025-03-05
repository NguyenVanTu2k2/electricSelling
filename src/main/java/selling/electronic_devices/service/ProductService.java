package selling.electronic_devices.service;
import org.springframework.stereotype.Service;
import selling.electronic_devices.model.Product;
import selling.electronic_devices.repository.ProductRepository;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
