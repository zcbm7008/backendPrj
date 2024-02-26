package webshop.order.command.application;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webshop.catalog.command.domain.product.Item;
import webshop.catalog.command.domain.product.ItemRepository;
import webshop.common.ValidationError;
import webshop.common.ValidationErrorException;
import webshop.order.command.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlaceOrderService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrdererService ordererService;

    @Transactional
    public OrderNo placeOrder(OrderRequest orderRequest){
        List<ValidationError> errors = validateOrderRequest(orderRequest);
        if(!errors.isEmpty()) throw new ValidationErrorException(errors);

        List<OrderItem> orderItems = new ArrayList<>();
        for(OrderProduct oi : orderRequest.getOrderProducts()){
            Optional<Item> itemOpt = itemRepository.findById(oi.getProductId());
            Item item = itemOpt.orElseThrow(()->new NoOrderProductException(oi.getProductId()));
            orderItems.add(new OrderItem(item.getId(),item.getPrice(),item.getStockQuantity()));
        }
        OrderNo orderNo = orderRepository.nextOrderNo();
        Orderer orderer = ordererService.createOrderer(orderRequest.getOrdererMemberId());

        Order order = new Order(orderer,orderItems, OrderState.PAYMENT_WAITING);
        orderRepository.save(order);
        return orderNo;
    }

    private List<ValidationError> validateOrderRequest(OrderRequest orderRequest){
        return new OrderRequestValidator().validate(orderRequest);
    }
}
