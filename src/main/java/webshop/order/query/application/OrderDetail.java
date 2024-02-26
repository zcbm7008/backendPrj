package webshop.order.query.application;

import jakarta.persistence.*;
import lombok.Getter;
import webshop.common.jpa.MoneyConverter;
import webshop.common.model.Money;
import webshop.order.command.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderDetail {

    private final String number;

    private final Orderer orderer;
    private final OrderState state;
    private final int totalAmounts;
    private List<OrderItemDetail> orderItems;

    public OrderDetail(Order order, List<OrderItemDetail> orderItemDetails){
        this.orderItems = orderItemDetails;
        number = order.getNumber().getNumber();
        orderer = order.getOrderer();
        state = order.getState();
        totalAmounts = order.getTotalAmounts().getValue();

    }


}
