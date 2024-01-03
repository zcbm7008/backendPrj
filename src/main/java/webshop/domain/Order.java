package webshop.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import webshop.domain.item.Money;

@Getter
@Setter
@Entity
@ToString
@Table(name = "ORDERS")
public class Order {
	@Id @GeneratedValue
	@Column(name ="ORDER_ID")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;
	
	@OneToMany(mappedBy="order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();
	
	private Date orderDate;
	
	public void setMember(Member member) {
		this.member = member;
		member.getOrders().add(this);
	}
	
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}

	//Create Method//
	public static Order createOrder(Member member, OrderItem... orderItems){
		Order order = new Order();
		order.setMember(member);
		for (OrderItem orderItem : orderItems){
			order.addOrderItem(orderItem);
		}

		order.setOrderDate(new Date());
		return order;
	}

	//Business Logic//
	public int getTotalPrice() {
		Money totalPrice = new Money(0);
		for(OrderItem orderItem : orderItems){
			totalPrice = totalPrice.add(orderItem.getTotalPrice());
		}
		return totalPrice.getValue();
	}


}
