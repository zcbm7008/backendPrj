package webshop.catalog.query.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import webshop.catalog.command.domain.category.Category;

import java.util.List;

@Getter
@AllArgsConstructor
public class CategoryItem {
    private Category category;

    private List<ItemSummary> items;

    private int page;
    private int size;
    private long totalCount;
    private int totalPages;
}
