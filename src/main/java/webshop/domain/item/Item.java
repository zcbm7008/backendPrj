package webshop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import webshop.domain.CategoryItem;
import webshop.domain.Seller;
import webshop.exception.NotEnoughStockException;
import webshop.exception.NotLimitedItemException;

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
	private int price;
	private boolean isLimitedQuantity;
	private int stockQuantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY_ID")
	private CategoryItem categoryItem;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SELLER_ID")
	private Seller seller;

	//Business Logic
	public void addStock(int quantity){
		if(isLimitedQuantity) {this.stockQuantity += quantity;}
		else {
			throw new NotLimitedItemException("isLimitedQuantity이 False입니다.");
		}
	}
	public void removeStock(int quantity){
		if(isLimitedQuantity){
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


}
