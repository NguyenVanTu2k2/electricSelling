package selling.electronic_devices.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import selling.electronic_devices.model.CartItem;
import selling.electronic_devices.service.CartService;

import java.util.List;

public class CartController {
    @RestController
    @RequestMapping("/api/cart")
    @CrossOrigin("*")
    public class CartControllers{
        @Autowired
        private CartService cartService;

        @GetMapping("/{userId}")
        public List<CartItem> getCart(@PathVariable Long userId) {
            return cartService.getCartItems(userId);
        }

        @PostMapping("/add")
        public ResponseEntity<?> addToCart(@RequestBody CartItemRequest request) {
            cartService.addToCart(request.getUserId(), request.getProductId(), request.getQuantity());
            return ResponseEntity.ok("Added to cart");
        }

        @DeleteMapping("/remove/{cartItemId}")
        public ResponseEntity<?> removeFromCart(@PathVariable Long cartItemId) {
            cartService.removeFromCart(cartItemId);
            return ResponseEntity.ok("Removed from cart");
        }
    }

    @Data
    class CartItemRequest {
        private Long userId;
        private Long productId;
        private int quantity;
    }

}
