package webshop.order.command.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import webshop.catalog.command.domain.product.Item;
import webshop.common.model.Money;
import webshop.common.jpa.MoneyConverter;
import webshop.catalog.command.domain.product.QuantityState;

@Getter
@ToString
@Entity
@Table(name = "ORDER_ITEM")
public class OrderItem {
	
	@Id @GeneratedValue
	@Column(name="ORDER_ITEM_ID")
	private Long itemId;

	@Convert(converter = MoneyConverter.class)
	@Column(name = "PRICE")
	private Money price;

	@Column(name = "quantity")
	private int quantity;

	@Convert(converter = MoneyConverter.class)
	@Column(name = "AMOUNTS")
	private Money amounts;

	protected OrderItem() {

	}

	public OrderItem(Long itemId, Money price, int quantity){
		this.itemId = itemId;
		this.price = price;
		this.quantity = quantity;
		this.amounts = calculateAmounts();

	}



	private Money calculateAmounts() { return price.multiply(quantity);}

}
