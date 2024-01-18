package webshop.User.domain.seller;

import java.util.ArrayList;
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
import lombok.*;
import webshop.User.domain.member.MemberBlockedEvent;
import webshop.catalog.command.domain.product.Item;
import webshop.User.domain.member.Member;
import webshop.common.event.Events;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
public class Seller {
	@Id @GeneratedValue
	@Column(name = "SELLER_ID")
	private Long id;
	
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

	public Seller(Member member){
		setMember(member);
	}


	
	@OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
	private List<Item> sellerItems = new ArrayList<Item>();
	
	public void setMember(Member member) {
		this.member = member;
		member.getSellers().add(this);
	}

	public void addSellerItem(Item sellerItem) {
		sellerItems.add(sellerItem);
		sellerItem.setSeller(this);
	}

	public void blockMember(){
		Events.raise(new MemberBlockedEvent(this.member.getId()));
	}
	
}
