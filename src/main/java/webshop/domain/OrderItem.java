package webshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import webshop.domain.item.Item;
import webshop.domain.item.Money;
import webshop.domain.item.MoneyConverter;

@Getter
@Setter
@Entity
@ToString
@Table(name = "ORDER_ITEM")
public class OrderItem {
	
	@Id @GeneratedValue
	@Column(name="ORDER_ITEM_ID")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Item item;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID")
	private Order order;

	@Convert(converter = MoneyConverter.class)
	private Money orderPrice;
	private int count;

	//Create Method//
	public static OrderItem createOrderItem(Item item, Money orderPrice, int count){
		OrderItem orderItem = new OrderItem();
		orderItem.setItem(item);
		orderItem.setOrderPrice(orderPrice);
		orderItem.setCount(count);

		if(item.isLimitedQuantity()){
			item.removeStock(count);
		}
		return orderItem;
	}

	//Business Logic//
	public Money getTotalPrice() {
		return orderPrice.multiply(count);
	}

}
