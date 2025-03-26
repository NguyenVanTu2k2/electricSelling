package selling.electronic_devices.service;

import org.springframework.stereotype.Service;
import selling.electronic_devices.DTO.OrderItemRequest;
import selling.electronic_devices.DTO.OrderRequest;
import selling.electronic_devices.model.Order;
import selling.electronic_devices.model.OrderItem;
import selling.electronic_devices.model.Product;
import selling.electronic_devices.repository.OrderRepository;
import selling.electronic_devices.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    // Tạo đơn hàng
    public Order createOrder(OrderRequest request) {
        Order order = new Order();
        order.setUser(request.getUser());

        List<OrderItem> items = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());

            // Chuyển product.getPrice() từ double sang BigDecimal
            item.setPrice(BigDecimal.valueOf(product.getPrice())
                    .multiply(BigDecimal.valueOf(itemReq.getQuantity())));

            totalPrice = totalPrice.add(item.getPrice());
            items.add(item);
        }

        order.setItems(items);
        order.setTotalPrice(totalPrice);
        return orderRepository.save(order);
    }

    // Lấy danh sách đơn hàng của người dùng
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // Lấy chi tiết đơn hàng
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));
    }

    // Hủy đơn hàng
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));
        order.setStatus("CANCELED");
        orderRepository.save(order);
    }
}

