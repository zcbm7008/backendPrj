package webshop.order.command.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import webshop.util.MailService;
import webshop.common.model.Money;
import webshop.User.domain.member.Member;

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
	
	private LocalDateTime orderDate;

	private OrderState state;

	@Transient
	MailService mailService;
	
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

		order.setOrderDate(LocalDateTime.now());
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

	//TODO
	@Transactional
	public void startDelivering() {
		this.state = OrderState.DELIVERING;
		StringBuilder contextBuilder = new StringBuilder();
		String subject = "상품배송";
		for(OrderItem orderItem : this.orderItems){

			contextBuilder.append(orderItem.getId()).append(" ");


		}
		String context = contextBuilder.toString();
		try{
			mailService.sendSimpleEmail(subject,member.getEmail(),context);
			this.state = OrderState.DELIVERY_COMPLETED;
		} catch(EmailSendingException e){

		}

	}


}
