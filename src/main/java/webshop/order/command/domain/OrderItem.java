package webshop.order.command.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import webshop.catalog.command.domain.product.Item;
import webshop.common.model.Money;
import webshop.common.jpa.MoneyConverter;
import webshop.catalog.command.domain.product.QuantityState;

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
	public static OrderItem createOrderItem(Item item, int count){
		OrderItem orderItem = new OrderItem();
		orderItem.setItem(item);
		orderItem.setCount(count);

		if(item.getQuantityState() == QuantityState.Limited){
			item.removeStock(count);
		}

		orderItem.setOrderPrice(item.getPrice().multiply(count));

		return orderItem;
	}

	//Business Logic//
	public Money getTotalPrice() {
		return orderPrice.multiply(count);
	}

}
