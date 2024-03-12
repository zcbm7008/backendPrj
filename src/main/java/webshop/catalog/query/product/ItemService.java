package webshop.catalog.query.product;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import webshop.catalog.NoCategoryException;
import webshop.catalog.command.domain.category.Category;
import webshop.catalog.command.domain.category.CategoryRepository;
import webshop.catalog.command.domain.product.Item;
import webshop.catalog.command.domain.product.ItemRepository;
import webshop.catalog.command.domain.product.ItemSpecification;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class ItemService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ItemRepository itemRepository;

    public void saveItem(Item item){
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> findOne(Long itemId){
        return itemRepository.findById(itemId);
    }

    public CategoryItem getItemInCategory(Long categoryId, int page, int size){
        Category category = categoryRepository.findById(categoryId).orElseThrow(NoCategoryException::new);

        Page<Item> itemPage = itemRepository.findByCategoryIdsContains(category.getId(), Pageable.ofSize(size).withPage(page-1));

        return new CategoryItem(category,toSummary(itemPage.getContent()),page,itemPage.getSize(),itemPage.getTotalElements(),itemPage.getTotalPages());

    }

    public CategoryItem getItemInCategory(Long categoryId, int page, int size, Specification<Item> specs){

        Category category = categoryRepository.findById(categoryId).orElseThrow(NoCategoryException::new);

        Page<Item> itemPage = itemRepository.findByCategoryIdsContains(specs,category.getId(), Pageable.ofSize(size).withPage(page-1));

        return new CategoryItem(category,toSummary(itemPage.getContent()),page,itemPage.getSize(),itemPage.getTotalElements(),itemPage.getTotalPages());

    }

    public CategoryItem getItemInName(String name, int page, int size){
        Category category = new Category(10L,"Any");
        Page<Item> itemPage = itemRepository.findAll(ItemSpecification.nameContains(name),Pageable.ofSize(size).withPage(page-1));
        return new CategoryItem(category,toSummary(itemPage.getContent()),page,itemPage.getSize(),itemPage.getTotalElements(),itemPage.getTotalPages());
    }

    public CategoryItem findItems(int page, int size,Specification<Item> specs){
        Category category = new Category(10L,"Any");

        Page<Item> itemPage = itemRepository.findAll(specs,Pageable.ofSize(size).withPage(page-1));

        return new CategoryItem(category,toSummary(itemPage.getContent()),page,itemPage.getSize(),itemPage.getTotalElements(),itemPage.getTotalPages());

    }

    private List<ItemSummary> toSummary(List<Item> items){
        return items.stream().map(
                item -> new ItemSummary(
                        item.getId(),
                        item.getName(),
                        item.getPrice().getValue(),
                        item.getFirstImage().getUrl(),
                        item.getSeller().getName()
                )
        ).collect(toList());
    }
}
