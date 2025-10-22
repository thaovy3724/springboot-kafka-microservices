package com.swa.order_service.order_domain.order_application_service.handler;

import com.swa.order_service.order_domain.order_application_service.dto.update.add.UpdateAddItemsCommand;
import com.swa.order_service.order_domain.order_application_service.dto.update.add.UpdateAddItemsResponse;
import com.swa.order_service.order_domain.order_application_service.mapper.OrderDataMapper;
import com.swa.order_service.order_domain.order_application_service.ports.output.OrderRepository;
import com.swa.order_service.order_domain.order_domain_core.entity.Order;
import com.swa.order_service.order_domain.order_domain_core.entity.OrderItem;
import com.swa.order_service.order_domain.order_domain_core.exception.OrderDomainException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateAddItemsHandler {
    private final OrderDataMapper mapper;
    private final OrderRepository repository;

    public UpdateAddItemsHandler(OrderDataMapper mapper, OrderRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public UpdateAddItemsResponse updateAddItems(UpdateAddItemsCommand command) {
        // Tìm order dựa trên tracking ID
        // Ở đây findByTrackingId trả về kiểu optional, mục đích là tránh việc order có thể lấy giá trị null
        // orElseThrow trả về giá trị của optional nếu có, còn không sẽ ném ngoại lệ
        Order order = repository.findByTrackingId(command.getTrackingId())
                .orElseThrow(
                        () -> new OrderDomainException(
                                "Order with tracking ID " + command.getTrackingId() + " not found"
                        )
                );

        // Map items dto trong command -> entity
        List<OrderItem> newItems = mapper.itemsToOrderItems(command.getItems());

        // Check business rule và update
        Order updateAddItemsOrder = order.validateAndUpdateAddItems(newItems);

        // Lưu cập nhật vào DB
        Order orderUpdated = repository.save(updateAddItemsOrder);
        //Trả response
        return mapper.orderToUpdateAddItemsResponse(
                orderUpdated,
                "Cập nhật thêm items vào order thành công");
    }
}
