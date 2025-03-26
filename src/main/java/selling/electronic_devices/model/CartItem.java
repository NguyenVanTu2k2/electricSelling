package selling.electronic_devices.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Người dùng sở hữu giỏ hàng

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product; // Sản phẩm được thêm vào giỏ hàng

    private int quantity; // Số lượng sản phẩm

    // Getter, Setter
}
