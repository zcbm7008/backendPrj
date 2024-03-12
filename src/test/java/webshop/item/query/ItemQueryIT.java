package webshop.item.query;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import webshop.catalog.command.domain.product.Item;
import webshop.catalog.command.domain.product.ItemSpecification;
import webshop.catalog.query.product.CategoryItem;
import webshop.catalog.query.product.ItemService;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/appConfig.xml")
@Profile("test")
@Sql({"/data.sql"})
@DataJpaTest
@Transactional
public class ItemQueryIT {
    @Autowired
    ItemService itemService;

    @Test
    public void getItemInNameTest() throws Exception{

        //Given
        String searchQuery = "art";
        int page = 1;
        int size = 10;

        CategoryItem items = itemService.getItemInName(searchQuery,page,size);

        assertEquals(2,items.getTotalCount());
        assertTrue(items.getItems().stream()
                .allMatch(item -> item.getName().contains(searchQuery)));

    }

    @Test
    public void hasCategoryQueryTest() throws Exception{

        //Given
        Long searchCategoryId = 0L;
        int page = 1;
        int size = 10;

        Specification<Item> specs = Specification.where(ItemSpecification.hasCategory(searchCategoryId));


        CategoryItem items = itemService.findItems(page,size,specs);

        assertEquals(2,items.getTotalCount());


    }

    @Test
    public void FindItemsWithSpecsTest() throws Exception{
        //Given
        Long searchCategoryId = 1L;
        String searchQuery = "pic";
        int page = 1;
        int size = 10;

        Specification<Item> specs = Specification.where(ItemSpecification.hasCategory(searchCategoryId))
                .and(ItemSpecification.nameContains(searchQuery));


        //When
        CategoryItem items = itemService.findItems(page,size,specs);

        //Then
        assertEquals(1,items.getTotalCount());
        assertTrue(items.getItems().stream()
                .allMatch(item -> item.getName().contains(searchQuery)));
    }
}
