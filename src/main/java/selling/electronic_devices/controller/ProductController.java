package selling.electronic_devices.controller;

import org.springframework.web.bind.annotation.*;
import selling.electronic_devices.model.Product;
import selling.electronic_devices.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}

