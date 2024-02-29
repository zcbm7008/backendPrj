package webshop.order.command.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderPlacedEvent {
    private String number;
    private Orderer orderer;
    private List<OrderItem> orderItems;
    private LocalDateTime orderDate;

    private OrderPlacedEvent(){
    }

    public OrderPlacedEvent(String number, Orderer orderer, List<OrderItem> orderItems, LocalDateTime orderDate){
        this.number=number;
        this.orderer=orderer;
        this.orderItems=orderItems;
        this.orderDate=orderDate;
    }
}
