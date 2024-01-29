package webshop.User.domain.member;

import jakarta.persistence.*;
import lombok.*;
import webshop.User.domain.seller.Seller;
import webshop.common.event.Events;
import webshop.common.jpa.EmailConverter;
import webshop.common.jpa.MoneyConverter;
import webshop.common.model.Email;
import webshop.common.model.Money;
import webshop.domain.Review;
import webshop.exception.NegativeBalanceException;
import webshop.order.command.domain.Order;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member {

	@Id @GeneratedValue
	@Column(name = "MEMBER_ID")
	private Long id;

	private String name;

	@Convert(converter = MoneyConverter.class)
	private Money balance = new Money(0);

	@Convert(converter = EmailConverter.class)
	private Email email;

	private boolean blocked;

	public Member(String name) {
		setName(name);
	}
	
	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<>();
	
	@OneToMany(mappedBy = "member")
	private List<Seller> sellers = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<Review> reviews = new ArrayList<>();

	public void block() {
		this.blocked = true;
	}

	public void unblock() {
		this.blocked = false;
	}


	public void addBalance(Money money){
		setBalance(getBalance().add(money));
	}

	public void subtractBalance(Money money){
		if (getBalance().getValue() - money.getValue() <0){
			throw new NegativeBalanceException("Cannot subtract a larger amount than the current value.");
		}
		setBalance(this.getBalance().subtract(money));

	}

	public boolean isBlocked() { return blocked; }

}
