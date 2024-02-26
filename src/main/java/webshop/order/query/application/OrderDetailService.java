package webshop.order.query.application;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import webshop.catalog.command.domain.product.Item;
import webshop.catalog.query.product.ItemData;
import webshop.catalog.query.product.ItemService;
import webshop.order.command.domain.Order;
import webshop.order.command.domain.OrderNo;
import webshop.order.command.domain.OrderRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class OrderDetailService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemService itemService;

    @Transactional
    public Optional<OrderDetail> getOrderDetail(String orderNumber){
        Optional<Order> orderOpt = orderRepository.findById(new OrderNo(orderNumber));
        return orderOpt.map(order -> {
            List<OrderItemDetail> orderItems = order.getOrderItems().stream()
                    .map(orderItem -> {
                        Optional<Item> itemOpt = itemService.findOne(orderItem.getItemId());
                        ItemData itemData = new ItemData(Objects.requireNonNull(itemOpt.orElse(null)));
                        return new OrderItemDetail(orderItem,itemData);
                    }).collect(Collectors.toList());
            return new OrderDetail(order, orderItems);
        });
    }
}
