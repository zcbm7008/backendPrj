package webshop.order.command.application;

import lombok.Getter;
import lombok.Setter;
import webshop.order.command.domain.OrderItem;

import java.util.List;

@Setter
@Getter
public class OrderRequest {

    private List<OrderProduct> orderProducts;

    private Long ordererMemberId;

}
