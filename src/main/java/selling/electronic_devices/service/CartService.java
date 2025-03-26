package selling.electronic_devices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import selling.electronic_devices.model.CartItem;
import selling.electronic_devices.model.Product;
import selling.electronic_devices.model.User;
import selling.electronic_devices.repository.CartItemRepository;
import selling.electronic_devices.repository.ProductRepository;
import selling.electronic_devices.repository.UserRepository;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public List<CartItem> getCartItems(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public CartItem addToCart(Long userId, Long productId, int quantity) {
        User user = userRepository.findById(userId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        return cartItemRepository.save(cartItem);
    }

    public void removeFromCart(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}
