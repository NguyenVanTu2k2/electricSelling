package selling.electronic_devices.DTO;

import lombok.Data;
import selling.electronic_devices.model.User;

import java.util.List;

@Data
public class OrderRequest {
    private User user;
    private List<OrderItemRequest> items;

}
