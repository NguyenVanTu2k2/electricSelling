package selling.electronic_devices.DTO;

import lombok.Data;

@Data
public class OrderItemRequest {
    private Long productId;
    private int quantity;

}

