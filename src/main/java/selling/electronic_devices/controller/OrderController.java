package selling.electronic_devices.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import selling.electronic_devices.DTO.OrderRequest;
import selling.electronic_devices.model.Order;
import selling.electronic_devices.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Tạo đơn hàng
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    // Lấy danh sách đơn hàng của người dùng
    @GetMapping
    public ResponseEntity<List<Order>> getUserOrders(@RequestParam Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }

    // Xem chi tiết đơn hàng
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderDetails(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    // Hủy đơn hàng
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("Đơn hàng đã bị hủy");
    }
}
