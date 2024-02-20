package webshop.catalog.command.domain.product;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import webshop.common.model.Image;
import webshop.user.domain.member.BalanceAddedEvent;
import webshop.user.domain.seller.Seller;
import webshop.common.event.Events;
import webshop.common.model.Comment;
import webshop.common.model.Money;
import webshop.common.jpa.MoneyConverter;
import webshop.domain.*;
import webshop.exception.NotEnoughStockException;
import webshop.exception.NotLimitedItemException;
import webshop.user.domain.member.Member;

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

	private String content;

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
	orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "ITEM_ID")
	@OrderColumn(name = "LIST_IDX")
	private List<Image> images = new ArrayList<>();


	@OneToMany(mappedBy = "item")
	private List<Review> reviews = new ArrayList<>();

	//Business Logic
	public void addStock(int quantity){
		if(quantityState == QuantityState.Limited) {this.stockQuantity += quantity;}
		else {
			throw new NotLimitedItemException("isLimitedQuantity이 False입니다.");
		}
	}

	@Transactional
	public void removeStock(int quantity){
		if(quantityState == QuantityState.Limited){
			if(this.stockQuantity - quantity < 0){
				throw new NotEnoughStockException("need more stock");
			}
			this.stockQuantity = this.stockQuantity - quantity;
		}
	}

	@Transactional
	public void saleStock(int quantity){
		System.out.println("Start saleStock");

		if (!canOrderable())
		{
			throw new IllegalStateException("This item is not Orderable");
		}

		if(quantityState == QuantityState.Limited) {
			if (this.stockQuantity - quantity < 0) {
				throw new NotEnoughStockException("need more stock");
			}

		}

		removeStock(quantity);
		System.out.println("Start Event");
		Events.raise(new BalanceAddedEvent(seller.getMember().getId(), price.multiply(quantity)));
		System.out.println("End Event");

		System.out.println("End saleStock");

	}





	public void addReview(Member member, String comment) {
		Review review = new Review();
		review.setMember(member);
		review.setComment(new Comment(comment));
		review.setItem(this);
	}

	public void addImage(Image image) {
		this.getImages().add(image);
	}




	public void setLimited() { this.setQuantityState(QuantityState.Limited);}
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
        return quantityState == QuantityState.Unlimited;
    }

}
