package com.swa.order_service.order_domain.order_application_service.handler;

import com.swa.order_service.order_domain.order_application_service.dto.update.delete.UpdateDelItemsCommand;
import com.swa.order_service.order_domain.order_application_service.dto.update.delete.UpdateDelItemsResponse;
import com.swa.order_service.order_domain.order_application_service.mapper.OrderDataMapper;
import com.swa.order_service.order_domain.order_application_service.ports.output.OrderRepository;
import com.swa.order_service.order_domain.order_domain_core.entity.Order;
import com.swa.order_service.order_domain.order_domain_core.exception.OrderDomainException;
import org.springframework.stereotype.Service;

@Service
public class UpdateDeleteItemsHandler {
    private final OrderDataMapper mapper;
    private final OrderRepository repository;

    public UpdateDeleteItemsHandler(OrderDataMapper mapper, OrderRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public UpdateDelItemsResponse updateDeleteItems(UpdateDelItemsCommand command) {
        Order order = repository.findByTrackingId(command.getTrackingId())
                .orElseThrow(() -> new OrderDomainException(
                        "Order with tracking ID " + command.getTrackingId() + " not found"
                ));

        Order updatedDeleteItemsOrder = order.validateAndUpdateDeleteItems(command.getProductIds());

        Order orderUpdated = repository.save(updatedDeleteItemsOrder);

        return mapper.orderToUpdateDeleteItemsResponse(
                orderUpdated,
                "Xoá sản phẩm khỏi order thành công"
        );
    }
}
