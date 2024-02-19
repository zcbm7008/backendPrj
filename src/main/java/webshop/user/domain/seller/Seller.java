package webshop.user.domain.seller;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import webshop.common.jpa.ImageConverter;
import webshop.common.jpa.MoneyConverter;
import webshop.common.model.Image;
import webshop.user.domain.member.MemberBlockedEvent;
import webshop.catalog.command.domain.product.Item;
import webshop.user.domain.member.Member;
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

	@JoinColumn(name = "SELLER_ID")
	@Convert(converter = ImageConverter.class)
	private Image image;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

	protected Seller(){

	}

	public Seller(Member member){
		setMember(member);
	}
	
	@OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
	private List<Item> sellerItems = new ArrayList<>();
	
	public void setMember(Member member) {
		this.member = member;
		member.getSellers().add(this);
	}

	public void addSellerItem(Item sellerItem) {
		verifySellableState();
		sellerItems.add(sellerItem);
		sellerItem.setSeller(this);
	}

	public void validateSeller(){
			verifyHasMember();
			verifyNotBlocked();
	}

	public void verifyHasMember(){
		if(this.getMember() == null){
			throw new IllegalStateException("seller has no member");
		}
	}
	private void verifySellableState() {
		verifyNotBlocked();
	}


	private void verifyNotBlocked() {
		if (this.getMember().isBlocked()){
			throw new IllegalStateException(this.getMember().getId()+ " is blocked");
		}
	}

	public void blockMember(){
		Events.raise(new MemberBlockedEvent(this.member.getId()));
	}

	public String getSellerImageUrl() {
		return this.image.getUrl();
	}


	
}
