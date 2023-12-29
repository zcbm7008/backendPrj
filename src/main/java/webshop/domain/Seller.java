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
import lombok.*;
import webshop.domain.item.Item;

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
	
	@OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
	private List<Item> items = new ArrayList<Item>();
	
	public void setMember(Member member) {
		this.member = member;
		member.getSellers().add(this);
	}

	//Create Method//
	public static Seller createSeller(Member member, String name){
		if (member == null) {
			throw new IllegalArgumentException("Member cannot be null");
		}
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}

		Seller seller = new Seller();
		seller.setName(name); // 이름 설정
		seller.setMember(member); // 멤버 설정

		return seller;
	}
	
}
