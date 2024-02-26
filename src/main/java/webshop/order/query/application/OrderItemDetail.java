package webshop.order.query.application;

import lombok.Getter;
import webshop.catalog.query.product.ItemData;
import webshop.order.command.domain.OrderItem;

@Getter
public class OrderItemDetail {

    private final Long itemId;
    private final int price;
    private final int quantity;
    private final int amounts;
    private final String itemName;
    private final String itemImgPath;

    public OrderItemDetail(OrderItem orderItem, ItemData itemData){
        itemId = orderItem.getItemId();
        price = orderItem.getPrice().getValue();
        quantity = orderItem.getQuantity();
        amounts = orderItem.getAmounts().getValue();
        itemName = itemData.getName();
        itemImgPath = itemData.getFirstImage().getUrl();
    }

}
