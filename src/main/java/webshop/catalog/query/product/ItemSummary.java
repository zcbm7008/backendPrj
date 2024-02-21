package webshop.catalog.query.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ItemSummary {
    private Long id;
    private String name;
    private int price;
    private String image;
    private String sellerName;
}
