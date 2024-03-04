package webshop.order.query.dto;

import lombok.Getter;
import webshop.order.command.domain.OrderNo;
import webshop.order.command.domain.OrderState;

@Getter
public class OrderView {
    private final String number;
    private final OrderState state;
    private final String memberName;
    private final String memberId;
    private final String itemName;

    public OrderView(OrderNo number, OrderState state, String memberName, Long memberId, String itemName){
        this.number = number.getNumber();
        this.state = state;
        this.memberName = memberName;
        this.memberId = memberId.toString();
        this.itemName = itemName;
    }

}
