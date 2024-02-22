package webshop.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import webshop.catalog.command.domain.category.Category;
import webshop.catalog.command.domain.category.CategoryRepository;
import webshop.catalog.command.domain.product.Artwork;
import webshop.catalog.command.domain.product.Item;
import webshop.catalog.command.domain.product.ItemRepository;
import webshop.catalog.query.product.CategoryItem;
import webshop.catalog.query.product.ItemService;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/appConfig.xml")
@Transactional
public class ItemCategoryIntegrationTest {
    @PersistenceContext
    EntityManager em;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ItemService itemService;
    Category category;
    Artwork artwork1;
    @BeforeEach
    void Setup(){
        category = new Category(0L,"category1");
        artwork1 = new Artwork();

        artwork1.setName("art1");
        artwork1.addCategoryId(0L);

        categoryRepository.save(category);
        itemService.saveItem(artwork1);
    }


    @Test
    public void FindByCategoryIdsContains() throws Exception{

        //Given

        //When
        Page<Item> items = itemRepository.findByCategoryIdsContains(category.getId(), Pageable.ofSize(1));

        System.out.println(artwork1.getCategoryIds());
        System.out.println(category.getId());

        assertEquals(1,items.getTotalElements());
    }

    @Test
    public void shouldFindItemsInCategory() throws Exception {
        CategoryItem categoryItem = itemService.getItemInCategory(category.getId(),1,5);

        assertEquals(1,categoryItem.getItems().size());
    }

}
