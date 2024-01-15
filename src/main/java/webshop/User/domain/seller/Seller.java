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
import webshop.catalog.command.domain.product.Item;
import webshop.User.domain.member.Member;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Seller {
	@Id @GeneratedValue
	@Column(name = "SELLER_ID")
	private Long id;
	
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

	private boolean blocked;
	
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

	//Create Method//
//	public static Seller createSeller(Member member, Item... sellerItems){
//		if (member == null) {
//			throw new IllegalArgumentException("Member cannot be null");
//		}
//
//		Seller seller = new Seller();
//		seller.setMember(member); // 멤버 설정
//
//		for (Item sellerItem : sellerItems){
//			seller.addSellerItem(sellerItem);
//		}
//
//
//		return seller;
//	}
	
}
