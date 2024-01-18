package webshop.catalog.command.domain.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import webshop.User.domain.member.BalanceAddedEvent;
import webshop.User.domain.seller.Seller;
import webshop.common.event.Events;
import webshop.common.model.Comment;
import webshop.common.model.Money;
import webshop.common.jpa.MoneyConverter;
import webshop.domain.*;
import webshop.exception.NotEnoughStockException;
import webshop.exception.NotLimitedItemException;
import webshop.User.domain.member.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {

	@Id @GeneratedValue
	@Column(name="ITEM_ID")
	private Long id;
	
	private String name;

	@Setter
	@Convert(converter = MoneyConverter.class)
	private Money price;

	private QuantityState quantityState = QuantityState.Unlimited;
	private int stockQuantity;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "ITEM_CATEGORY", joinColumns = @JoinColumn(name = "ITEM_ID"))
	private Set<Long> categoryIds;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SELLER_ID")
	private Seller seller;

	private ContentType contentType;

	private String Content;


	@OneToMany(mappedBy = "item")
	private List<Review> reviews = new ArrayList<>();

	//Business Logic
	public void addStock(int quantity){
		if(quantityState == QuantityState.Limited) {this.stockQuantity += quantity;}
		else {
			throw new NotLimitedItemException("isLimitedQuantity이 False입니다.");
		}
	}
	public void removeStock(int quantity){
		if(quantityState == QuantityState.Limited){
			int restStock = this.stockQuantity - quantity;
			if(restStock < 0){
				throw new NotEnoughStockException("need more stock");
			}
			this.stockQuantity = restStock;
		}
	}

	public void saleStock(int quantity){
		if (!canOrderable())
		{
			throw new IllegalStateException("This item is not Orderable");
		}
		removeStock(quantity);
		Events.raise(new BalanceAddedEvent(seller.getMember().getId(), price.multiply(quantity)));

	}



	public void addReview(Member member, String comment) {
		Review review = new Review();
		review.setMember(member);
		review.setComment(new Comment(comment));
		review.setItem(this);
	}

	public void setLimited() {
		this.setQuantityState(QuantityState.Limited);
	}
	public void setUnLimited(){ this.setQuantityState(QuantityState.Unlimited);}
	public void setDiscontinued() {this.setQuantityState(QuantityState.Discontinued);}

	public void verifyOrderable() {
		if(!canOrderable()){
			throw new IllegalStateException();
		}

	}

	public boolean canOrderable() {
		if (quantityState == QuantityState.Limited){
			if(stockQuantity > 0){
				return true;
			}
		}
		if (quantityState == QuantityState.Unlimited){
			return true;
		}
		return false;
	}

}
