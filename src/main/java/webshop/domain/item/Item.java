package webshop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import webshop.domain.*;
import webshop.exception.NotEnoughStockException;
import webshop.exception.NotLimitedItemException;

import java.util.ArrayList;
import java.util.List;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY_ID")
	private CategoryItem categoryItem;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SELLER_ID")
	private Seller seller;


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
		else{
			throw new NotLimitedItemException("isLimitedQuantity이 False입니다.");
		}
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


}
