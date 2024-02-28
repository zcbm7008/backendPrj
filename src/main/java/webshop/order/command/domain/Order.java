package webshop.order.command.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import webshop.common.jpa.MoneyConverter;
import webshop.util.MailService;
import webshop.common.model.Money;
import webshop.user.domain.member.Member;

@Getter
@Setter
@Entity
@ToString
@Table(name = "ORDERS")
public class Order {

	@EmbeddedId
	private OrderNo number;
	
	@Embedded
	private Orderer orderer;

	@Convert(converter = MoneyConverter.class)
	@Column(name = "TOTAL_AMOUNTS")
	private Money totalAmounts;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "ORDER_ITEMS", joinColumns = @JoinColumn(name = "ORDER_NUMBER"))
	@OrderColumn(name = "line_idx")
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();

	private OrderState state;

	@Column(name = "ORDER_DATE")
	private LocalDateTime orderDate;

	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
	}

	protected Order() {

	}
	public Order(OrderNo number,Orderer orderer, List<OrderItem> orderItems, OrderState state){
		setNumber(number);
		setOrderer(orderer);
		setOrderItems(orderItems);
		this.state = state;
		this.orderDate = LocalDateTime.now();
	}

	private void setOrderItems(List<OrderItem> orderItems){
		verifyAtLeastOneMoreOrderItems(orderItems);
		this.orderItems = orderItems;
		calculateTotalAmounts();
	}

	private void verifyAtLeastOneMoreOrderItems(List<OrderItem> orderItems){
		if(orderItems == null || orderItems.isEmpty()){
			throw new IllegalArgumentException("no OrderItems");
		}
	}

	//Business Logic//
	private void calculateTotalAmounts() {
		this.totalAmounts = new Money(orderItems.stream()
				.mapToInt(x -> x.getAmounts().getValue()).sum());
	}



}
