package webshop.catalog.query.product;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import webshop.catalog.command.domain.product.ContentType;
import webshop.catalog.command.domain.product.Item;
import webshop.catalog.command.domain.product.QuantityState;
import webshop.common.jpa.MoneyConverter;
import webshop.common.model.Image;
import webshop.common.model.InitImage;
import webshop.common.model.Money;
import webshop.domain.Review;
import webshop.user.domain.seller.Seller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Getter
@Entity
@Table(name = "ITEM")
public class ItemData {

    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;

    @Setter
    @Convert(converter = MoneyConverter.class)
    private Money price;

    private QuantityState quantityState = QuantityState.Unlimited;
    private int stockQuantity;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "ITEM_CATEGORY", joinColumns = @JoinColumn(name = "ITEM_ID"))
    private Set<Long> categoryIds = new HashSet<>();

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

    protected ItemData(){};

    public ItemData(Item item){
        this.id  = item.getId();
        name = item.getName();
        price = item.getPrice();
        contentType = item.getContentType();
        content = item.getContent();
        images = item.getImages();
        reviews = item.getReviews();
    }

    public Image getFirstImage(){
        if(images.isEmpty() || images.get(0) == null){
            return new InitImage();
        }
        return this.getImages().get(0);
    }
}
